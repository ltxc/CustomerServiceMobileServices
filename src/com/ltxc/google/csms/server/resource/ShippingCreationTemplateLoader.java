package com.ltxc.google.csms.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.domain.ShippingLineItem;
import com.ltxc.google.csms.server.domain.ShippingTransaction;
import com.ltxc.google.csms.server.domain.TemplateBase;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class ShippingCreationTemplateLoader extends TemplateLoaderBase
		implements ITemplateLoader {
	public static String shippingCreationTemplateFile = "com/ltxc/google/csms/server/resource/ShippingCreationTemplate.xml";
	public static String pickCreationTemplateFile = "com/ltxc/google/csms/server/resource/PickCreationTemplate.xml";
	public static String pickNonListActionFile = "com/ltxc/google/csms/server/resource/PickNonListAction.xml";
	public static String shippingLineTemplateFile = "com/ltxc/google/csms/server/resource/ShipLineTemplate.xml";
	public static String pickLineTemplateFile = "com/ltxc/google/csms/server/resource/PickLineTemplate.xml";
	public static String shippingListOpenCreationTempalteFile = "com/ltxc/google/csms/server/resource/ShippingListOpenCreationTemplate.xml";
	public static String pickOrigDocIdSearch = "(select order_id from seal.dbo.demand_material WITH (nolock) where demand_id = '%{demand_id}' and bpart_id= '%{bpart_id}')";
	public static String pickOrigDocIdNoSearch = "'%{orig_doc_id}'";
	//public static String ShippingVendorRepairActionFile = "com/ltxc/google/csms/server/resource/ShippingVendorRepairAction.xml";
	public static String ShippingVendorRepairActionLineFile = "com/ltxc/google/csms/server/resource/ShippingVendorRepairActionLineTemplate.xml";
	private static Logger logger = Logger
			.getLogger(ShippingCreationTemplateLoader.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public String generateAPIXml(TransactionTypeEnum type,
			TransactionBase transaction) {
		try {
			//decide the vendor repair action for pick and allocatedshiplist type.
			boolean isVendorRepairAction = false;
			String vendorRepairActionLineTemplate = "";			
			ShippingTransaction shipTransaction = (ShippingTransaction)transaction;
			if(type == TransactionTypeEnum.PICK||type == TransactionTypeEnum.ALLOCATEDSHIPLIST)
			{
				String is_vendor = shipTransaction.getIs_vendor();
				if(is_vendor!=null&&is_vendor.equalsIgnoreCase("Y"))
				{
					isVendorRepairAction = true;
					vendorRepairActionLineTemplate = loadTemplateFile(ShippingVendorRepairActionLineFile);
				}
				
			}
			
			StringBuilder sbLineItems = new StringBuilder();
			StringBuilder sbVendorRepairActionLineItems = new StringBuilder();
			String creationTemplate = "";
			String openlisttemplate = "";
			Map<String, Object> map = transaction.getMappedValues();
			Map<String, Object> mapOpenList = new HashMap<String, Object>();
			if (type == TransactionTypeEnum.PICK
					|| type == TransactionTypeEnum.PICKLIST) {
				creationTemplate = loadTemplateFile(pickCreationTemplateFile);
				String origDocIdSearch = pickOrigDocIdSearch;
				if (type == TransactionTypeEnum.PICKLIST)				
				{
					//PICKLIST					
					origDocIdSearch = pickOrigDocIdNoSearch;
				}
				else
				{
					//PICK
					openlisttemplate = loadTemplateFile(pickNonListActionFile);
				}
				mapOpenList.put(
						SharedConstants.Attribute_Shipping_Search_Orig_Doc_ID,
						origDocIdSearch);
				mapOpenList.put(
						SharedConstants.Attribute_Shipping_NonListAction,
						openlisttemplate);
				creationTemplate = StringHelper.format(creationTemplate,
						mapOpenList);
			} else if (type == TransactionTypeEnum.SHIP) {
				creationTemplate = loadTemplateFile(shippingCreationTemplateFile);
			} else if (type == TransactionTypeEnum.SHIPLIST||type == TransactionTypeEnum.ALLOCATEDSHIPLIST) {
				creationTemplate = loadTemplateFile(shippingListOpenCreationTempalteFile);
			}

			String lineTemplate = "";
			if (type == TransactionTypeEnum.PICK||type == TransactionTypeEnum.PICKLIST)
				lineTemplate = loadTemplateFile(pickLineTemplateFile);
			else if (type == TransactionTypeEnum.SHIP||type == TransactionTypeEnum.SHIPLIST||type == TransactionTypeEnum.ALLOCATEDSHIPLIST)
				lineTemplate = loadTemplateFile(shippingLineTemplateFile);

			
				int i = 0;
				for (ShippingLineItem lineItem : (List<ShippingLineItem>) transaction
						.getLineItems()) {
					if (i == 0) {
//						String search_key = "demand_id";
//						String search_value = lineItem.getDemand_id();
						map.put(SharedConstants.Attribute_Shipping_ORIG_DOC_ID,
								lineItem.getOrig_doc_id() == null ? ""
										: lineItem.getOrig_doc_id());
						map.put(SharedConstants.Attribute_Shipping_Item_ID,
								lineItem.getItem_id() == null ? "" : lineItem
										.getItem_id());
//						map.put(SharedConstants.Attribute_Shipping_Search_Key,
//								search_key);
//						map.put(SharedConstants.Attribute_Shipping_Search_Value,
//								search_value);
					}

					String row = loadLineItem(lineItem, lineTemplate);
					if(isVendorRepairAction)
					{
						String vendorRepairActionLineString = loadVendorRepairActionLine(vendorRepairActionLineTemplate,lineItem.getLineitemnumber(),lineItem.getOrig_doc_id() , map); 
						sbVendorRepairActionLineItems.append(vendorRepairActionLineString);
					}
					sbLineItems.append(row);
					i=i+1;
				}

//			if(isVendorRepairAction)
//			{				
				map.put(SharedConstants.Attribute_Shipping_VendorAction, sbVendorRepairActionLineItems.toString());
//			}
			map.put(SharedConstants.Attribute_Shipping_Lines, sbLineItems.toString());

			String v = StringHelper.format(creationTemplate, map);
			return v;

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(
					Level.SEVERE,
					"ShippingCreationTemplateLoader:generateAPIXml - "
							+ xe.getMessage());
		}
		return null;
	}

	private String loadLineItem(ShippingLineItem lineItem, String template) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SharedConstants.Attribute_Shipping_LineItemNo,
				lineItem.getLineitemnumber());
		map.put(SharedConstants.Attribute_Shipping_DEMAND_ID,
				lineItem.getDemand_id() == null ? "" : lineItem.getDemand_id());
		map.put(SharedConstants.Attribute_Shipping_Serial_No,
				lineItem.getSerial_no() == null ? "" : lineItem.getSerial_no());
		map.put(SharedConstants.Attribute_Shipping_BPart_ID,
				lineItem.getBpart_id() == null ? "" : lineItem.getBpart_id());
		String v = StringHelper.format(template, map);
		return v;

	}
	
	private String loadVendorRepairActionLine(String template,int linenumber, String orig_doc_id, Map<String, Object> headerMap) {
		String shipped_by = (String)headerMap.get(SharedConstants.Attribute_Shipping_Shipped_By);
		String created_date = (String)headerMap.get(SharedConstants.Attribute_CreatedDate);
		String target_value = (String)headerMap.get(SharedConstants.Attribute_Shipping_Target_Value);
		String fr_warehouse_id = (String)headerMap.get(SharedConstants.Attribute_Shipping_Fr_Warehouse_ID);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SharedConstants.Attribute_Shipping_LineItemNo,
				linenumber);
		map.put(SharedConstants.Attribute_Shipping_ORIG_DOC_ID,
				orig_doc_id== null ? "" : orig_doc_id);
		map.put(SharedConstants.Attribute_Shipping_Shipped_By,
				shipped_by == null ? "" : shipped_by);
		map.put(SharedConstants.Attribute_CreatedDate,
				created_date == null ? "" : created_date);
		map.put(SharedConstants.Attribute_Shipping_Target_Value,
				target_value == null ? "" : target_value);
		map.put(SharedConstants.Attribute_Shipping_Fr_Warehouse_ID,
				fr_warehouse_id == null ? "" : fr_warehouse_id);
		String v = StringHelper.format(template, map);
		return v;

	}

	@Override
	public String generateAPIXmlLineByLine(TransactionTypeEnum type,
			TransactionBase transaction, int index) {

		return null;
	}

}
