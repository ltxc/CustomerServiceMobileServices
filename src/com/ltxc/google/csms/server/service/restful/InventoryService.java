package com.ltxc.google.csms.server.service.restful;


import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ltxc.google.csms.server.domain.Carrier;
import com.ltxc.google.csms.server.domain.Company;
import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.ShippingTransaction;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.domain.Warehouse;
import com.ltxc.google.csms.server.service.ILoader;
import com.ltxc.google.csms.server.service.LoaderFactory;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.SharedConstants;

@Path("/inventory")
public class InventoryService extends RestfulServiceBase {
	private static Logger logger = Logger
			.getLogger(InventoryService.class.getName());
	//Get Methods
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/inventorytransaction/{ipad_id}")
	public Response getInventoryTransaction(@Context HttpServletRequest req,@PathParam("ipad_id") String ipad_id)
	{
		super.checkRequest(req);
		InventoryTransaction inv = null;
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			inv = InventoryTransaction.findInventoryTransactionByIPadID(ipad_id);
			if(inv!=null)
				status = Response.Status.OK;
			else
				status = Response.Status.NO_CONTENT;
		}catch(Exception xe)
		{
			throwInternalErrorException(xe.getMessage());
		}
		Response.ResponseBuilder builder = Response.ok(inv!=null?inv:"", req.getContentType()).status(status);

		return builder.build();
		
	}
	
	
	//Post Methods
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/load")
	public Response postInventoryTransaction(@Context HttpServletRequest req,  InventoryTransaction inventoryTransaction)
	{
		super.checkRequest(req);

		if (inventoryTransaction == null)
		{
			throwInternalErrorException("Inventory Transaction Entity failed to be created from the http jason content.");
		}


		Date process_date = Calendar.getInstance().getTime();
		ProcessResult pr = new ProcessResult();
		pr.setTransactionId(inventoryTransaction.getIpad_id());
		pr.setTransactionType(inventoryTransaction.getTransaction_type());
		pr.setProcess_date(process_date);
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		
		
		try{
			//double check whether device id is null in the deviceRegister, otherwise, just set it to null
			User user = super.getUser(req);
			logger.info("InventoryService:postInventoryTransaction -- submitted by user id:"+user.getUserid()+". Start process inventory transaction with IPad_ID: "+inventoryTransaction.getIpad_id()+"; Transaction Type:"+inventoryTransaction.getTransaction_type()+"...");
			
			
				
			//first find whether there is one

			boolean isLoad = false;
			InventoryTransaction inv = InventoryTransaction.findInventoryTransactionByIPadID(inventoryTransaction.getIpad_id());
			long interval = 0;
			if(inv==null||((interval = inv.getLapsedSeconds())>SharedConstants.RESET_INTERVAL&&!ProcessStatusEnum.COMPLETED.getProcessStatusName().equalsIgnoreCase(inv.getProcess_status())))
			{
				isLoad = true;
				if(interval>SharedConstants.RESET_INTERVAL)
				{
					inv.remove();
				}
				
			}
			else
			{
				//just get the status
				isLoad = false;
				pr.setProcess_status(inv.getProcess_status());
				pr.setProcess_message(inv.getProcess_message());
				pr.setProcess_date(inv.getProcess_date());
			}
			
			if(isLoad)
			{
				//populate the reference one
				if(inventoryTransaction.getCarrier()!=null)
					inventoryTransaction.setCarrier(findCarrierById(inventoryTransaction.getCarrier().getCarrier_id()));
				if(inventoryTransaction.getCompany()!=null)
					inventoryTransaction.setCompany(findCompanyById(inventoryTransaction.getCompany().getCompany_id()));
				if(inventoryTransaction.getFr_warehouse()!=null)
					inventoryTransaction.setFr_warehouse(findWarehouseById(inventoryTransaction.getFr_warehouse().getWarehouse_id()));
				if(inventoryTransaction.getTo_warehouse()!=null)
					inventoryTransaction.setTo_warehouse(findWarehouseById(inventoryTransaction.getTo_warehouse().getWarehouse_id()));
			
				
				if(inventoryTransaction.getCreatedDate()==null)
					inventoryTransaction.setCreatedDate(process_date);
				if(inventoryTransaction.getProcessDate()==null)
					inventoryTransaction.setProcessDate(process_date);
				
				//"Process %s transaction with type:%s and ipad_id:%s at server time %s. Status:%s. Message:%s.

			      if(inventoryTransaction.getInventoryLineItems().size()>0)
			      {
			    	  for(InventoryLineItem lineItem:inventoryTransaction.getInventoryLineItems())
			    	  {
			    		  lineItem.setInventoryHeader(inventoryTransaction);
			    	  }
			 
			      }
				//load now
				ILoader loader = LoaderFactory.get().getLoader(inventoryTransaction.getTransactionType());
				if(loader!=null)
				{
					//create InventoryTransaction

					loader.load(inventoryTransaction);
					inventoryTransaction.update();
					pr.setProcess_status(inventoryTransaction.getProcess_status());
					pr.setProcess_message(inventoryTransaction.getProcess_message());

				}
				else
				{
					super.throwInternalErrorException("Failed to find the loader for transaction: "+inventoryTransaction.toString());
				}
			}


			status = Response.Status.OK;
			
			
		}catch(Exception xe)
		{
			throwInternalErrorException(xe.getMessage());
		}
		Response.ResponseBuilder builder = Response.ok(pr, MediaType.APPLICATION_JSON).status(status);

		return builder.build();
	}
	
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/result")
	public Response getResult(@Context HttpServletRequest req,  @QueryParam("ipadid") String ipad_id)
	{
		TransactionService transactionService = new TransactionService(SharedConstants.RESET_INTERVAL);
		InventoryTransaction inventoryTransaction = new InventoryTransaction();
		inventoryTransaction.setIpad_id(ipad_id);
		inventoryTransaction.setActioncode(0);
		Response response = transactionService.processTransaction(this, req, inventoryTransaction,new ITransactionService() {
			
//			@Override
//			public TransactionBase searchExistingTransaction(TransactionBase transaction) {
//				return InventoryTransaction.findInventoryTransactionByIPadID(transaction.getTransactionID());
//			}
//			
			@Override
			public void preLoad(TransactionBase transaction, ProcessResult processResult) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postLoad(TransactionBase transaction,
					ProcessResult processResult) {
				// TODO Auto-generated method stub
				
			}
		});
		return response;
	}
	
/*************** help methods ************************/
	
	private Warehouse findWarehouseById(String warehouse_id)
	{
		if(warehouse_id!=null)
			return Warehouse.findWarehouseByWarehouseID(warehouse_id);
		else
			return null;		
	}
	
	private Company findCompanyById(String company_id)
	{
		if(company_id!=null)
			return Company.findCompanyByCompanyID(company_id);
		else
			return null;
	}
	
	private Carrier findCarrierById(String carrier_id)
	{
		if(carrier_id!=null)
			return Carrier.findCarrierByCarrierID(carrier_id);
		else
			return null;
	}
	
	

	
}
