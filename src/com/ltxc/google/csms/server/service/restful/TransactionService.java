package com.ltxc.google.csms.server.service.restful;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.service.ILoader;
import com.ltxc.google.csms.server.service.LoaderFactory;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.SharedConstants;

public class TransactionService {
	private long resetInterval = SharedConstants.RESET_INTERVAL;
	
	private static Logger logger = Logger
			.getLogger(TransactionService.class.getName());
	public TransactionService(long resetInterval)
	{
		
		this.resetInterval = resetInterval;
	}



	public long getResetInterval() {
		return resetInterval;
	}


	public void setResetInterval(long resetInterval) {
		this.resetInterval = resetInterval;
	}







	/**
	 * this one implement the pattern to avoid duplication transaction and AA service api load.
	 * @param restfulServiceBase
	 * @param request
	 * @param transaction
	 * @param transactionService
	 * @return
	 */
	public Response processTransaction(RestfulServiceBase restfulServiceBase, HttpServletRequest request, TransactionBase transaction, ITransactionService transactionService)
	{
		if (transaction == null)
		{
			String message = "Transaction Entity failed to be created from the http jason content.";
			logger.log(Level.SEVERE, "TransactionService:processTransaction - Error:"+message);
			restfulServiceBase.throwInternalErrorException(message);
		}

		if (transactionService == null)
		{
			String message = "Transaction Service Interface is not initialized...";
			logger.log(Level.SEVERE, "TransactionService:processTransaction - Error:"+message);
			restfulServiceBase.throwInternalErrorException(message);
		}

		Date process_date = Calendar.getInstance().getTime();
		ProcessResult pr = new ProcessResult();
		pr.setTransactionId(transaction.getTransactionID());
		pr.setTransactionType(transaction.getTransactionType().getTransactionTypeName());
		pr.setProcess_date(process_date);
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		
		
		try{
			//double check whether device id is null in the deviceRegister, otherwise, just set it to null
			User user = restfulServiceBase.getUser(request);
			logger.info("TransactionService:processTransaction -- submitted by user id:"+user.getUserid()+". Transaction Type:"+transaction.getTransactionType().getTransactionTypeName()+"...");
			
			
				
			//first find whether there is one

			boolean isLoad = false;
			TransactionBase tbExist = transactionService.searchExistingTransaction(transaction);
			long interval = 0;
			if(tbExist==null||((interval = this.getLapsedMinutes(tbExist.getProcessDate()))>this.resetInterval&&!ProcessStatusEnum.COMPLETED.getProcessStatusName().equalsIgnoreCase(tbExist.getProcessStatus())))
			{
				isLoad = true;
				if(interval>this.resetInterval)
				{
					tbExist.remove();
				}
				
			}
			else
			{
				//just get the status
				isLoad = false;
				pr.setProcess_status(tbExist.getProcessStatus());
				pr.setProcess_message(tbExist.getProcessMessage());
				pr.setProcess_date(tbExist.getProcessDate());
				
			}
			
			if(isLoad)
			{
				transactionService.preLoad(transaction, pr);
				
				//load now
				ILoader loader = LoaderFactory.get().getLoader(transaction.getTransactionType());
				if(loader!=null)
				{
					//create transaction

					loader.load(transaction);
					transaction.update();
					pr.setProcess_status(transaction.getProcessStatus());
					pr.setProcess_message(transaction.getProcessMessage());

				}
				else
				{
					String message = "Failed to find the loader for shipping transaction: "+transaction.toString();
					logger.log(Level.SEVERE, "TransactionService:processTransaction - Error:"+message);
					restfulServiceBase.throwInternalErrorException(message);
				}
				transactionService.postLoad(transaction, pr);
			}


			status = Response.Status.OK;
			
			
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "TransactionService:processTransaction - Error:"+xe.getMessage());
			restfulServiceBase.throwInternalErrorException(xe.getMessage());
		}
		Response.ResponseBuilder builder = Response.ok(pr, MediaType.APPLICATION_JSON).status(status);

		return builder.build();
		
		
	}
	
	
	public long getLapsedMinutes(Date process_date)
	{
		long lapsedMinutes = 0;
		if(process_date!=null)
		{
			Date current = Calendar.getInstance().getTime();
			lapsedMinutes = (current.getTime()-process_date.getTime())/SharedConstants.DATE_SECOND;
		}
		
		return lapsedMinutes;		
	}
}
