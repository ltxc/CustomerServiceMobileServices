package com.ltxc.google.csms.server.service.restful;


import java.security.Principal;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.HttpHeaders;

import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.server.servlet.utils.Authenticator;
import com.ltxc.google.csms.server.servlet.utils.LoginHelper;
import com.ltxc.google.csms.shared.SharedConstants;
import com.sun.jersey.api.container.MappableContainerException;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerRequest;




/**
 *  * A Jersey ContainerRequestFilter that provides a SecurityContext for all  *
 * requests processed by this application.  
 */
public class SecurityFilter implements ContainerRequestFilter {

	private static final Logger logger = Logger.getLogger(SecurityFilter.class
			.getName());

	@Context
	UriInfo uriInfo;

	// inject
	@Context
	HttpServletRequest request; 
	
	@Context 
	ResourceConfig resourceContext;

	//private static final String REALM = "HTTPS authentication in the ContainerRequestFilter";

	public SecurityFilter() {
		logger.setLevel(Level.INFO);
		logger.info("SecurityFilter is installed.");

	}

	/**
	 *  * Authenticate the user for this request, and add a security context so
	 *  * that role checking can be performed.  *  * @param request The request
	 * we re processing  * @return the decorated request  
	 */
	public ContainerRequest filter(ContainerRequest request) {
		
		if(!SharedConstants.IsLoadInitParam)
		{
			//load it here
			SharedConstants.URL_AsteaAlliance_API = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_URL_AsteaAlliance_API);
			SharedConstants.AA_UserName = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_AA_UserName);
			SharedConstants.AA_Password = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_AA_Password);
			SharedConstants.AA_Profile = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_AA_Profile);
			
			SharedConstants.ActiveDirectory_LADPURL = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_ActiveDirectory_LADPURL);
			SharedConstants.ActiveDirectory_DOMAIN = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_ActiveDirectory_DOMAIN);
			SharedConstants.ActiveDirectory_DefaultSearchBase = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_ActiveDirectory_DefaultSearchBase);
			SharedConstants.ActiveDirectory_GroupDistinguishName = (String)resourceContext.getProperty(SharedConstants.INIT_PARAM_ActiveDirectory_GroupDistinguishName);
			SharedConstants.IsLoadInitParam = true;
		}
		
		User User = authenticate(request);
		request.setSecurityContext(new Authorizer(User));
		return request;
	}

	/**
	 *  * Perform the required authentication checks, and return the User
	 * instance  * for the authenticated user.  
	 */
	private User authenticate(ContainerRequest containerRequest) {
		User user = null;
		Authenticator authenticator = new Authenticator();
		logger.info("authenticate now...");
		// check httpsession to see whether login
		if (LoginHelper.isLoggedIn(request)) {
			return LoginHelper.getLoggedInUser(request.getSession());

		} else {
			// now find the authenication information and authenticate against
			// provider
			try {
				// step 1. Look at the http property for the basic
				// authentication information
				String authentication = containerRequest
						.getHeaderValue(HttpHeaders.AUTHORIZATION);
//				String provider = containerRequest.getHeaderValue("Provider");
//				if (provider!=null)
//					authenticator.setProvider(provider);
				boolean isset = authenticator.setBasicAuth(authentication);
				if (!isset)
					logger.info("Authentication information not found in the header:"+authentication);

				// step 2. If MediaType.APPLICATION_JSON, look for username,
				// password and provider information
				if (!authenticator.hasAuthenticationInfo()) {
//					String contentType = containerRequest
//							.getHeaderValue(HttpHeaders.CONTENT_TYPE);
//					if (MediaType.APPLICATION_JSON
//							.equalsIgnoreCase(contentType)) {
//						findAuthenticationInfoFromJsonBody(request, authenticator);
//					}
//					else
//					{
						//search the url for username/password
						
						String sUsername = request.getParameter("username");
						String sPassword = request.getParameter("password");
						authenticator.setUserInfo(sUsername, sPassword);
						
//					}
				}

				//step 3. Authentication 
				if(authenticator.isValidUserinfo())
				{
				try{
					//get the user account based on the email address
					user= User.getUser(SharedConstants.ActiveDirectory_DOMAIN, authenticator.getUsername(), authenticator.getPassword());
					if (user==null||!user.isAuthorized())
					{
						if(user==null||!user.isAuthenticated())
							throw new MappableContainerException(new RestfulLoginException("Authentication failed. Please double check the username and password. ",Response.Status.UNAUTHORIZED));
						else
							throw new MappableContainerException(new RestfulLoginException("Authorization failed. You are not authorized ",Response.Status.UNAUTHORIZED));
					}
					else
					{
						
						authenticator.setAuthenticated(true);
						
					}
					
				}catch(Exception xe)
				{
					throw new MappableContainerException(new RestfulLoginException(xe.getMessage(),Response.Status.INTERNAL_SERVER_ERROR));
				}
				}else
					throw new MappableContainerException(new RestfulLoginException("Authentication information can not be found..."));
				
				
				request.getSession().setAttribute(SharedConstants.SESSION_ATTRIBUTE_AUTHENTICATOR, authenticator);
				LoginHelper.loginSuccess(request.getSession(), user);
			} 
			catch(RestfulLoginException le)
			{
				logger.severe(le.getMessage());
				throw new MappableContainerException(le);
			}
			catch (Exception ex) {
				ex.printStackTrace();
				logger.severe(ex.getMessage());			
				throw new MappableContainerException(new RestfulLoginException(ex.getMessage()));
			}
			
			
			try{
				if (user!=null)
					LoginHelper.loginSuccess(request.getSession(), user);
				
			}catch(Exception xe)
			{
				throw new MappableContainerException(new RestfulLoginException(xe.getMessage(),Response.Status.INTERNAL_SERVER_ERROR));
			}
			
		}
		return user;
	}

