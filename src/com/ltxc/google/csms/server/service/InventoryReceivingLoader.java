package com.ltxc.google.csms.server.service;


import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ltxc.google.csms.server.aa.api.WebServiceSoap;
import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ReceivingValidationStoredProcedure;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.server.resource.TemplateLoaderFactory;
import com.ltxc.google.csms.server.service.WebServiceAPI.WebServiceAPIDelegate;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

/***
 * 
 * @author JLU Miscellaneous Loader
 */
public class InventoryReceivingLoader implements ILoader {
	private static Logger logger = Logger
			.getLogger(InventoryReceivingLoader.class.getName());
	private WebServiceAPI _webServiceAPI;

	@Override
	public boolean load(TransactionBase tb) {
		boolean isSuccess = false;
		final Date process_date = Calendar.getInstance().getTime();
		final InventoryTransaction transaction = (InventoryTransaction) tb;
		//set loading status

		try {
			if (transaction == null)
				throw new LoaderException(
						"InventoryMSRLoader:load - transaction object is not set yet...");
			transaction.setProcess_date(process_date);
			transaction.setProcess_message("Start loading... If you see this message, it means loading failed...");
			transaction.setProcess_status(ProcessStatusEnum.PROCESSING.getProcessStatusName());
			transaction.persist();
			_webServiceAPI.serviceCall(new WebServiceAPIDelegate() {

				@Override
				public boolean transaction(String sessionID,
						WebServiceSoap proxy) {
					boolean isSuccess = false;
					try {
						// first call the validation stored procedure for each
						// line item
						if (!ReceivingValidationStoredProcedure
								.validateInventoryTransaction(transaction)) {
							return false;
						}
						
						TransactionTypeEnum type;
						// get transaction type
						try {
							type = TransactionTypeEnum.parse(transaction
									.getTransaction_type());
						} catch (Exception xe) {
							transaction
									.setProcess_status(ProcessStatusEnum.FAILED
											.getProcessStatusName());
							transaction
									.setProcess_message("Unsupported transaction type: "
											+ transaction.getTransaction_type());
							return false;
						}

						// send one by one
						int count = transaction.getLineItems().size();
						StringBuilder sb = new StringBuilder();
						boolean isAllSuccess = true;
						for (int i = 0; i < count; i++) {
							StringBuilder linesb = new StringBuilder();
							String validationMessage = "";
							InventoryLineItem lineItem = (InventoryLineItem)transaction.getLineItems().get(i);
							//do the validation one by one
							if(!ReceivingValidationStoredProcedure.validateInventoryTransaction(transaction, i))
							{
								lineItem.setProcess_message(lineItem.getValidationMessage());
								validationMessage =  lineItem.getValidationMessage();
								if(validationMessage==null)
									validationMessage="";
								isAllSuccess = false;
								continue;
							}

							
							//exception handling here
							if("RRFV1".equals(lineItem.getValidationTemplate())||"RRFV2".equals(lineItem.getValidationTemplate()))
							{
								transaction.setRef_doc_type_id("800");		
								type = TransactionTypeEnum.RRFV;
								transaction.setTransaction_type(type.getTransactionTypeName());
							}
							
							String xmlTemplate = TemplateLoaderFactory.get()
									.getLoader(type)
									.generateAPIXmlLineByLine(type, transaction,i);
							if (xmlTemplate != null) {
								//transaction.setXmldoc(xmlTemplate);
								String result = proxy.sendSynchronic(sessionID,
										xmlTemplate);
								WebServiceResult wsr = new WebServiceResult(
										result);
								if (wsr.parse()) {
									if (wsr.isSuccess()) {
										linesb.append("Line ").append(transaction.getLineItemNumber(i)).append(":").append(wsr.getResult()).append(";").append(lineItem.getValidationMessage());
									} else {
										linesb.append("Line ").append(transaction.getLineItemNumber(i)).append(" failed:").append(wsr.getError()).append(";").append(lineItem.getValidationMessage());
										isAllSuccess = false;
									}
								} else {
									linesb.append("Line ").append(transaction.getLineItemNumber(i)).append(" failed: system error, failed to parse result;").append(lineItem.getValidationMessage());
									isAllSuccess = false;
									
								}

							}
							lineItem.setXmldoc(xmlTemplate);
							lineItem.setProcess_message(linesb.toString());
							sb.append(linesb.toString());
						}
						
						if (isAllSuccess)
							transaction.setProcess_status(ProcessStatusEnum.COMPLETED.getProcessStatusName());
						else
							transaction.setProcess_status(ProcessStatusEnum.FAILED.getProcessStatusName());
						transaction.setProcess_message(sb.toString());
					} catch (Exception xe) {
						xe.printStackTrace();
						logger.log(
								Level.SEVERE,
								"InventoryLoader:load -- exception:"
										+ xe.getMessage());
						transaction.setProcess_status(ProcessStatusEnum.FAILED
								.getProcessStatusName());
						transaction.setProcess_message(xe.getMessage());
					}
					finally{
						transaction.setProcess_date(process_date);
						
					}
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
			logger.log(Level.SEVERE,
					"InventoryLoader:load -- " + xe.getMessage());
		}

		return isSuccess;
	}

	@Override
	public void setWebServiceAPI(WebServiceAPI _webServiceAPI)
			throws LoaderException {
		if (_webServiceAPI != null)
			this._webServiceAPI = _webServiceAPI;
		else
			throw new LoaderException(
					"InventoryReceivingLoader:setWebServiceAPI - web service api object can not be null");
	}

}
