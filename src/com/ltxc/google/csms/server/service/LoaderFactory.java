package com.ltxc.google.csms.server.service;

import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class LoaderFactory {
	private static LoaderFactory instance;
	private WebServiceAPI _webServiceAPI;
	private LoaderFactory() throws Exception 
	{
		_webServiceAPI = WebServiceAPIFactory.get().init(SharedConstants.URL_AsteaAlliance_API, SharedConstants.AA_UserName, SharedConstants.AA_Password, SharedConstants.AA_Profile).getWebServiceAPI();
	}
	
	
	
	public WebServiceAPI getWebServiceAPI() {
		return _webServiceAPI;
	}



	public void setWebServiceAPI(WebServiceAPI _webServiceAPI) {
		this._webServiceAPI = _webServiceAPI;
	}



	public static LoaderFactory get() throws Exception
	{
		if(instance==null)
			instance = new LoaderFactory();
		
		return instance;
	}
	
	public ILoader getLoader(TransactionTypeEnum type) throws LoaderException
	{
		ILoader loader = null;
		if(type!=null)
		{
			
			switch(type)
			{
			case MRC: loader  =new  InventoryReceivingLoader();break;
			case DRC: loader  =new  InventoryReceivingLoader();break;
			case RRFV: loader  =new  InventoryReceivingLoader();break;
			case RFR: loader  =new  InventoryReceivingLoader();break;
			case PICK: loader = new InventoryShippingLoader();break;
			case PICKLIST: loader = new InventoryShippingLoader();break;
			case SHIP: loader = new InventoryShippingLoader();break;
			case SHIPLIST: loader = new InventoryShippingLoader();break;
			case ALLOCATEDSHIPLIST: loader = new InventoryShippingLoader();break;
			default:break;
			}
			
			if(loader!=null)
			{
				loader.setWebServiceAPI(_webServiceAPI);
				
			}
		}
		
		return loader;
	}
}
