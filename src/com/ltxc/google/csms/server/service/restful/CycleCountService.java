package com.ltxc.google.csms.server.service.restful;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.ltxc.google.csms.server.domain.Carrier;
import com.ltxc.google.csms.server.domain.Company;
import com.ltxc.google.csms.server.domain.CycleCount;
import com.ltxc.google.csms.server.domain.CycleCountCount;
import com.ltxc.google.csms.server.domain.CycleCountMaster;
import com.ltxc.google.csms.server.domain.CycleCountPost;
import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.domain.Warehouse;
import com.ltxc.google.csms.server.service.ILoader;
import com.ltxc.google.csms.server.service.LoaderFactory;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.SharedConstants;

@Path("/cyclecount")
public class CycleCountService extends RestfulServiceBase {
	private static Logger logger = Logger
			.getLogger(CycleCountService.class.getName());
	
/************************************* Post Methods ********************************************************/
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/bin/{who}/{warehouseid}/{bincode}/{clear}")
	public Response postBinCycleCount(@Context HttpServletRequest req,  List<CycleCountCount> cycleCounts, @PathParam("who") String who,@PathParam("warehouseid") String warehouse_id,@PathParam("bincode") String bin_code_id, @PathParam("clear") String isclear)
	{
		super.checkRequest(req);
		if (cycleCounts == null)
		{
			throwInternalErrorException("Bin Cycle Counts Entity failed to be created from the http jason content.");
		}
		return handleBinCycleCount(req, cycleCounts, who, warehouse_id, bin_code_id, isclear);
	}
	
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/part/{who}/{warehouseid}/{bincode}/{bpart}/{clear}")
	public Response postPartCycleCount(@Context HttpServletRequest req,  List<CycleCountCount> cycleCounts, @PathParam("who") String who,@PathParam("warehouseid") String warehouse_id,@PathParam("bincode") String bin_code_id,@PathParam("bpart") String bpart_id, @PathParam("clear") String isclear)
	{
		super.checkRequest(req);
		if (cycleCounts == null)
		{
			throwInternalErrorException("Part Cycle Counts Entity failed to be created from the http jason content.");
		}
		return handlePartCycleCount(req, cycleCounts, who, warehouse_id, bin_code_id, bpart_id, isclear);
	}
	
	
	
	@POST
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/load")
	public Response postCycleCount(@Context HttpServletRequest req,  CycleCountPost cycleCountPost)
	{
		super.checkRequest(req);
		if (cycleCountPost == null)
		{
			throwInternalErrorException("Cycle Count Post Entity failed to be created from the http jason content.");
		}

		
		
		String cycle_type = cycleCountPost.getCycleType();
		String who = cycleCountPost.getWho();
		String warehouse_id = cycleCountPost.getWarehouseId();
		String bin_code_id = cycleCountPost.getBinCodeId();
		List<CycleCountCount> counts = cycleCountPost.getCounts();
		String isclear = cycleCountPost.getIsclear();
		Response response = null;
		if (SharedConstants.CYCLE_TYPE_BIN.equals(cycle_type))
		{
			response = handleBinCycleCount(req, counts, who, warehouse_id, bin_code_id, isclear);
		}
		else if (SharedConstants.CYCLE_TYPE_PART.equals(cycle_type))
		{
			String bpart_id = cycleCountPost.getBpartId();
			response = handlePartCycleCount(req, counts, who, warehouse_id, bin_code_id, bpart_id, isclear);
		}
		else
		{
			throwInternalErrorException("Unknow Cycle Count Type ("+cycle_type+") failed to be created from the http jason content.");
			
		}
		
		return response;
	}
	
	
	
	
	
	
	
/***************************** help method *****************************************************/	
	public Response handleBinCycleCount(HttpServletRequest req,  List<CycleCountCount> cycleCounts, String who,String warehouse_id,String bin_code_id, String isclear)
	{
		String type = SharedConstants.CYCLE_TYPE_BIN;
		boolean isCleared = false;
		Date process_date = Calendar.getInstance().getTime();
		ProcessResult pr = new ProcessResult();
		pr.setTransactionId(who);
		pr.setTransactionType(type);
		pr.setProcess_date(process_date);
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		
		StringBuilder sb = new StringBuilder();
		try{
			//double check whether device id is null in the deviceRegister, otherwise, just set it to null
			User user = super.getUser(req);
			logger.info("InventoryService:postInventoryTransaction -- submitted by user id:"+user.getUserid()+". Start to insert "+type+" cycle counted by "+who+"...");
			

			//check whether clear
			if(isclear!=null&&isclear.equalsIgnoreCase("Y"))
			{
				//clear the record
				CycleCount.clearCycleBinCount(warehouse_id, bin_code_id);
				
				isCleared = true;
			}
				
			boolean isLoad = true;		
			if(cycleCounts==null)
			{
				isLoad = false;
				sb.append(SharedConstants.Exception_CycleCount_NoCount);
			}
			else
			{
			for(CycleCountCount count:cycleCounts)
			{
				try{
					CycleCount cycleCount = new CycleCount(warehouse_id, bin_code_id, who, count);
					cycleCount.setLastupdated(process_date);
					CycleCount.saveCycleCountEntry(cycleCount,!isCleared);
					
				}catch(Exception xe)
				{
					isLoad = false;
					sb.append(xe.getMessage()).append(";");
				}
			}
			}
			if(!isLoad)
			{
				pr.setProcess_message(sb.toString());
				pr.setProcess_status(ProcessStatusEnum.FAILED.getProcessStatusName());
			}
			else
			{
				pr.setProcess_message("Cycle Count (Bin:"+bin_code_id+") loaded successfully...");
				pr.setProcess_status(ProcessStatusEnum.COMPLETED.getProcessStatusName());
				//now update the cycle count master
				CycleCountMaster.updateCycleCountMasterBinCounted(warehouse_id, bin_code_id);
			}

			status = Response.Status.OK;			
			
		}catch(Exception xe)
		{
			
			//throwInternalErrorException(xe.getMessage());
			sb.append("Exception occurred. Error:"+xe.getMessage());
			pr.setProcess_message(sb.toString());
			pr.setProcess_status(ProcessStatusEnum.FAILED.getProcessStatusName());

		}
		Response.ResponseBuilder builder = Response.ok(pr, MediaType.APPLICATION_JSON).status(status);

		return builder.build();
	}
	
