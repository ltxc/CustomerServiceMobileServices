package com.ltxc.google.csms.shared;


public enum TransactionTypeEnum {

	NONE("none","none","none",0),
	MRC("MRC","misc_receive","400",1),
	DRC("DRC","receive_via_shiplist","300",2),
	RRFV("RRFV","receive_via_vendor","800",4),
	RFR("RFR","receive_via_repair","900",5),
	PICK("PICK","allocated to picked","none",6),
	SHIP("SHIP","picked to shipped","none",7),
	PICKLIST("PICKLIST","picklistopen to picked","none",8),
	SHIPLIST("SHIPLIST","picked to picklistopen","none",9),
	ALLOCATEDSHIPLIST("ALLOCATEDSHIPLIST","allocated to shiplistopen","none",10);	
	

	public String getTransactionTypeName() {
		return transactionTypeName;
	}


	public int getTransactionType() {
		return transactionType;
	}


	public String getBc_name() {
		return bc_name;
	}


	public String getRef_doc_type_id() {
		return ref_doc_type_id;
	}


	private String transactionTypeName;
	private String bc_name;
	private String ref_doc_type_id;
	private int transactionType;
	
	TransactionTypeEnum(String transactionTypeName,String bc_name, String ref_doc_type_id, int transactionType)
	{
			this.transactionTypeName = transactionTypeName;
			this.bc_name = bc_name;
			this.ref_doc_type_id = ref_doc_type_id;
			this.transactionType = transactionType;		
	}
	
	public static TransactionTypeEnum parse(int transactionType) throws Exception
	{
		TransactionTypeEnum transactionTypeEnum = TransactionTypeEnum.NONE;
		switch(transactionType)
		{
			case 1: transactionTypeEnum = TransactionTypeEnum.MRC;break;
			case 2: transactionTypeEnum = TransactionTypeEnum.DRC;break;
			case 4: transactionTypeEnum = TransactionTypeEnum.RRFV;break;
			case 5: transactionTypeEnum = TransactionTypeEnum.RFR;break;
			case 6: transactionTypeEnum = TransactionTypeEnum.PICK;break;
			case 7: transactionTypeEnum = TransactionTypeEnum.SHIP;break;
			case 8: transactionTypeEnum = TransactionTypeEnum.PICKLIST;break;
			case 9: transactionTypeEnum = TransactionTypeEnum.SHIPLIST;break;
			case 10: transactionTypeEnum = TransactionTypeEnum.ALLOCATEDSHIPLIST;break;
			default:break;//throw new Exception("Failed to parse "+transactionType+" to any enum type.");
		}
		
		return transactionTypeEnum;
	}
	
	public static TransactionTypeEnum parse(String transactionTypeName) throws Exception
	{
		TransactionTypeEnum value = TransactionTypeEnum.NONE;
		if (transactionTypeName!=null)
		{
			String r = transactionTypeName.trim();
			if (r.equalsIgnoreCase(TransactionTypeEnum.MRC.getTransactionTypeName()))
				value = TransactionTypeEnum.MRC;
			else if (r.equalsIgnoreCase(TransactionTypeEnum.DRC.getTransactionTypeName()))
				value = TransactionTypeEnum.DRC;
			else if (r.equalsIgnoreCase(TransactionTypeEnum.RRFV.getTransactionTypeName()))
				value = TransactionTypeEnum.RRFV;
			else if (r.equalsIgnoreCase(TransactionTypeEnum.RFR.getTransactionTypeName()))
				value = TransactionTypeEnum.RFR;
			else if (r.equalsIgnoreCase(TransactionTypeEnum.PICK.getTransactionTypeName()))
				value = TransactionTypeEnum.PICK;			
			else if (r.equalsIgnoreCase(TransactionTypeEnum.SHIP.getTransactionTypeName()))
				value = TransactionTypeEnum.SHIP;	
			else if (r.equalsIgnoreCase(TransactionTypeEnum.PICKLIST.getTransactionTypeName()))
				value = TransactionTypeEnum.PICKLIST;	
			else if (r.equalsIgnoreCase(TransactionTypeEnum.SHIPLIST.getTransactionTypeName()))
				value = TransactionTypeEnum.SHIPLIST;	
			else if (r.equalsIgnoreCase(TransactionTypeEnum.ALLOCATEDSHIPLIST.getTransactionTypeName()))
				value = TransactionTypeEnum.ALLOCATEDSHIPLIST;	
		}		
		return value;
	}
	
}