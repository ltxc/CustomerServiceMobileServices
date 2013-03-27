package com.ltxc.google.csms.server.resource;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ltxc.google.csms.server.domain.TemplateBase;
import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public class MiscTemplateLoader extends TemplateLoaderBase implements
		ITemplateLoader {
	public static String HeaderTemplateFile = "com/ltxc/google/csms/server/resource/NewMiscReceiveHeaderTemplate.xml";
	public static String LineItemTemplateFile = "com/ltxc/google/csms/server/resource/NewMiscReceiveLineItemTemplate.xml";

	private static Logger logger = Logger.getLogger(MiscTemplateLoader.class
			.getName());

	@Override
	public String generateAPIXml(TransactionTypeEnum type,
			TransactionBase transaction) {
		try {
			List<? extends TemplateBase> lineItems = transaction.getLineItems();
			StringBuilder lineItemTemplates = new StringBuilder();
			String headerTemplate = loadTemplateFile(HeaderTemplateFile);
			String lineItemTemplate = loadTemplateFile(LineItemTemplateFile);
			for (TemplateBase tb : lineItems) {
				Map<String, Object> map = tb.getMappedValues();
				String v = StringHelper.format(lineItemTemplate, map);

				lineItemTemplates.append(v);
			}
			Map<String, Object> map = transaction.getMappedValues();
			map.put(SharedConstants.Attribute_Inventory_LineItems,
					lineItemTemplates.toString());
			String v = StringHelper.format(headerTemplate, map);
			return v;

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(Level.SEVERE, "MiscTemplateLoader:generateAPIXml - "
					+ xe.getMessage());
		}
		return null;
	}

	@Override
	public String generateAPIXmlLineByLine(TransactionTypeEnum type,
			TransactionBase transaction, int index) {
		try {
			TemplateBase tb = transaction.getLineItems().get(index);
			StringBuilder lineItemTemplates = new StringBuilder();
			String headerTemplate = loadTemplateFile(HeaderTemplateFile);
			String lineItemTemplate = loadTemplateFile(LineItemTemplateFile);
			Map<String, Object> linemap = tb.getMappedValues();
			String linevalue = StringHelper.format(lineItemTemplate, linemap);
			lineItemTemplates.append(linevalue);
			Map<String, Object> headermap = transaction.getMappedValues();
			headermap.put(SharedConstants.Attribute_Inventory_LineItems,
					lineItemTemplates.toString());
			String headervalue = StringHelper.format(headerTemplate, headermap);
			return headervalue;

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(Level.SEVERE, "TemplateLoaderFactory:generateAPIXml - "
					+ xe.getMessage());
		}
		return null;
	}

}