	public Response handlePartCycleCount(HttpServletRequest req,  List<CycleCountCount> cycleCounts, String who,String warehouse_id,String bin_code_id,String bpart_id, String isclear)
	{
		


		String type = SharedConstants.CYCLE_TYPE_PART;		
		boolean isCleared = false;
		Date process_date = Calendar.getInstance().getTime();
		ProcessResult pr = new ProcessResult();
		pr.setTransactionId(who);
		pr.setTransactionType(type);
		pr.setProcess_date(process_date);
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		
		StringBuilder sb = new StringBuilder();
		try{
			//double check whether device id is null in the deviceRegister, otherwise, just set it to null
			User user = super.getUser(req);
			logger.info("InventoryService:postInventoryTransaction -- submitted by user id:"+user.getUserid()+". Start to insert "+type+" cycle counted by "+who+"...");
			

			//check whether clear
			if(isclear!=null&&isclear.equalsIgnoreCase("Y"))
			{
				//clear the record
				CycleCount.clearCyclePartCount(warehouse_id, bin_code_id, bpart_id);
				
				isCleared = true;
			}
				
			boolean isLoad = true;		
			if(cycleCounts==null)
			{
				isLoad = false;
				sb.append(SharedConstants.Exception_CycleCount_NoCount);
			}
			else
			{
			for(CycleCountCount count:cycleCounts)
			{
				try{
					CycleCount cycleCount = new CycleCount(warehouse_id, bin_code_id,bpart_id, who, count);
					cycleCount.setLastupdated(process_date);
					CycleCount.saveCycleCountEntry(cycleCount,!isCleared);
					
				}catch(Exception xe)
				{
					isLoad = false;
					sb.append(xe.getMessage()).append(";");
				}
			}
			}
			if(!isLoad)
			{
				pr.setProcess_message(sb.toString());
				pr.setProcess_status(ProcessStatusEnum.FAILED.getProcessStatusName());
			}
			else
			{
				pr.setProcess_message("Cycle Count (Bin:"+bin_code_id+"; Part:"+bpart_id+") loaded successfully...");
				pr.setProcess_status(ProcessStatusEnum.COMPLETED.getProcessStatusName());
				//now update the cycle count master
				CycleCountMaster.updateCycleCountMasterPartCounted(warehouse_id, bin_code_id, bpart_id);
			}

			status = Response.Status.OK;			
			
		}catch(Exception xe)
		{
			
			//throwInternalErrorException(xe.getMessage());
			sb.append("Exception occurred. Error:"+xe.getMessage());
			pr.setProcess_message(sb.toString());
			pr.setProcess_status(ProcessStatusEnum.FAILED.getProcessStatusName());

		}
		Response.ResponseBuilder builder = Response.ok(pr, MediaType.APPLICATION_JSON).status(status);

		return builder.build();
	}
	
}
