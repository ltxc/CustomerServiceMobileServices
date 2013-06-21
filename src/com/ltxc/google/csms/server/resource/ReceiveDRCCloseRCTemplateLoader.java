package com.ltxc.google.csms.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.domain.InventoryTransaction;
import com.ltxc.google.csms.server.domain.TemplateBase;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class ReceiveDRCCloseRCTemplateLoader extends TemplateLoaderBase
		implements ITemplateLoader {
	private static Logger logger = Logger.getLogger(ReceiveDRCCloseRCTemplateLoader.class
			.getName());
	public static String HeaderTemplateFile = "com/ltxc/google/csms/server/resource/ReceivingDRCRCCloseHeaderTemplate.xml";
	public static String LineItemTemplateFile = "com/ltxc/google/csms/server/resource/ReceivingDRCRCCloseLineItemTemplate.xml";

	@Override
	public String generateAPIXml(TransactionTypeEnum type,
			TransactionBase transaction) {
		try {
			InventoryTransaction invTransaction = (InventoryTransaction)transaction;
			StringBuilder lineItemTemplates = new StringBuilder();
			String headerTemplate = loadTemplateFile(HeaderTemplateFile);
			String lineItemTemplate = loadTemplateFile(LineItemTemplateFile);
			int undelivereditemcount = invTransaction.getUndeliveredlinecount();
			for (int i=1; i<(undelivereditemcount+1); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(SharedConstants.Attribute_Inventory_LineItemNo, i);
				String v = StringHelper.format(lineItemTemplate, map);
				lineItemTemplates.append(v);
			}
			Map<String, Object> map = transaction.getMappedValues();
			map.put(SharedConstants.Attribute_Inventory_LineItems,
					lineItemTemplates.toString());
			map.put(SharedConstants.Attribute_Inventory_OUTPUTREFNO,
					invTransaction.getOutputrefno());
			String v = StringHelper.format(headerTemplate, map);
			return v;

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(Level.SEVERE, "ReceiveDRCCloseRCTemplateLoader:generateAPIXml - "
					+ xe.getMessage());
		}
		return null;
	}

	@Override
	public String generateAPIXmlLineByLine(TransactionTypeEnum type,
			TransactionBase transaction, int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
