package com.ltxc.google.csms.server.service.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ltxc.google.csms.server.domain.ApplicationData;
import com.ltxc.google.csms.server.domain.BinPart;
import com.ltxc.google.csms.server.domain.Carrier;
import com.ltxc.google.csms.server.domain.Company;
import com.ltxc.google.csms.server.domain.CycleCount;
import com.ltxc.google.csms.server.domain.CycleCountMaster;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ManAdjustReason;
import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.Queries;
import com.ltxc.google.csms.server.domain.ShipmentInstructions;
import com.ltxc.google.csms.server.domain.TransactionBase;
//import com.ltxc.google.csms.server.domain.ShippingList;
import com.ltxc.google.csms.server.domain.ViewShippingList;
import com.ltxc.google.csms.server.domain.Warehouse;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

@Path("/synch")
public class SynchService extends RestfulServiceBase {
	private static Logger logger = Logger
			.getLogger(SynchService.class.getName());
	//Get methods
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/applicationdata")
	public Response getApplicationData(@Context HttpServletRequest req)
	{
		super.checkRequest(req);

		List<ApplicationData> list = new ArrayList<ApplicationData>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = ApplicationData.findAllApplicationDatas();
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "getApplicationData - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}

		
		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
			GenericEntity<List<ApplicationData>> entity = new
				GenericEntity<List<ApplicationData>>(
								list) {
						};
						builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/processresult")
	public Response getProcessResult(@Context HttpServletRequest req,  @QueryParam("ipadid") String ipad_id, @QueryParam("type") String type)
	{
		TransactionTypeEnum tType = TransactionTypeEnum.NONE;
		Response response = null;
		try{
			tType = TransactionTypeEnum.parse(type);
			
		}catch(Exception xe)
		{
			super.throwInternalErrorException("Invalid transaction type "+type+". Please contact system adminstrator.");
		}
		
		TransactionService transactionService = new TransactionService(SharedConstants.RESET_INTERVAL);
		TransactionBase transaction = TransactionFactory.get().generate(tType);
		transaction.setIpad_id(ipad_id);
		transaction.setProcessActionCode(0);
		response = transactionService.processTransaction(this, req, transaction,new ITransactionService() {
			
//			@Override
//			public TransactionBase searchExistingTransaction(TransactionBase transaction) {
//				return InventoryTransaction.findInventoryTransactionByIPadID(transaction.getTransactionID());
//			}
			
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
	
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/company")
	public Response getCompany(@Context HttpServletRequest req,@QueryParam("firstresult") int firstResult,@QueryParam("maxresult") int maxResults )
	{
		super.checkRequest(req);

		if(firstResult<0)
			firstResult = 0;
		if(maxResults<=0)
			maxResults = SharedConstants.MAX_RESULTS;
		List<Company> list = new ArrayList<Company>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = Company.findCompanyEntries(firstResult, maxResults);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getCompany - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<Company>> entity = new
				GenericEntity<List<Company>>(
								list) {
						};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/carrier")
	public Response getCarrier(@Context HttpServletRequest req,@QueryParam("firstresult") int firstResult,@QueryParam("maxresult") int maxResults )
	{
		super.checkRequest(req);

		if(firstResult<0)
			firstResult = 0;
		if(maxResults<=0)
			maxResults = SharedConstants.MAX_RESULTS;
		List<Carrier> list = new ArrayList<Carrier>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = Carrier.findCarrierEntries(firstResult, maxResults);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getCarrier - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}

		
		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<Carrier>> entity = new
				GenericEntity<List<Carrier>>(
								list) {
						};
		builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/warehouse")
	public Response getWarehouse(@Context HttpServletRequest req,@QueryParam("firstresult") int firstResult,@QueryParam("maxresult") int maxResults )
	{
		
		super.checkRequest(req);

		if(firstResult<0)
			firstResult = 0;
		if(maxResults<=0)
			maxResults = SharedConstants.MAX_RESULTS;
		List<Warehouse> list = new ArrayList<Warehouse>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = Warehouse.findWarehouseEntries(firstResult, maxResults);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getWarehouse - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}
		

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<Warehouse>> entity = new
				GenericEntity<List<Warehouse>>(
								list) {
						};

		
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();

	}
	

	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/binpart")
	public Response getBinPart(@Context HttpServletRequest req,@QueryParam("warehouseid") String warehouse_id, @QueryParam("firstresult") int firstResult,@QueryParam("maxresult") int maxResults, @QueryParam("lastdate") DateParam lastDate )
	{
		super.checkRequest(req);

		if(firstResult<0)
			firstResult = 0;
		if(maxResults<=0)
			maxResults = SharedConstants.MAX_RESULTS;
		List<BinPart> list = new ArrayList<BinPart>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = BinPart.findBinPartEntriesByWarehouseId(warehouse_id,firstResult, maxResults,lastDate);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getBinPart - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<BinPart>> entity = new
				GenericEntity<List<BinPart>>(
								list) {
						};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}
	//Get methods
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/manadjustreason")
	public Response getManAdjustReason(@Context HttpServletRequest req,@QueryParam("firstresult") int firstResult,@QueryParam("maxresult") int maxResults )
	{
		super.checkRequest(req);

		if(firstResult<0)
			firstResult = 0;
		if(maxResults<=0)
			maxResults = SharedConstants.MAX_RESULTS;
		List<ManAdjustReason> list = new ArrayList<ManAdjustReason>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = ManAdjustReason.findManAdjustReasonEntries(firstResult, maxResults);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getManAdjustReason - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<ManAdjustReason>> entity = new
				GenericEntity<List<ManAdjustReason>>(
								list) {
						};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}
	
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/queries")
	public Response getQueries(@Context HttpServletRequest req,@QueryParam("firstresult") int firstResult,@QueryParam("maxresult") int maxResults )
	{
		super.checkRequest(req);

		if(firstResult<0)
			firstResult = 0;
		if(maxResults<=0)
			maxResults = SharedConstants.MAX_RESULTS;
		List<Queries> list = new ArrayList<Queries>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = Queries.findQueriesEntries(firstResult, maxResults);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getQueries - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}
		

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<Queries>> entity = new
				GenericEntity<List<Queries>>(
								list) {
						};
		builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}
	
	
	//Cycle Count Master
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/cyclecountmaster")
	public Response getCycleCountMaster(@Context HttpServletRequest req,@QueryParam("warehouseid") String warehouseID )
	{
		super.checkRequest(req);


		List<CycleCountMaster> list = null;
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = CycleCountMaster.findCycleCountMasterEntriesBywarehouseId(warehouseID.trim().toUpperCase(), 1, 0);
			//list = CycleCountMaster.findAllCycleCountMasters();
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getCycleCountMaster - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}
		
		Response.ResponseBuilder builder = null;

		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
			GenericEntity<List<CycleCountMaster>> entity = new
					GenericEntity<List<CycleCountMaster>>(
									list) {
							};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		

		return builder.build();
	}
	
	//Cycle Count Master
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/cyclecount")
	public Response getCycleCount(@Context HttpServletRequest req,@QueryParam("warehouseid") String warehouseID )
	{
		super.checkRequest(req);


		List<CycleCount> list = null;
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = CycleCount.findCycleCountEntriesByWarehouseID(warehouseID.trim().toUpperCase());
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getCycleCount - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}
		
		Response.ResponseBuilder builder = null;

		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
			GenericEntity<List<CycleCount>> entity = new
					GenericEntity<List<CycleCount>>(
									list) {
							};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}

		return builder.build();
	}
	
//	//Shipping List
//	@GET
//	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//	@Path("/shippinglist")
//	public Response getShippingList(@Context HttpServletRequest req,@QueryParam("warehouseid") String warehouseID )
//	{
//		super.checkRequest(req);
//
//
//		List<ShippingList> list = null;
//		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
//		try{
//			list = ShippingList.findShippingListEntriesByFrWarehouseID(warehouseID.trim().toUpperCase());
//			status = Response.Status.OK;
//		}catch(Exception xe)
//		{
//			logger.log(Level.SEVERE, "SynchService:getShippingList - Error:"+xe.getMessage());
//			throwInternalErrorException(xe.getMessage());
//		}
//		
//		Response.ResponseBuilder builder = null;
//
//		if(list==null||list.size()<=0)
//		{
//			
//			builder = Response.ok("{}", req.getContentType()).status(status);
//		}
//		else
//		{
//			GenericEntity<List<ShippingList>> entity = new
//					GenericEntity<List<ShippingList>>(
//									list) {
//							};
//			builder = Response.ok(entity, req.getContentType()).status(status);
//		}
//		
//
//		return builder.build();
//	}
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/shippinglistview")
	public Response getShippingListView(@Context HttpServletRequest req,@QueryParam("warehouseid") String warehouseID )
	{
		super.checkRequest(req);


		List<ViewShippingList> list = null;
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = ViewShippingList.findViewShippingListInfoEntriesByrequest_id(warehouseID.trim().toUpperCase());
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getShippingList - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}
		
		Response.ResponseBuilder builder = null;

		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
			GenericEntity<List<ViewShippingList>> entity = new
					GenericEntity<List<ViewShippingList>>(
									list) {
							};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		

		return builder.build();
	}
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/shipmentinstructions")
	public Response getShipmentInstrunctions(@Context HttpServletRequest req)
	{
		
		super.checkRequest(req);

		List<ShipmentInstructions> list = new ArrayList<ShipmentInstructions>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = ShipmentInstructions.findAllShipmentInstructionss();
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "SynchService:getShipmentInstructions - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}
		

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<ShipmentInstructions>> entity = new
				GenericEntity<List<ShipmentInstructions>>(
								list) {
						};

		
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();

	}
	
}
