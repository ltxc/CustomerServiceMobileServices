package com.ltxc.google.csms.server.resource;

import com.ltxc.google.csms.server.domain.TransactionBase;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

public interface ITemplateLoader {
	public String generateAPIXml(TransactionTypeEnum type, TransactionBase transaction);
	public String generateAPIXmlLineByLine(TransactionTypeEnum type, TransactionBase transaction, int index);
}
