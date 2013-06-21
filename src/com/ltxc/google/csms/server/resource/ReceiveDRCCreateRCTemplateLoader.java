package com.ltxc.google.csms.server.resource;


import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;


public class ReceiveDRCCreateRCTemplateLoader extends TemplateLoaderBase implements ITemplateLoader {
	private static Logger logger = Logger.getLogger(ReceiveDRCCreateRCTemplateLoader.class
			.getName());

	public static String templateFile = "com/ltxc/google/csms/server/resource/ReceivingDRCRCCreationTemplate.xml";


	@Override
	public String generateAPIXml(TransactionTypeEnum type,
			TransactionBase transaction) {
		try {
			
			String template = loadTemplateFile(templateFile);			
			Map<String, Object> map = transaction.getMappedValues();
			String v = StringHelper.format(template, map);
			return v;

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(Level.SEVERE, "ReceiveDRCCreateRCTemplateLoader:generateAPIXml - "
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
