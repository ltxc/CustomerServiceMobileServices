package com.ltxc.google.csms.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.TemplateBase;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class NonMiscTemplateLoader extends TemplateLoaderBase implements
		ITemplateLoader {
	
	public static final String DRCCLOSETEMPLATE = "com/ltxc/google/csms/server/resource/NonMiscReceiveDRCCloseFullReceivel.xml";
	public static final String DRCCLOSETEMPLATEITEM = "com/ltxc/google/csms/server/resource/NonMiscReceiveDRCCloseFullReceivelItem.xml";
	public static final String DRCFULLRECEIVEL= "com/ltxc/google/csms/server/resource/NonMiscReceiveDRCFullReceivel.xml";
	public static final String NONDRCFULLRECEIVEL= "com/ltxc/google/csms/server/resource/NonMiscReceiveNonDRCFullReceivel.xml";
	public static final String RRFV2Action= "com/ltxc/google/csms/server/resource/NonMiscReceiveRRFV2Action.txt";
	public static final String NONMISCRECEIVEHEADER= "com/ltxc/google/csms/server/resource/NonMiscReceiveHeaderTemplate.xml";
	
	public static final String OUTPUTTEMPLATE_DRC1= "DRC1";
	public static final String OUTPUTTEMPLATE_RFR1= "RFR1";
	public static final String OUTPUTTEMPLATE_RFR2= "RFR2";
	public static final String OUTPUTTEMPLATE_RRFV1= "RRFV1";
	public static final String OUTPUTTEMPLATE_RRFV2= "RRFV2";
	
	private static Logger logger = Logger
			.getLogger(NonMiscTemplateLoader.class.getName());
	@Override
	public String generateAPIXml(TransactionTypeEnum type,
			TransactionBase transaction) {
//		try{
//			List<? extends TemplateBase> lineItems = transaction.getLineItems();
//			StringBuilder lineItemTemplates = new StringBuilder();
//			String headerTemplate = loadTemplateFile(HeaderTemplateFile);
//			String lineItemTemplate = loadTemplateFile(LineItemTemplateFile);
//			for(TemplateBase tb:lineItems)
//			{
//				Map<String, Object> map = tb.getMappedValues();
//				String v = StringHelper.format(lineItemTemplate, map);
//				
//				lineItemTemplates.append(v);
//			}
//			Map<String, Object> map = transaction.getMappedValues();
//			map.put(SharedConstants.Attribute_Inventory_LineItems, lineItemTemplates.toString());
//			String v = StringHelper.format(headerTemplate, map);
//			return v;
//
//		}catch(Exception xe)
//		{
//			xe.printStackTrace();
//			logger.log(Level.SEVERE, "TemplateLoaderFactory:generateAPIXml - "+xe.getMessage());
//		}
		return null;
	}

	



	@Override
	public String generateAPIXmlLineByLine(TransactionTypeEnum type,
			TransactionBase transaction, int index) {
		try{
		InventoryTransaction inv = (InventoryTransaction)transaction;
		InventoryLineItem lineItem = (InventoryLineItem)transaction.getLineItems().get(index);
		String validationTemplate = lineItem.getValidationTemplate();
		if(validationTemplate==null)
			return "Validation return null validation template.";
		
		String rfr2_action = "";
		String nondrc_full_receivel = "";
		String drc_full_receivel = "";
		String drc_close_action = "";
		Map<String, Object> map = new HashMap<String, Object>();
//		if(validationTemplate.equalsIgnoreCase(OUTPUTTEMPLATE_DRC1))
//		{
//			
//			//drc
//			drc_full_receivel = loadDRCFullReceivel(lineItem);
//			if(lineItem.getUndeliveredLineCount()!=0)
//				drc_close_action = loadDRCClose(lineItem);
//		}
//		else
//		{

			//rfr1, rfr2, rrfv1 and rrfv2
			if(validationTemplate.equalsIgnoreCase(OUTPUTTEMPLATE_RRFV2))  //removed validationTemplate.equalsIgnoreCase(OUTPUTTEMPLATE_RFR2)||
			{
				rfr2_action = loadRFR2Action(lineItem, type);
				
			}
			nondrc_full_receivel = loadNonDRCFullReceivel(lineItem);
			

//		}

		map.put(SharedConstants.Attribute_Inventory_RFR2ACTION, rfr2_action);
		map.put(SharedConstants.Attribute_Inventory_NONDRCFULLRECEIVEL, nondrc_full_receivel);
		map.put(SharedConstants.Attribute_Inventory_DRCFULLRECEIVEL, drc_full_receivel);
		map.put(SharedConstants.Attribute_Inventory_DRCCLOSE, drc_close_action);
		
		map.put(SharedConstants.Attribute_Inventory_Ref_Doc_Type_Id, type.getRef_doc_type_id());
		map.put(SharedConstants.Attribute_Inventory_OUTPUTREFNO, lineItem.getOutputRefNo());
		
		map.put(SharedConstants.Attribute_Inventory_Received_By, inv.getCreated_by());
		map.put(SharedConstants.Attribute_Inventory_To_Warehouse_ID, inv.getTo_warehouse().getWarehouse_id());

		map.put(SharedConstants.Attribute_Inventory_Carrier, inv.getCarrier()!=null?inv.getCarrier().getCarrier_id():"");
		map.put(SharedConstants.Attribute_Inventory_Carrier_Refno, inv.getCarrier_refno()!=null?inv.getCarrier_refno():"");
		map.put(SharedConstants.Attribute_Inventory_Sender_Refno, inv.getSender_refno()!=null?inv.getSender_refno():"");

		String template = loadTemplateFile(NONMISCRECEIVEHEADER);
		String v = StringHelper.format(template, map);
		return v;

	}catch(Exception xe)
	{
		xe.printStackTrace();
		logger.log(Level.SEVERE, "TemplateLoaderFactory:generateAPIXml - "+xe.getMessage());
	}
		return null;
	}
	
	private String loadRFR2Action(InventoryLineItem lineItem,TransactionTypeEnum type)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SharedConstants.Attribute_Inventory_OUTPUTREFNO, lineItem.getOutputRefNo());
		map.put(SharedConstants.Attribute_Inventory_OUTPUTRP, lineItem.getOutputRP());
		map.put(SharedConstants.Attribute_Inventory_CUSTOMERCOMPANYID,lineItem.getCustomerCompanyID());
		map.put(SharedConstants.Attribute_Inventory_BPart_ID,lineItem.getBpart_id());
		map.put(SharedConstants.Attribute_Inventory_Serial_No,lineItem.getSerial_no());
		//RP Information
		map.put(SharedConstants.Attribute_Inventory_Dest_Warehouse_ID,lineItem.getDestWarehouseId());
		map.put(SharedConstants.Attribute_Inventory_War_Type_ID,lineItem.getWarrTypeId());
		map.put(SharedConstants.Attribute_Inventory_Cconth_ID,lineItem.getCconthId());
		map.put(SharedConstants.Attribute_Inventory_Node_ID,lineItem.getNodeId());
		map.put(SharedConstants.Attribute_Inventory_PO_ID,lineItem.getPoId());
		map.put(SharedConstants.Attribute_Inventory_Priority,lineItem.getPriority());
		map.put(SharedConstants.Attribute_Inventory_Pcode_ID,lineItem.getPcodeId());
		map.put(SharedConstants.Attribute_Inventory_ITEMID, lineItem.getItemID());

		
		String template = loadTemplateFile(RRFV2Action);
		String v = StringHelper.format(template, map);
		return v;
	}
	
	private String loadNonDRCFullReceivel(InventoryLineItem lineItem)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		//map.put(SharedConstants.Attribute_Inventory_Inv_Type_ID,lineItem.getInv_type_id());
		map.put(SharedConstants.Attribute_Inventory_Inv_Type_ID,lineItem.getDestInvTypeID());
		map.put(SharedConstants.Attribute_Inventory_Bin_Code_ID,lineItem.getBin_code_id());
		map.put(SharedConstants.Attribute_Inventory_QTY,Float.toString(lineItem.getQty()));
		map.put(SharedConstants.Attribute_Inventory_BPart_ID,lineItem.getBpart_id());
		map.put(SharedConstants.Attribute_Inventory_Serial_No,lineItem.getSerial_no());
		map.put(SharedConstants.Attribute_Inventory_ORIGDOCLINEID,lineItem.getOrigDocLineID());
		String template = loadTemplateFile(NONDRCFULLRECEIVEL);
		String v = StringHelper.format(template, map);
		return v;
	}
	
