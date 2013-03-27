package com.ltxc.google.csms.server.service;

import com.ltxc.google.csms.server.domain.TransactionBase;

public interface ILoader {
	boolean  load(TransactionBase tb);
	void setWebServiceAPI(WebServiceAPI _webServiceAPI) throws LoaderException;
	
}
