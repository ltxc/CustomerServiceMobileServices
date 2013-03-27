package com.ltxc.google.csms.server.service;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import com.ltxc.google.csms.shared.SharedConstants;

import com.ltxc.google.csms.server.aa.api.WebServiceImpl;
import com.ltxc.google.csms.server.aa.api.WebServiceSoap;;

public class WebServiceAPI {
	public static interface WebServiceAPIDelegate{
		public boolean beforeTransaction(String sessionID, WebServiceSoap proxy);
		public boolean transaction(String sessionID, WebServiceSoap proxy);
		public boolean afterTransaction(String sessionID, WebServiceSoap proxy);
		
	}
	
	private static Logger logger = Logger
			.getLogger(WebServiceAPI.class.getName());
	
	private Pattern _pattern = null;
	private WebServiceSoap _proxy = null;
	private String _user = null;
	private String _password = null;
	private String _profile = null;
	private String _sessionid = null;
	
	
	public WebServiceAPI() throws Exception
	{
		
		_proxy = getProxy(SharedConstants.URL_AsteaAlliance_API);
		_pattern = Pattern.compile(SharedConstants.Pattern_SessionID);
		_user = SharedConstants.AA_UserName;
		_password = SharedConstants.AA_Password;
		_profile = SharedConstants.AA_Profile;		
	}
	
	public WebServiceAPI(String url, String username, String password, String profile) throws Exception
	{
		_proxy = getProxy(url);
		_pattern = Pattern.compile(SharedConstants.Pattern_SessionID);
		_user = username;
		_password = password;
		_profile = profile;		
	}
	
	public WebServiceSoap getProxy(String url) throws Exception
	{
		WebServiceImpl service = new WebServiceImpl();
		
		WebServiceSoap proxy = service.getWebServiceSoap();
		proxy._setProperty("javax.xml.rpc.service.endpoint.address", url);
		return proxy;
		
	}
	
	public void serviceCall(WebServiceAPIDelegate webServiceAPIDelegate)
	{
		boolean isSignedIn = false;
		try {
			_sessionid = _proxy.login(_user, _password, _profile);
			//_sessionid = _proxy.login(_user, _password, _profile);
			if(_sessionid!=null)
			{
				
				isSignedIn = _pattern.matcher(_sessionid.toString()).matches();
				if(isSignedIn)
				{
					
					boolean isSessionValid = _proxy.isSessionValid(_sessionid);
					if(isSessionValid)
					{
						//call the call back method to process the API call now. passing sessionid and proxy object
						if(webServiceAPIDelegate!=null)
						{
							if(webServiceAPIDelegate.beforeTransaction(_sessionid, _proxy))
							{
								if(webServiceAPIDelegate.transaction(_sessionid, _proxy))
									webServiceAPIDelegate.afterTransaction(_sessionid, _proxy);
							}							
						}
					}
					
				}
				
			}

		} catch (Exception xe) {
			xe.printStackTrace();
		} finally {
			if (isSignedIn) {
				try {
					_proxy.logout(_sessionid);
				} catch (Exception xe) {
					xe.printStackTrace();
				}
			}
			else
			{
				//logged error message
				logger.log(Level.SEVERE, "Astea Alliance Web Service Call failed when signing in. Message:"+_sessionid);
				
			}
		}
	}
}
