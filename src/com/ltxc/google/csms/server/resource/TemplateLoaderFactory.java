package com.ltxc.google.csms.server.resource;

import java.util.logging.Logger;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class TemplateLoaderFactory {
	private static Logger logger = Logger
			.getLogger(TemplateLoaderFactory.class.getName());
	
	private static TemplateLoaderFactory instance = null;
	
	private TemplateLoaderFactory()
	{
		
	}
	
	public static TemplateLoaderFactory get()
	{
		if(instance==null)
			instance = new TemplateLoaderFactory();
		return instance;
		
	}
	
	public ITemplateLoader getLoader(TransactionTypeEnum type)
	{
		ITemplateLoader loader = null;
		switch(type)
		{
			case MRC:loader = new MiscTemplateLoader(); break;
			case DRC:loader = new NonMiscTemplateLoader(); break;
			case RRFV:loader = new NonMiscTemplateLoader(); break;
			case RFR:loader = new NonMiscTemplateLoader(); break;
			case PICK:loader = new ShippingCreationTemplateLoader();break;
			case PICKLIST:loader = new ShippingCreationTemplateLoader();break;
			case SHIP:loader = new ShippingCreationTemplateLoader();break;
			case SHIPLIST:loader = new ShippingCreationTemplateLoader();break;
			case ALLOCATEDSHIPLIST:loader = new ShippingCreationTemplateLoader();break;
			default:break;
		}
		return loader;
	}
}
