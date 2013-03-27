package com.ltxc.google.csms.server.servlet.utils;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties; 
import java.util.Random;
import javax.mail.Message; 
import javax.mail.MessagingException; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 

import com.ltxc.google.csms.shared.StringHelper;



import java.util.List;




public class ServletUtils {

	  /**
	   * NOT UNIT TESTED Returns the URL (including query parameters) minus the scheme, host, and context path. This method probably
	   * be moved to a more general purpose class.
	   */
	  public static String getRelativeUrl(HttpServletRequest request) {
		if (request!=null)
		{
	    String baseUrl = null;

	    if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
	      baseUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
	    else
	      baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	          + request.getContextPath();

	    StringBuffer buf = request.getRequestURL();

	    if (request.getQueryString() != null) {
	      buf.append("?");
	      buf.append(request.getQueryString());
	    }

	    return buf.substring(baseUrl.length());
		}else
			return null;
	    
	  }

	  /**
	   * NOT UNIT TESTED Returns the base url (e.g, <tt>http://myhost:8080/myapp</tt>) suitable for using in a base tag or building
	   * reliable urls.
	   */
	  public static String getBaseUrl(HttpServletRequest request) {
	    if ((request.getServerPort() == 80) || (request.getServerPort() == 443))
	      return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
	    else
	      return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	          + request.getContextPath();
	  }

	  /**
	   * Returns the file specified by <tt>path</tt> as returned by <tt>ServletContext.getRealPath()</tt>.
	   */
	  public static File getRealFile(HttpServletRequest request, String path) {

		if (request!=null)
		{
		if (path == null)
			return new File(request.getSession().getServletContext().getContextPath());
		else
			return new File(request.getSession().getServletContext().getRealPath(path));
		}
		else
		{
			String currentPath = System.getProperty("user.dir");
			return new File(currentPath+File.separator+"war"+File.separator+path!=null?path:"");

		}
	  }
	  
	  public static void sendEmail(InternetAddress from, InternetAddress to, InternetAddress cc, InternetAddress bcc, String subject, String msgBody) throws MessagingException
	  {
		  
		  Properties props = new Properties(); 
		  Session session = Session.getDefaultInstance(props, null); 


		  Message msg = new MimeMessage(session); 
		  msg.setFrom(from); 
		  msg.addRecipient(Message.RecipientType.TO,to);
		  if (cc!=null)
			  msg.addRecipient(Message.RecipientType.CC, cc);
		  if (bcc!=null)
			  msg.addRecipient(Message.RecipientType.BCC, bcc);
		  msg.setSubject(subject); 
		  msg.setText(msgBody); 
		  Transport.send(msg); 		 

	  }
	  

	  
	  public static <T> List<T> mergeList(List<T> a, List<T> b)
	  {
		  List<T> merged = new ArrayList<T>();
		  if (a!=null)
		  {
			  for(T t: a)
			  {
				  merged.add(t);
			  }
		  }
		  if (b!=null)
		  {
			  for(T t: b)
			  {
				  merged.add(t);
			  }
		  }		  
		  return merged;
	  }

//	  public static String generateRandomPassword()
//	  {
//		  return generateRandomString(SharedConstants.VALIDATIONCODE_LENGTH);
//	  }
	  
	  public static String generateRandomString(int length)
	  {
		  Random RANDOM = new SecureRandom();
		  
	      // Pick from some letters that won't be easily mistaken for each
	      // other. So, for example, omit o O and 0, 1 l and L.
	      String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

	      String pw = "";
	      for (int i=0; i<length; i++)
	      {
	          int index = (int)(RANDOM.nextDouble()*letters.length());
	          pw += letters.substring(index, index+1);
	      }
	      return pw;
	  }
	  
		public static final Date getCurrentDate() {
			return Calendar.getInstance().getTime();
		}
		
		public static final String getTimestampString()
		{
			Date date = getCurrentDate();
			return getTimestampString(date, "MM-dd-yyyy kk:mm:ss");
		}
		
		public static final Date convertFromMillisecondToDate(long millisecond)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(millisecond);
			
			return calendar.getTime();
		}
		
		public static final String getTimestampString(Date date, String datePattern)
		{
			if(datePattern==null||datePattern.trim().isEmpty())
				datePattern = "MM-dd-yyyy kk:mm:ss";
			SimpleDateFormat dateformat = new SimpleDateFormat(datePattern);
			return dateformat.format(date);
		}
		
		public static final String removeLastOccurred(String target, String extension)
		{
			String result = target;
			if (target!=null&&extension!=null&&target.endsWith(extension))
			{
				result = target.substring(0,target.length()-extension.length());
			}
			
			return result;
		}
		
		
		
		public static final String addTimestampToCompressFile(String target)
		{
			String result = StringHelper.removeExtension(target);
			String timestamp = getTimestampString();
			result = (result==null?timestamp:result+"_"+timestamp);
			return result;			
			
		}
		
		public static boolean isNotWindows()
		{
			return "/".equals(File.separator);
		}
}