//	private String loadDRCFullReceivel(InventoryLineItem lineItem)
//	{
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put(SharedConstants.Attribute_Inventory_Inv_Type_ID,lineItem.getDestInvTypeID());
//		map.put(SharedConstants.Attribute_Inventory_Bin_Code_ID,lineItem.getBin_code_id());
//		map.put(SharedConstants.Attribute_Inventory_QTY,Float.toString(lineItem.getQty()));
//		map.put(SharedConstants.Attribute_Inventory_ORIGDOCLINEID,lineItem.getOrigDocLineID());
//		String template = loadTemplateFile(DRCFULLRECEIVEL);
//		String v = StringHelper.format(template, map);
//		return v;
//	}
//	
//
//	
//	private String loadDRCClose(InventoryLineItem lineItem)
//	{
//		StringBuilder sb = new StringBuilder();
//		int count = lineItem.getUndeliveredLineCount();
//		for(int i=0; i<count;i++)
//		{
//			String sitem = loadDRCCloseItem(i, lineItem.getDestInvTypeID());
//			sb.append(sitem);
//		}
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put(SharedConstants.Attribute_Inventory_DRCCLOSEITEM, sb.toString());
//		String template = loadTemplateFile(DRCCLOSETEMPLATE);
//		String v = StringHelper.format(template, map);		
//		return v;
//	}
//	
//	private String loadDRCCloseItem(int index, String inv_type_id)
//	{
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put(SharedConstants.Attribute_Inventory_Inv_Type_ID, inv_type_id);
//		map.put(SharedConstants.Attribute_Inventory_INDEX,Integer.toString(index+1));
//		String template = loadTemplateFile(DRCCLOSETEMPLATEITEM);
//		String v = StringHelper.format(template, map);
//		return v;
//	}
	
}
