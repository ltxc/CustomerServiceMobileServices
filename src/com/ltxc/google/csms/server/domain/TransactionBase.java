package com.ltxc.google.csms.server.domain;

import java.util.Date;
import java.util.List;

import com.ltxc.google.csms.shared.TransactionTypeEnum;

public interface TransactionBase extends TemplateBase {
	List<? extends TemplateBase> getLineItems();
	TransactionTypeEnum getTransactionType();
	String getTransactionID();
	Date getProcessDate();
	String getProcessMessage();
	String getProcessStatus();
	int getProcessActionCode(); //0 - check status, 1 - process
	void persist();
	void update();
	void remove();
}
