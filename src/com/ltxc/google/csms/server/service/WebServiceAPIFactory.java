package com.ltxc.google.csms.server.service;

public class WebServiceAPIFactory {
	private static WebServiceAPIFactory _instance;
	private WebServiceAPI _webServiceAPI;
	public static WebServiceAPIFactory get()
	{
		if(_instance==null)
		{
			_instance = new WebServiceAPIFactory();
		}
		
		return _instance;
	}
	
	public WebServiceAPIFactory()
	{}
	
	public WebServiceAPIFactory init(String url, String username, String password, String profile) throws Exception
	{
		_webServiceAPI = new WebServiceAPI(url, username, password, profile);
		return this;
	}
	
	public WebServiceAPIFactory init() throws Exception
	{
		if(_webServiceAPI==null)
			_webServiceAPI = new WebServiceAPI();

		return this;
	}

	public WebServiceAPI getWebServiceAPI() {
		return _webServiceAPI;
	}

	
	
	
}
