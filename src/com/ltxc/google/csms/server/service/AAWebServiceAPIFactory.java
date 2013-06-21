package com.ltxc.google.csms.server.service;

public class AAWebServiceAPIFactory {
	private static AAWebServiceAPIFactory _instance;
	private AAWebServiceAPI _webServiceAPI;
	public static AAWebServiceAPIFactory get()
	{
		if(_instance==null)
		{
			_instance = new AAWebServiceAPIFactory();
		}
		
		return _instance;
	}
	
	public AAWebServiceAPIFactory()
	{}
	
	public AAWebServiceAPIFactory init(String url, String username, String password, String profile) throws Exception
	{
		_webServiceAPI = new AAWebServiceAPI(url, username, password, profile);
		return this;
	}
	
	public AAWebServiceAPIFactory init() throws Exception
	{
		if(_webServiceAPI==null)
			_webServiceAPI = new AAWebServiceAPI();

		return this;
	}

	public AAWebServiceAPI getWebServiceAPI() {
		return _webServiceAPI;
	}

	
	
	
}
