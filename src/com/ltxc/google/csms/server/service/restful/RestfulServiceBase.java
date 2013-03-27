package com.ltxc.google.csms.server.service.restful;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.servlet.utils.Authenticator;
import com.ltxc.google.csms.server.servlet.utils.LoginHelper;
import com.ltxc.google.csms.shared.SharedConstants;





public abstract class RestfulServiceBase {
	protected void throwInternalErrorException(String message)
	{
		throw new WebApplicationException(Response.ok().status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).type(MediaType.TEXT_PLAIN_TYPE).build());
	}
	
	protected void checkRequest(HttpServletRequest req)
	{
		if (req==null)
		{
			throwInternalErrorException(SharedConstants.Exception_Login_RequestNull);
		}
	}
	
	protected User getUser(HttpServletRequest req) 
	{
		User User = LoginHelper.getLoggedInUser(req.getSession());
		if (User==null)
			throwInternalErrorException(SharedConstants.Exception_Login_SessionExpire);

		return User;
	}
	
	protected void addCookiesToResponse(HttpServletRequest req,Response.ResponseBuilder builder)
	{
		if(req!=null&&builder!=null)
		{
			Authenticator authenticator = (Authenticator)req.getSession().getAttribute(SharedConstants.SESSION_ATTRIBUTE_AUTHENTICATOR);
			if (authenticator!=null)
				authenticator.addCookiesToResponse(builder);
		}
	}


}
