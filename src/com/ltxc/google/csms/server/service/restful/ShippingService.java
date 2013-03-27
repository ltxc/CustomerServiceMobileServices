package com.ltxc.google.csms.server.service.restful;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.ShippingLineItem;
import com.ltxc.google.csms.server.domain.ShippingTransaction;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.service.ILoader;
import com.ltxc.google.csms.server.service.LoaderFactory;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.SharedConstants;

@Path("/shipping")
public class ShippingService extends RestfulServiceBase {
	private static Logger logger = Logger
			.getLogger(ShippingService.class.getName());
//	@POST
//	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	@Path("/load")
//	public Response postShippingTransaction(@Context HttpServletRequest req,  ShippingTransaction shippingTransaction)
//	{
//		super.checkRequest(req);
//
//		if (shippingTransaction == null)
//		{
//			String message = "Shipping Transaction Entity failed to be created from the http jason content.";
//			logger.log(Level.SEVERE, "ShippingService:getShippingList - Error:"+message);
//			throwInternalErrorException(message);
//		}
//
//
//		Date process_date = Calendar.getInstance().getTime();
//		ProcessResult pr = new ProcessResult();
//		pr.setTransactionId(shippingTransaction.getIpad_id());
//		pr.setTransactionType(shippingTransaction.getTransaction_type());
//		pr.setProcess_date(process_date);
//		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
//		
//		
//		try{
//			//double check whether device id is null in the deviceRegister, otherwise, just set it to null
//			User user = super.getUser(req);
//			logger.info("ShippingService:getShippingList -- submitted by user id:"+user.getUserid()+". Start process shipping transaction with IPad_ID: "+shippingTransaction.getIpad_id()+"; Transaction Type:"+shippingTransaction.getTransaction_type()+"...");
//			
//			
//				
//			//first find whether there is one
//
//			boolean isLoad = false;
//			ShippingTransaction shp = ShippingTransaction.findShippingTransactionByIPadID(shippingTransaction.getIpad_id());
//			long interval = 0;
//			if(shp==null||((interval = shp.getLapsedMinutes())>SharedConstants.RESET_INTERVAL&&!ProcessStatusEnum.COMPLETED.getProcessStatusName().equalsIgnoreCase(shp.getProcess_status())))
//			{
//				isLoad = true;
//				if(interval>SharedConstants.RESET_INTERVAL)
//				{
//					shp.remove();
//				}
//				
//			}
//			else
//			{
//				//just get the status
//				isLoad = false;
//				pr.setProcess_status(shp.getProcess_status());
//				pr.setProcess_message(shp.getProcess_message());
//				pr.setProcess_date(shp.getProcess_date());
//			}
//			
//			if(isLoad)
//			{
//
//				
//				//"Process %s transaction with type:%s and ipad_id:%s at server time %s. Status:%s. Message:%s.
//
//			      if(shippingTransaction.getShippingLineItems().size()>0)
//			      {
//			    	  int i = 1;
//			    	  for(ShippingLineItem lineItem:shippingTransaction.getShippingLineItems())
//			    	  {
//			    		  lineItem.setShippingHeader(shippingTransaction);
//			    		  lineItem.setLineitemnumber(i);
//			    		  i = i + 1;
//			    	  }
//			    	 
//			      }
//				//load now
//				ILoader loader = LoaderFactory.get().getLoader(shippingTransaction.getTransactionType());
//				if(loader!=null)
//				{
//					//create ShippingTransaction
//
//					loader.load(shippingTransaction);
//					shippingTransaction.update();
//					pr.setProcess_status(shippingTransaction.getProcess_status());
//					pr.setProcess_message(shippingTransaction.getProcess_message());
//
//				}
//				else
//				{
//					String message = "Failed to find the loader for shipping transaction: "+shippingTransaction.toString();
//					logger.log(Level.SEVERE, "ShippingService:getShippingList - Error:"+message);
//					super.throwInternalErrorException(message);
//				}
//			}
//
//
//			status = Response.Status.OK;
//			
//			
//		}catch(Exception xe)
//		{
//			logger.log(Level.SEVERE, "ShippingService:getShippingList - Error:"+xe.getMessage());
//			throwInternalErrorException(xe.getMessage());
//		}
//		Response.ResponseBuilder builder = Response.ok(pr, MediaType.APPLICATION_JSON).status(status);
//
//		return builder.build();
//	}
	

		@POST
		@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
		@Path("/loadall")
		public Response postShippingTransactionAll(@Context HttpServletRequest req,  ShippingTransaction shippingTransaction)
		{
				TransactionService transactionService = new TransactionService(SharedConstants.RESET_INTERVAL);
				Response response = transactionService.processTransaction(this, req, shippingTransaction, new ITransactionService() {
					
					@Override
					public TransactionBase searchExistingTransaction(TransactionBase transaction) {
						ShippingTransaction shippingTransaction = (ShippingTransaction)transaction;
						return ShippingTransaction.findShippingTransactionByIPadID(shippingTransaction.getIpad_id());
						
					}
					
					@Override
					public void preLoad(TransactionBase transaction,
							ProcessResult processResult) {	
						ShippingTransaction shippingTransaction = (ShippingTransaction)transaction;
						if(shippingTransaction.getShippingLineItems().size()>0)
					      {
					    	  int i = 1;
					    	  for(ShippingLineItem lineItem:shippingTransaction.getShippingLineItems())
					    	  {
					    		  lineItem.setShippingHeader(shippingTransaction);
					    		  lineItem.setLineitemnumber(i);
					    		  i = i + 1;
					    	  }
					    	  
					      }
					}

					@Override
					public void postLoad(TransactionBase transaction,
							ProcessResult processResult) {
						// Leave intentionally, do nothing.
						
					}
				});
				return response;
		}
	
}
