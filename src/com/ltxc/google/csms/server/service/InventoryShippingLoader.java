package com.ltxc.google.csms.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.aa.api.WebServiceSoap;
import com.ltxc.google.csms.server.domain.ShippingLineItem;
//import com.ltxc.google.csms.server.domain.ShippingList;
import com.ltxc.google.csms.server.domain.ShippingTransaction;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.server.resource.TemplateLoaderFactory;
import com.ltxc.google.csms.server.service.AAWebServiceAPI.WebServiceAPIDelegate;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class InventoryShippingLoader implements ILoader {
	private static Logger logger = Logger
			.getLogger(InventoryShippingLoader.class.getName());
	private AAWebServiceAPI _webServiceAPI;
	@Override
	public boolean load(TransactionBase tb) {
		boolean isSuccess = false;
		final Date process_date = Calendar.getInstance().getTime();
		final ShippingTransaction transaction = (ShippingTransaction) tb;
		try {
			if (transaction == null)
				throw new LoaderException(
						"InventoryMSRLoader:load - transaction object is not set yet...");
			//set loading status
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
						//no validation required
						
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
						// send all once
						String xmlTemplate = TemplateLoaderFactory.get()
								.getLoader(type)
								.generateAPIXml(type, transaction);
						StringBuilder sb = new StringBuilder();
						if (xmlTemplate != null) {
							transaction.setXmldoc(xmlTemplate);
							String result = proxy.sendSynchronic(sessionID,
									xmlTemplate);
							AAWebServiceResult wsr = new AAWebServiceResult(
									result);
							if (wsr.parse(null)) {
								if (wsr.isSuccess()) {
									sb.append(":").append(wsr.getResult()).append(";");
									isSuccess = true;
								} else {
									sb.append(" failed:").append(wsr.getError()).append(";");
									
								}
							} else {
								sb.append(" failed: system error, failed to parse result;").append(wsr.getResult());
							}
						}
						
						
						
						if (isSuccess)
						{
//							//now loop through lineitems to update the ship list table
//							if(transaction.getLineItems()!=null)
//							{
//								List<ShippingLineItem> list = transaction.getShippingLineItems();
//								for(ShippingLineItem item:list)
//								{
//									try{
//										ShippingList.updateShipListStatus(item.getShippinglist_id());
//										
//									}catch(Exception se)
//									{
//										logger.log(Level.WARNING, "Shipping Transaction with id("+transaction.getId()+") Update ShippingList item ("+item.getShippinglist_id()+") status failed. Error:"+se.getMessage());
//									}
//									
//								}
//							}
							
							transaction.setProcess_status(ProcessStatusEnum.COMPLETED.getProcessStatusName());
						
						}
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
					"InventoryShippingLoader:load -- " + xe.getMessage());
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
					"InventoryShippingLoader:setWebServiceAPI - web service api object can not be null");


	}

}
