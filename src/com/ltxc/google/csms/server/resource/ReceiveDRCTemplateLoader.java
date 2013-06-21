package com.ltxc.google.csms.server.resource;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.domain.InventoryLineItem;
import com.ltxc.google.csms.server.domain.TemplateBase;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class ReceiveDRCTemplateLoader extends TemplateLoaderBase implements
		ITemplateLoader {
	private static Logger logger = Logger
			.getLogger(ReceiveDRCTemplateLoader.class.getName());

	public static String HeaderTemplateFile = "com/ltxc/google/csms/server/resource/ReceivingDRCHeaderTemplate.xml";
	public static String LineItemTemplateFile = "com/ltxc/google/csms/server/resource/ReceivingDRCLineItemTemplate.xml";

	@Override
	public String generateAPIXml(TransactionTypeEnum type,
			TransactionBase transaction) {
		try {
			List<? extends TemplateBase> lineItems = transaction.getLineItems();
			StringBuilder lineItemTemplates = new StringBuilder();
			String headerTemplate = loadTemplateFile(HeaderTemplateFile);
			String lineItemTemplate = loadTemplateFile(LineItemTemplateFile);
			boolean isAnyLineItemToReceive = false;
			for (TemplateBase tb : lineItems) {
				if (tb == null)
					continue;
				InventoryLineItem lineItem = (InventoryLineItem) tb;
				// if not validated, no need to be added into the API call to
				// receive
				if (lineItem.getValidationCode() != 0) {
					Map<String, Object> map = tb.getMappedValues();
					String v = StringHelper.format(lineItemTemplate, map);
					lineItemTemplates.append(v);
					isAnyLineItemToReceive = true;
				}
			}
			String v = "";
			if (isAnyLineItemToReceive) {
				Map<String, Object> map = transaction.getMappedValues();
				map.put(SharedConstants.Attribute_Inventory_LineItems,
						lineItemTemplates.toString());
				v = StringHelper.format(headerTemplate, map);
			}
			return v;

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(
					Level.SEVERE,
					"ReceiveDRCTemplateLoader:generateAPIXml - "
							+ xe.getMessage());
		}
		return null;
	}

	@Override
	public String generateAPIXmlLineByLine(TransactionTypeEnum type,
			TransactionBase transaction, int index) {
		return null;
	}

}
