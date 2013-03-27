package com.ltxc.google.csms.server.service.restful;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



/**
 * Custom Jersey security filter login exception class
 * @author JLU
 *
 */
@SuppressWarnings("serial")
public class RestfulLoginException extends WebApplicationException {
	public RestfulLoginException() {
		super(Response.status(401).type(MediaType.TEXT_PLAIN_TYPE).build());
	}
	
	public RestfulLoginException(String message) {
		super(Response.status(401).entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());
	}
	
	public RestfulLoginException(String message, Status status)
	{
		super(Response.status(status).entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());
	}
	
}
