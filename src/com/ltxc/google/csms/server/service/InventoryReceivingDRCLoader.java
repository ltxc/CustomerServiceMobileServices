package com.ltxc.google.csms.server.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.aa.api.WebServiceSoap;
import com.ltxc.google.csms.server.domain.IValidationStoredProcedure;
import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ReceivingDRCCloseRCStoredProcedure;
import com.ltxc.google.csms.server.domain.ReceivingDRCCreateRCStoredProcedure;
import com.ltxc.google.csms.server.domain.ReceivingDRCValidationStoredProcedure;
import com.ltxc.google.csms.server.domain.ReceivingValidationStoredProcedure;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.server.resource.TemplateLoaderFactory;
import com.ltxc.google.csms.server.service.AAWebServiceAPI.WebServiceAPIDelegate;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

/**
 * Receiving Via Shiplist, DRC loader with create repair order, add item, close
 * repair order
 * 
 * @author JLU
 * 
 */
public class InventoryReceivingDRCLoader implements ILoader {

	private static Logger logger = Logger
			.getLogger(InventoryReceivingDRCLoader.class.getName());
	private AAWebServiceAPI _webServiceAPI;

	@Override
	public boolean load(TransactionBase tb) {
		boolean isSuccess = false;
		// final Date process_date = Calendar.getInstance().getTime();
		final InventoryTransaction transaction = (InventoryTransaction) tb;
		// set loading status
		final StringBuilder log = new StringBuilder();
		final StringBuilder templates = new StringBuilder();
		try {
			if (transaction == null)
				throw new LoaderException(
						"InventoryReceivingDRCLoader:load - transaction object is not set yet...");
			// transaction.setProcess_date(process_date);
			transaction
					.setProcess_message("Start loading... If you see this message, it means loading failed...");
			transaction.setProcess_status(ProcessStatusEnum.PROCESSING
					.getProcessStatusName());
			transaction.persist();

			_webServiceAPI.serviceCall(new WebServiceAPIDelegate() {

				@Override
				public boolean transaction(String sessionID,
						WebServiceSoap proxy) {
					boolean isSuccess = false;

					try {
						// first call the validation stored procedure and set up
						// the processing message
						if (createRC(sessionID, proxy, transaction, templates,
								log)) {
							// accept lines
							if (receiveLineItems(sessionID, proxy, transaction,
									templates, log)) {
								// close rp
								if (closeRC(sessionID, proxy, transaction,
										templates, log)) {
									isSuccess = true;
								}
							}
						}
					} catch (Exception xe) {
						xe.printStackTrace();
						logger.log(
								Level.SEVERE,
								"InventoryLoader:load -- exception:"
										+ xe.getMessage());

						log.append(
								"Error occurred while processing DRC receiving; ")
								.append(xe.getMessage());
					}

					// transaction.setProcess_date(process_date);
					transaction.setProcess_message(log.toString());
					transaction.setXmldoc(templates.toString());
					if (isSuccess)
						transaction
								.setProcess_status(ProcessStatusEnum.COMPLETED
										.getProcessStatusName());
					else
						transaction.setProcess_status(ProcessStatusEnum.FAILED
								.getProcessStatusName());
					return isSuccess;
				}

				@Override
				public boolean beforeTransaction(String sessionID,
						WebServiceSoap proxy) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean afterTransaction(String sessionID,
						WebServiceSoap proxy) {
					// TODO Auto-generated method stub
					return true;
				}
			});

		} catch (Exception xe) {
			xe.printStackTrace();
			String message = "InventoryReceingDRCLoader:load failed -- "
					+ xe.getMessage();
			logger.log(Level.SEVERE, message);
			transaction.setProcess_message(message + "; Processed Log: "
					+ log.toString());
		}

		return isSuccess;
	}

	@Override
	public void setWebServiceAPI(AAWebServiceAPI _webServiceAPI)
			throws LoaderException {
		if (_webServiceAPI != null)
			this._webServiceAPI = _webServiceAPI;
		else
			throw new LoaderException(
					"InventoryReceivingDRCLoader:setWebServiceAPI - web service api object can not be null");

	}