//	@SuppressWarnings("rawtypes")
//	private void findAuthenticationInfoFromJsonBody(HttpServletRequest request,
//			Authenticator result) {
//
//		try {
//			JSONObject obj = JSONObject
//					.fromObject(readJSONStringFromRequest(request));
//			Iterator i = obj.entrySet().iterator();
//			while (i.hasNext()) {
//				Map.Entry e = (Map.Entry) i.next();
//				String key = (String) e.getKey();
//				String value = (String) e.getValue();
//				if ("username".equalsIgnoreCase(key)) {
//					result.setUsername(value);
//				} else if ("password".equalsIgnoreCase(key)) {
//					result.setPassword(value);
//				} else if ("provider".equalsIgnoreCase(key)) {
//					result.setProvider(value);
//				}
//			}
//
//		} catch (Exception xe) {
//			xe.printStackTrace();
//			logger.severe("Failed to read json body:" + xe.getMessage());
//		}
//	}
//
//	private String readJSONStringFromRequest(HttpServletRequest request)
//			throws IOException {
//		BufferedReader reader = request.getReader();
//		StringBuilder sb = new StringBuilder();
//		String line = reader.readLine();
//		while (line != null) {
//			sb.append(line + "\n");
//			line = reader.readLine();
//		}
//		reader.close();
//		String data = sb.toString();
//		return data;
//	}

	public class Authorizer implements SecurityContext {

		private User user;
		private Principal principal;

		public Authorizer(final User user) {
			this.user = user;
			principal = new Principal() {
				//@Override
				public String getName() {
					if (user != null)
						return user.getUserid();

					return null;
				}
			};
		}

		public User getUser()
		{
			return this.user;
		}
		
		//@Override
		public String getAuthenticationScheme() {
			if (principal == null)
				return null;
			return SecurityContext.BASIC_AUTH;
		}

		//@Override
		public Principal getUserPrincipal() {

			return this.principal;
		}

		//@Override
		public boolean isSecure() {

			return "https".equals(uriInfo.getRequestUri().getScheme());
		}

		//@Override
		public boolean isUserInRole(String role) {
			if ("admin".equals(role)) {
				return false;
			} else if ("user".equals(role)) {
				return principal != null;
			}
			return false;
		}

	}
	
	

}
