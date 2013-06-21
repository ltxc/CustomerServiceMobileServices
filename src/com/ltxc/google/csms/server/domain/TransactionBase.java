package com.ltxc.google.csms.server.domain;

import java.util.Date;
import java.util.List;

import com.ltxc.google.csms.shared.TransactionTypeEnum;

public interface TransactionBase extends TemplateBase {
	List<? extends TemplateBase> getLineItems();
	TransactionTypeEnum getTransactionType();
	String getTransactionID();
	Date getCreatedDate();
	void setCreatedDate(Date date);
	Date getProcessDate();
	void setProcessDate(Date date);
	String getProcessMessage();
	String getProcessStatus();
	int getProcessActionCode(); //0 - check status, 1 - process
	void setProcessActionCode(int actioncode);
	void persist();
	void update();
	void remove();
	void setIpad_id(String ipad_id);
	String getIpad_id();
	TransactionBase searchTransactionByIPADID(String ipadid);
}
