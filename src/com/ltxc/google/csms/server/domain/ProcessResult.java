package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "processresult" ) 
public class ProcessResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String transactionType;
	
	private String transactionId;
	
	private String process_status;
	
	private String process_message;
	
	private Date process_date;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getProcess_status() {
		return process_status;
	}

	public void setProcess_status(String process_status) {
		this.process_status = process_status;
	}

	public String getProcess_message() {
		return process_message;
	}

	public void setProcess_message(String process_message) {
		this.process_message = process_message;
	}

	public Date getProcess_date() {
		return process_date;
	}

	public void setProcess_date(Date process_date) {
		this.process_date = process_date;
	}
	
	
}
