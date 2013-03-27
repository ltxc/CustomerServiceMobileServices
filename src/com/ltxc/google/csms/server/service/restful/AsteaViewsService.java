package com.ltxc.google.csms.server.service.restful;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ltxc.google.csms.server.domain.ViewRepairInfo;
import com.ltxc.google.csms.shared.SharedConstants;

@Path("/views")
public class AsteaViewsService extends RestfulServiceBase {
	private static Logger logger = Logger
			.getLogger(AsteaViewsService.class.getName());
	
	//Get methods
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/repairinfo")
	public Response getRepairInfo(@Context HttpServletRequest req,@QueryParam("request_id") String request_id )
	{
		super.checkRequest(req);


		List<ViewRepairInfo> list = new ArrayList<ViewRepairInfo>();
		Status status = Response.Status.INTERNAL_SERVER_ERROR; //Internal Server Error
		try{
			list = ViewRepairInfo.findViewRepairInfoEntriesByrequest_id(request_id);
			status = Response.Status.OK;
		}catch(Exception xe)
		{
			logger.log(Level.SEVERE, "AsteaViewsService:getRepairInfo - Error:"+xe.getMessage());
			throwInternalErrorException(xe.getMessage());
		}

		Response.ResponseBuilder builder = null;
		if(list==null||list.size()<=0)
		{
			
			builder = Response.ok("{}", req.getContentType()).status(status);
		}
		else
		{
		GenericEntity<List<ViewRepairInfo>> entity = new
				GenericEntity<List<ViewRepairInfo>>(
								list) {
						};
			builder = Response.ok(entity, req.getContentType()).status(status);
		}
		return builder.build();
	}

}
