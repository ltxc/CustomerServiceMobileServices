package com.ltxc.google.csms.server.resource;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;


public class TemplateLoaderBase {
	private static Logger logger = Logger
			.getLogger(TemplateLoaderBase.class.getName());
	protected String loadTemplateFile(String templateFilePath)
	{
		
		try{
			
			InputStream inputStream = TemplateLoaderBase.class.getClassLoader().getResourceAsStream(templateFilePath);
			StringWriter writer = new StringWriter();
			IOUtils.copy(inputStream, writer, "UTF-8");
			String templateString = writer.toString();
			return templateString;
			//prepare the 
		}catch(Exception xe)
		{
			xe.printStackTrace();
			logger.log(Level.SEVERE, "TemplateLoaderBase - templateFilePath does not exist. "+xe.getMessage());
		}
		return null;
	}
	
}
