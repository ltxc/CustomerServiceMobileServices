package com.ltxc.google.csms.server.service.restful;

import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.ShippingTransaction;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class TransactionFactory {
	private static TransactionFactory instance = null;
	
	private TransactionFactory()
	{
		
	}
	
	public static TransactionFactory get()
	{
		if(instance==null)
			instance = new TransactionFactory();
		return instance;		
	}
	
	public TransactionBase generate(TransactionTypeEnum type)
	{
		TransactionBase  transaction = null;
		switch(type)
		{
		//receiving
		case DRC: transaction = new InventoryTransaction();break;
		case MRC: transaction = new InventoryTransaction();break;
		case RFR: transaction = new InventoryTransaction();break;
		case RRFV: transaction = new InventoryTransaction();break;
		//shipping
		case ALLOCATEDSHIPLIST: transaction = new ShippingTransaction();break;
		case PICK:transaction = new ShippingTransaction();break;
		case SHIP:transaction = new ShippingTransaction();break;
		case SHIPLIST:transaction = new ShippingTransaction();break;
		default:break;
		
		}
		
		return transaction;
	}
}
