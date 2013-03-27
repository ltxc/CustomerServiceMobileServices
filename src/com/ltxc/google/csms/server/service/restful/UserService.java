package com.ltxc.google.csms.server.service.restful;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ltxc.google.csms.server.domain.Queries;
import com.ltxc.google.csms.server.domain.User;


@Path("/user")
public class UserService extends RestfulServiceBase {
	private static Logger logger = Logger
			.getLogger(UserService.class.getName());	
	
	@GET
	@Consumes ({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/myaccount")
	public Response getMyAccount(@Context HttpServletRequest req)
	{
		super.checkRequest(req);	
		String contentType = req.getContentType();
		
		User user =  super.getUser(req);
		if(user==null)
			super.throwInternalErrorException("Session expired. Can not get user object from session...");
		Status status = Response.Status.OK;
		Response.ResponseBuilder builder = Response.ok(user, contentType).status(status);

		return builder.build();
		
	}
}
