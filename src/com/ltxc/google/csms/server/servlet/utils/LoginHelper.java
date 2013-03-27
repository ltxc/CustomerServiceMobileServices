package com.ltxc.google.csms.server.servlet.utils;


import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;
import com.ltxc.google.csms.server.domain.User;
import com.ltxc.google.csms.shared.SharedConstants;





public class LoginHelper {
	private static Logger logger = Logger
			.getLogger(LoginHelper.class.getName());


	static public String getApplitionURL(HttpServletRequest request) {
			return ServletUtils.getBaseUrl(request);		
	}
	

	/**
	 * Get the UserAccount by the user account key stored in the session.
	 * 
	 * @param session
	 * @param pm
	 * @return
	 * @throws GoogleDataServiceException 
	 */
	static public User getLoggedInUser(HttpSession session)  {

		if (session == null)
			return null; // user not logged in

		return (User) session.getAttribute(SharedConstants.SESSION_ATTRIBUTE_USER);
	}

	/**
	 * Check whether logged in by check the session attribute loggedin.
	 * 
	 * @param req
	 * @return
	 */
	static public boolean isLoggedIn(HttpServletRequest req) {

		if (req == null)
			return false;
		else {
			HttpSession session = req.getSession();
			if (session == null) {
				logger.info("Session is null...");
				return false;
			} else {
				Boolean isLoggedIn = (Boolean) session.getAttribute(SharedConstants.SESSION_ATTRIBUTE_ISLOGGEDIN);
				if (isLoggedIn == null) {
					logger.info("Session found, but did not find loggedin attribute in it: user not logged in");
					return false;
				} else if (isLoggedIn) {
					logger.info("User is logged in...");
					return true;
				} else {
					logger.info("User not logged in");
					return false;
				}
			}
		}
	}


	public static void loginSuccess(HttpSession session, User user)
	{
		if (session!=null&&user!=null)
		{
			session.setAttribute(SharedConstants.SESSION_ATTRIBUTE_USER, user);
			session.setAttribute(SharedConstants.SESSION_ATTRIBUTE_USERID, user.getUserid());
			session.setAttribute(SharedConstants.SESSION_ATTRIBUTE_ISLOGGEDIN, true);
		}
	}
	
	public static void logoutSuccess(HttpSession session)
	{
		if (session!=null)
		{
			session.removeAttribute(SharedConstants.SESSION_ATTRIBUTE_USER);
			session.removeAttribute(SharedConstants.SESSION_ATTRIBUTE_USERID);
			session.removeAttribute(SharedConstants.SESSION_ATTRIBUTE_ISLOGGEDIN);
		}
	}
	


	public static void addAuthorizationCookieToReponse(HttpSession session, Response.ResponseBuilder builder)
	{
		if (session==null||builder==null)
			return;
		Authenticator authenticator = (Authenticator) session.getAttribute(SharedConstants.SESSION_ATTRIBUTE_AUTHENTICATOR);
		if (authenticator!=null)
			authenticator.addCookiesToResponse(builder);
	}
}

