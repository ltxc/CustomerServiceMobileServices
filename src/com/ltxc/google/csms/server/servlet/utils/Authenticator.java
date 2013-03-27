package com.ltxc.google.csms.server.servlet.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;

public class Authenticator implements Serializable{
	public static String BasicString = "Basic ";
	private boolean isAuthenticated = false;
	private String authenticationToken = null;
	private List<Cookie> cookies = new ArrayList<Cookie>();
	private StringBuilder sb = new StringBuilder();

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username!=null)
			username = username.trim().toLowerCase();
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public List<Cookie> getCookie() {
		return cookies;
	}

	public void setCookie(List<Cookie> cookie) {
		this.cookies = cookie;
	}

	public String getMessage() {
		return sb.toString();
	}

	public void addMessage(String message) {
		this.sb.append(message);
	}

	public void clearMessage() {
		sb.delete(0, sb.length() - 1);
	}

	public Authenticator() {

	}

	public Authenticator(String username, String password) {
		this.username = username;
		this.password = password;


	}

	public boolean hasAuthenticationInfo() {
		if (this.username == null || this.password == null)
			return false;
		else
			return true;
	}

	public boolean setBasicAuth(String auth) {

		boolean isSet = false;
		if (auth != null && auth.startsWith(BasicString)) {
			auth = auth.substring(BasicString.length());
			String[] values = new String(Base64.decodeBase64(auth.getBytes()))
					.split(":");
			if (values.length == 2) {
				this.username = values[0];
				this.password = values[1];
				isSet = true;
			}
		}
		return isSet;
	}



	public void addCookiesToServletResponse(HttpServletResponse resp) {
		if (resp == null)
			return;
		for (Cookie cookie : cookies) {
			resp.addCookie(new javax.servlet.http.Cookie(cookie.getName(),
					cookie.getValue()));
		}
	}

	public void addCookiesToResponse(Response.ResponseBuilder builder) {
		if (builder == null)
			return;
		if (cookies.size() > 0) {
			for (Cookie cookie : cookies) {
				builder.cookie(new NewCookie(cookie.getName(), cookie
						.getValue()));
			}
		}
	}

	public void setUserInfo(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);

	}
	
	public boolean isValidUserinfo()
	{
		if(this.username==null||this.password==null)
			return false;
		else
			return true;
	}
}
