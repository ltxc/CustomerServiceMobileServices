package com.ltxc.google.csms.shared;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;



public class StringHelper {
	/**
	 ** An interpreter for strings with named placeholders. For example given the
	 * string "hello %(myName)" and the map <code>      
	 * <p>Map<String, Object> map = new HashMap<String, Object>();</p>      
	 * <p>map.put("myName", "world");</p>      
	 * </code> the call {@code format("hello %(myName)", map)} returns
	 * "hello world"
	 * 
	 * It replaces every occurrence of a named placeholder with its given value
	 * in the map. If there is a named place holder which is not found in the
	 * map then the string will retain that placeholder. Likewise, if there is
	 * an entry in the map that does not have its respective placeholder, it is
	 * ignored.
	 * 
	 * @param str
	 *            string to format
	 * @param values
	 *            to replace
	 * @return formatted string
	 */


	public static String getDomainName()
	{
		String baseURL = GWT.getHostPageBaseURL();
		if(baseURL.startsWith("http://"))
		{
			baseURL = baseURL.substring(7);
		}
		if(baseURL.startsWith("https://"))
		{
			baseURL = baseURL.substring(8);
		}
		
		if(baseURL.contains(":"))
		{
			baseURL = baseURL.substring(0,baseURL.indexOf(":"));
		}
		
		if(baseURL.contains("/"))
		{
			baseURL = baseURL.substring(0,baseURL.indexOf("/"));
		}
		return baseURL;
	}
	public static String format(String str, Map<String, Object> values) {
		StringBuilder builder = new StringBuilder(str);
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			int start;
			String pattern = "%{" + entry.getKey() + "}";
			Object o =entry.getValue();
			String value = (o!=null)? o.toString():"";
			// Replace every occurence of %(key) with value
			while ((start = builder.indexOf(pattern)) != -1) {
				builder.replace(start, start + pattern.length(), value);
			}
		}
		return builder.toString();
	}
	


	/**
	 * Shorten long displayName to something smaller if name is too long.
	 * 
	 * @param displayName
	 * @return a possibly truncated displayNames
	 */
	public static String truncateLongName(String displayName, int MAX) {
		// truncate string if longer than MAX
		final String SUFFIX = "...";

		if (displayName.length() < MAX)
			return displayName;

		String shortened = displayName.substring(0, MAX - SUFFIX.length())
				+ SUFFIX;

		return shortened;
	}


	public static boolean validateRegExp(String value, String pattern) {
		boolean isValidated = false;
		if (value == null || value.trim().isEmpty())
			return isValidated;
		value = value.trim();
		isValidated = value.matches(pattern);
		return isValidated;
	}
	
	

	public static String makeProperCase(String theString) throws java.io.IOException {
		if (theString==null)
			return null;
		char[] chars = theString.toCharArray();
		
		boolean precededBySpace = true;
		StringBuffer properCase = new StringBuffer();
		for(char c:chars) {
			if (c == ' ' || c == '"' || c == '(' || c == '.' || c == '/'
					|| c == '\\' || c == ',') {
				properCase.append(c);
				precededBySpace = true;
			} else {
				if (precededBySpace) {
					properCase.append(Character.toUpperCase(c));
				} else {
					properCase.append(c);
				}
				precededBySpace = false;
			}
		}

		return properCase.toString();

	}

	public static List<String> getStringList(Set<String> set)
	{
		if (set==null)
			return null;
		List<String> list = new ArrayList<String>();
		for(String s:set)
			list.add(s);
		return list;
	}
	
	//TODO: need to provide file deliminator from user proxy object. Guessing is not accurate.
	public static String getFileName(String filepath)
	{
		String result = filepath;
		if (filepath!=null)
		{
			int i = filepath.lastIndexOf("/");
			int l = filepath.length();
			if (i>=0)
			{
				if (i==(l-1))
					return "";
				result = filepath.substring(i+1);
			}
			else
			{
				i = filepath.lastIndexOf("\\");
				if (i>=0)
				{
					if (i==(l-1))
						return "";
					result = filepath.substring(i+1);
				}
			}
		}
		return result;
	}
	
	public static String getLastWord(String s, String deliminator)
	{
		String result = s;
		if (s!=null)
		{
			int index = s.lastIndexOf(deliminator);
			if (index>0)
			{
				if(s.length()>(index+1))
				 result = s.substring(index+1);
			}
		}
		return result;
	}
	  

	
	public static String getUrl(String baseUrl, String relativeUrl)
	{
		StringBuilder sb = new StringBuilder();
		if (baseUrl!=null)
		{
			baseUrl = baseUrl.endsWith("/")?baseUrl.substring(0, baseUrl.length()-1):baseUrl;
			sb.append(baseUrl).append(relativeUrl);			
		}
		return URL.encode(sb.toString());
	}
	

	
	public static String addAnchorTag(String s)
	{
		if(s!=null&&!s.trim().isEmpty())
			return "<a>"+s+"</a>";
		else
			return s;
	}
	
	public static final String removeExtension(String target)
	{
		String result = target;
		int index = -1;
		if(target!=null&&(index=target.indexOf("."))>0)
		{
			result = target.substring(0,index);
		}
		return result;
	}
	
	public static String formatDate(Date date, String formatter)
	{
		DateFormat dateFormat = null;
		if(formatter!=null)
			dateFormat = new SimpleDateFormat(formatter);
		else
			dateFormat = DateFormat.getDateInstance();
		return dateFormat.format(date);
	}
	




	

	

}