package com.ltxc.google.csms.server.domain;

import java.io.File;
import java.io.Serializable;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;


import com.ltxc.google.csms.server.servlet.utils.AuthFilter;
import com.ltxc.google.csms.server.servlet.utils.LdapGroupAuthenticator;
import com.ltxc.google.csms.server.servlet.utils.LoginHelper;
import com.ltxc.google.csms.shared.SharedConstants;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(User.class.getName());
	
	private String aaacount;
	@XmlTransient
	private String displayname;
	@XmlTransient
	private String password;
	
	private String userid;

	private boolean isAuthorized = false;
	@XmlTransient
	private boolean isAuthenticated = false;

	public User() {
		
	}


	public User(String userid)
	{
		//temporary
		if (userid!=null)
			this.userid = userid;
	}

	public String getAaacount() {
		return aaacount;
	}


	public void setAaacount(String aaacount) {
		this.aaacount = aaacount;
	}


	public String getPassword() {
		return password;
	}




	public void setPassword(String password) {
		this.password = password;
	}




	public String getUserid() {
		return userid;
	}




	public void setUserid(String userid) {
		this.userid = userid;
	}




	public boolean isAuthorized() {
		return isAuthorized;
	}


	public void setAuthorized(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}


	public boolean isAuthenticated() {
		return isAuthenticated;
	}


	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}


	
	
	public String getDisplayname() {
		return displayname;
	}


	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}


	/****************** static operation portion ****************************/
	
	public static User getUser(String domain, String username, String password)
	{
		User user = null;
		try
		{
			user = LdapGroupAuthenticator.authenticate(domain, username, password);
		}catch(Exception xe)
		{
			xe.printStackTrace();
		}
		return user;
	}

	public static User getUserInSession()
	{
		
		HttpServletRequest request = AuthFilter.perThreadRequest
				.get();
		if (request!=null)
			return LoginHelper.getLoggedInUser(request.getSession());
		else
			return null;
	}
	
	public static void setUserInSession(User user)
	{
		HttpServletRequest request = AuthFilter.perThreadRequest
				.get();
		if (request!=null)
			LoginHelper.loginSuccess(request.getSession(), user);	
	}
	
	public static Boolean logout() {
		// clean the session
		HttpServletRequest request = (HttpServletRequest) AuthFilter.perThreadRequest
				.get();
		if (request != null) {
			LoginHelper.logoutSuccess(request.getSession());
			return true;
		} else
			return false;

	}


}