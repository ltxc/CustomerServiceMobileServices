package com.ltxc.google.csms.server.servlet.utils;
/**
 * This filter is for the GWT RequestFactory Servlet to catch the basic authentication
 */
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





public final class AuthFilter implements Filter {

	  private static Logger logger = Logger.getLogger(AuthFilter.class.getName());
	  public static final ThreadLocal<HttpServletRequest> perThreadRequest = new ThreadLocal<HttpServletRequest>();
	  
	  //@Override 
	  public void doFilter(ServletRequest servletRequest, ServletResponse ServletResponse, FilterChain chain) throws IOException,
	      ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) ServletResponse;
	    logger.info("in AuthFilter.doFilter");
	    try {
	      HttpServletRequest req = (HttpServletRequest) request;
	      perThreadRequest.set(req);
	      //chain.doFilter(request, response);
	      if (LoginHelper.isLoggedIn(req)) {
	        logger.info("User is logged in...");
	        //TODO:may check whether this is logout request, if yes, do the logout here
	        chain.doFilter(request, response);
	      } else {
	        logger.warning("User is not logged in...");
//	        if (request.getContentType().contains("x-gwt-rpc")){
//	          // GWT requests
//	          HttpServletResponse resp = (HttpServletResponse) response;
//	          resp.setHeader("content-type", request.getContentType());
//	          resp.getWriter().print(SharedConstants.LOGGED_OUT);
//	        } else {
//	          HttpServletResponse resp = (HttpServletResponse) response;
//	          resp.sendRedirect("/");
//	        }
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	  }

	  //@Override 
	  public void destroy() {


	  }

	  //@Override 
	  public void init(FilterConfig arg0) throws ServletException {


	  }

	} // end class
