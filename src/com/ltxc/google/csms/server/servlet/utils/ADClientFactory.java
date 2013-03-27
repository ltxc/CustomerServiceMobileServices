package com.ltxc.google.csms.server.servlet.utils;

import com.ltxc.google.csms.server.domain.User;

public class ADClientFactory {
	private static ADClientFactory instance;
	

	private ADClientFactory()
	{
		
		
	}
	
	public static ADClientFactory get()
	{
		if(instance==null)
			instance = new ADClientFactory();
		return instance;
	}
	
	
//	public ADClient createADClient(String url, String username, String password)
//	{
//		return new ADClient(url, username, password);
//		
//	}
	
}
