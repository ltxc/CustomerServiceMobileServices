package com.ltxc.google.csms.aa;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.junit.Test;


import com.ltxc.google.csms.server.aa.api.WebServiceSoap;
import com.ltxc.google.csms.server.service.WebServiceAPI;
import com.ltxc.google.csms.server.service.WebServiceAPIFactory;


public class TestAAAPIWebService {
	private static Logger logger = Logger.getLogger(TestAAAPIWebService.class.getName());
	@Test
	public void test() {
		try{
		final WebServiceAPI webServiceAPI = WebServiceAPIFactory.get().init().getWebServiceAPI();
		if(webServiceAPI!=null)
		{
			webServiceAPI.serviceCall(new WebServiceAPI.WebServiceAPIDelegate() {
				

				@Override
				public boolean beforeTransaction(String sessionID,
						WebServiceSoap proxy) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean afterTransaction(String sessionID,
						WebServiceSoap proxy) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean transaction(String sessionID,
						WebServiceSoap proxy) {
					String id = sessionID.toString();
					logger.log(Level.INFO, "SessionID:"+id);
					
					//call the loading service
					StringBuilder sb = new StringBuilder();
//					try{
//						NormalizedString ns = proxy.sendSynchronic(sessionID, new NormalizedString(sb.toString()));
//						if(ns!=null)
//						{
//							logger.log(Level.INFO, ns.toString());
//						}
//					}catch(Exception xe)
//					{
//						logger.log(Level.SEVERE, xe.getMessage());
//					}
					
					return true;
				}
			});
		}
		}catch(Exception xe)
		{
			xe.printStackTrace();
			
		}
	}

}
