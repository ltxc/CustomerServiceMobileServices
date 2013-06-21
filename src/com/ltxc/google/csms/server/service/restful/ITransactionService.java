package com.ltxc.google.csms.server.service.restful;

import com.ltxc.google.csms.server.domain.ProcessResult;
import com.ltxc.google.csms.server.domain.TransactionBase;


/**
 * 
 * @author JLU
 *	define the interface methods called by TransactionService
 */
public interface ITransactionService {
	void preLoad(TransactionBase transaction, ProcessResult processResult);
	void postLoad(TransactionBase transaction, ProcessResult processResult);
	//TransactionBase searchExistingTransaction(TransactionBase transaction);	
}
