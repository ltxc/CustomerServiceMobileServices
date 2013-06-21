package com.ltxc.google.csms.server.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ltxc.google.csms.shared.SharedConstants;

public class AAWebServiceResult {
	
	private static final String SUCCESS = "True";
	private String error;
	private boolean isSuccess = false;
	private String result;
	private String xmlString;
	
	
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public AAWebServiceResult(String xmlString)
	{
		this.xmlString = xmlString;
	}
	/**
	 * 
	 * 
	 * @param resultHandler
	 * @return
	 */
	public boolean parse(IAAWebServiceResultHandler resultHandler)
	{
		boolean isParseSuccess = false;
		try{
			Document doc = AAWebServiceResult.getXMLDocument(xmlString, false);
			XPathExpression expr_success = AAWebServiceResult.compileXPath(SharedConstants.AA_Result_XPATH_SUCCESS);
			String sTrue = AAWebServiceResult.getXmlInnerText(doc, expr_success);
			if(sTrue.equalsIgnoreCase(SUCCESS))
			{
				this.isSuccess = true;
				XPathExpression expr_result = AAWebServiceResult.compileXPath(SharedConstants.AA_Result_XPATH_RESULT);
				this.result = AAWebServiceResult.getXmlInnerText(doc, expr_result);
				if(resultHandler!=null)
					resultHandler.onSuccess(this.result);
			}
			else
			{
				this.isSuccess = false;
				XPathExpression expr_error = AAWebServiceResult.compileXPath(SharedConstants.AA_Result_XPATH_ERROR);
				this.error = AAWebServiceResult.getXmlInnerText(doc, expr_error);
				if(resultHandler!=null)
					resultHandler.onFailure(this.error);
			}		
			
			isParseSuccess = true;
		}catch(Exception xe)
		{
			xe.printStackTrace();
			if(resultHandler!=null)
				resultHandler.onInvalidResult(xe.getMessage());
		}
		return isParseSuccess;
	}

	public static Document getXMLDocument(String xml, boolean isNameSpaceAware) throws SAXException, IOException, ParserConfigurationException
	{
	    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(isNameSpaceAware); // never forget this!
	    domFactory.setValidating(false);
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    Document doc = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
	    
	    return doc;
	}
	
	public static XPathExpression compileXPath(String xpathString) throws XPathExpressionException
	{
		
	    XPathFactory factory = XPathFactory.newInstance();
	    XPath xpath = factory.newXPath();
	    XPathExpression expr 
	     = xpath.compile(xpathString);
	    return expr;

	}
	
	public static String getXmlInnerText(Document doc, XPathExpression expr) throws XPathExpressionException
	{
		String s = null;
	    Object result = expr.evaluate(doc, XPathConstants.NODE);
	    Node node = (Node) result;
	    if(node!=null)
	    	s = node.getTextContent();
	    return s;
	}
	
}
