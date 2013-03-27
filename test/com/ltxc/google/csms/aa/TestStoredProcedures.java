package com.ltxc.google.csms.aa;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.ltxc.google.csms.server.domain.ReceivingValidationStoredProcedure;

public class TestStoredProcedures {

	@Test
	public void testReceivingValidationStoredProcedure() {
		ReceivingValidationStoredProcedure sp = new ReceivingValidationStoredProcedure();
		sp.setTransactionType("DRC");
		sp.setWarehouseID("A-SINGAPORE");
		sp.setOurRefNo("SH1212040003");
		sp.setLineNo(1);
		sp.setbPartID("865-1967-00");
		sp.setSerialNo("11087844");
		sp.setQty(1);
		sp.setInvTypeID("good");
		sp.setCompanyID("RVI");
		sp.setCarrierID("DHL");
		sp.setText1("Text1");
		sp.setInt1(1);
		
		ReceivingValidationStoredProcedure result = ReceivingValidationStoredProcedure.callValidation(sp);
		Assert.assertNotNull(result);
	}

}