	public boolean createRC(String sessionID, WebServiceSoap proxy,
			InventoryTransaction transaction, StringBuilder templates,
			final StringBuilder log) throws RemoteException {
		boolean isCreateRCOK = false;
		int validationCode = ReceivingDRCCreateRCStoredProcedure.validate(
				transaction, log);

		if (validationCode == 1) {

			// create RC
			String xmlTemplate = TemplateLoaderFactory
					.get()
					.getLoader(TransactionTypeEnum.DRCCREATERC)
					.generateAPIXml(TransactionTypeEnum.DRCCREATERC,
							transaction);
			templates.append("\n********Create RC********\n").append(
					xmlTemplate);
			String result = proxy.sendSynchronic(sessionID, xmlTemplate);
			AAWebServiceResult wsr = new AAWebServiceResult(result);
			wsr.parse(new IAAWebServiceResultHandler() {

				@Override
				public void onSuccess(String result) {

					log.append("RC#:").append(result)
							.append(" has been created.");
				}

				@Override
				public void onInvalidResult(String error) {

					log.append(
							"Error occurred while parsing the AA RC Creation API call result. ")
							.append(error);

				}

				@Override
				public void onFailure(String error) {

					log.append("It failed to create RC. ").append(error);
				}
			});
			if (wsr.isSuccess()) {
				// successfully created rc
				isCreateRCOK = true;
			}
		} else if (validationCode == 2) {
			isCreateRCOK = true;
		}

		return isCreateRCOK;
	}

	public boolean receiveLineItems(String sessionID, WebServiceSoap proxy,
			InventoryTransaction transaction, StringBuilder templates,
			final StringBuilder log) throws RemoteException {
		boolean isReceiveOK = false;
		final boolean isAllValidated = ReceivingDRCValidationStoredProcedure
				.validate(transaction, log);
		// receive line items
		String xmlTemplate = TemplateLoaderFactory.get()
				.getLoader(TransactionTypeEnum.DRC)
				.generateAPIXml(TransactionTypeEnum.DRC, transaction);
		templates.append("\n********Receiving Line Items********\n").append(
				xmlTemplate);
		if (xmlTemplate != null && !xmlTemplate.trim().isEmpty()) {
			String result = proxy.sendSynchronic(sessionID, xmlTemplate);
			AAWebServiceResult wsr = new AAWebServiceResult(result);
			wsr.parse(new IAAWebServiceResultHandler() {

				@Override
				public void onSuccess(String result) {
					if (isAllValidated)
						log.append(SharedConstants.Message_DRC_ReceivingSuccess);
					else
						log.append(SharedConstants.Message_DRC_ReceivingWarning);
				}

				@Override
				public void onInvalidResult(String error) {
					log.append(
							"Error occurred while parsing the AA DRC Receiving API call result. ")
							.append(error);
				}

				@Override
				public void onFailure(String error) {
					log.append("It failed to receive line items. ").append(
							error);
				}
			});
			if (wsr.isSuccess()) {
				// successfully created rc
				isReceiveOK = true;
			}
		} else
			isReceiveOK = true; // no receive but still need to close RC
		return isReceiveOK;
	}

	public boolean closeRC(String sessionID, WebServiceSoap proxy,
			InventoryTransaction transaction, StringBuilder templates,
			final StringBuilder log) throws RemoteException {

		boolean isCloseRCOK = false;
		int validationCode = ReceivingDRCCloseRCStoredProcedure
				.closeRC(transaction,log);
		if (validationCode==1) {
			// close RC
			String xmlTemplate = TemplateLoaderFactory
					.get()
					.getLoader(TransactionTypeEnum.DRCCLOSERC)
					.generateAPIXml(TransactionTypeEnum.DRCCLOSERC, transaction);
			templates.append("\n********Close RC********\n")
					.append(xmlTemplate);
			String result = proxy.sendSynchronic(sessionID, xmlTemplate);
			AAWebServiceResult wsr = new AAWebServiceResult(result);
			wsr.parse(new IAAWebServiceResultHandler() {

				@Override
				public void onSuccess(String result) {
					log.append("This RC has been closed.");
				}

				@Override
				public void onInvalidResult(String error) {
					log.append(
							"Error occurred while parsing the RC Close API call result. ")
							.append(error);

				}

				@Override
				public void onFailure(String error) {
					log.append("It failed to close the RC. ").append(error);

				}
			});
			if (wsr.isSuccess()) {
				// successfully created rc
				isCloseRCOK = true;
			}
		}
		else if (validationCode==2)
			isCloseRCOK = true;
		return isCloseRCOK;
	}
}
