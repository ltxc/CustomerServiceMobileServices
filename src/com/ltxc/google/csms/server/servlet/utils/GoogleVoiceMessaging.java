package com.ltxc.google.csms.server.servlet.utils;
import java.io.IOException;
import java.util.logging.Logger;

import com.ltxc.google.csms.shared.SharedConstants;
import com.techventus.server.voice.Voice;

public class GoogleVoiceMessaging {
	
	private static final Logger logger = Logger.getLogger(GoogleVoiceMessaging.class.getName());

	public static GoogleVoiceMessaging instance;
	
	/**
	 * 
	 * @return singleton object that holds the global text messaging account
	 */
	public static GoogleVoiceMessaging get() 
	{
		if (instance==null)
			instance = new GoogleVoiceMessaging();
		return instance;
	}
	
	public static GoogleVoiceMessaging get(String gmailUserName, String gmailPassword)
	{
		return new GoogleVoiceMessaging(gmailUserName,gmailPassword);
	}
	
	private String voice_email;
	private String voice_password;
	private Voice voice;
	
	private GoogleVoiceMessaging() 
	{
		voice_email = SharedConstants.SYSTEM_GOOGLE_VOICE_EMAIL;
		voice_password = SharedConstants.SYSTEM_GOOGLE_VOICE_PASSWORD;
	}
	
	private GoogleVoiceMessaging(String gmailUserName, String gmailPassword)
	{
		voice_email = gmailUserName;
		voice_password = gmailPassword;
	}
	
	private void voiceLogin() throws IOException
	{
		if (voice==null)
			voice = new Voice(voice_email, voice_password);
		if (!voice.isLoggedIn())
			voice.login();
	}
	
	public void sendTextMessage(
			String phonenum,
			String alert
					) {

		 try 
         { 
                 logger.info("voice is going to log in"); 
         		 voiceLogin();
                 logger.info("logged in"); 
                 voice.sendSMS(phonenum, alert); 
                 logger.info("sms sent was sent to "+phonenum); 
                
         } 
         catch(Exception e) 
         { 
                 e.printStackTrace();                  
                 logger.severe("GoogleVoiceMessaging:sendTextMessage -- error: "+e.getMessage());
                 
         } 

	}
}

