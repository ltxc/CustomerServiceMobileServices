package com.ltxc.google.csms.aa;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ltxc.google.csms.server.domain.CycleCount;
import com.ltxc.google.csms.server.domain.CycleCountMaster;
import com.ltxc.google.csms.server.servlet.utils.ServletUtils;
import com.ltxc.google.csms.shared.SharedConstants;

public class TestCycleCount {

	@Test
	public void test() {
		List<CycleCount> cycleCounts = new ArrayList<CycleCount>();
		String warehouse_id = "A-SINGAPORE";
		String bin_code_id = "BS10A";
		//clear bin
		boolean isBinCleared = CycleCount.clearCycleBinCount(warehouse_id, bin_code_id);
		Assert.assertTrue(isBinCleared);
		//Bin test
		CycleCount cycleCount = new CycleCount();
		cycleCount.setCycleType(SharedConstants.CYCLE_TYPE_BIN);
		cycleCount.setWarehouseId(warehouse_id);
		cycleCount.setBinCodeId(bin_code_id);
		cycleCount.setSource(SharedConstants.CYCLE_TYPE_SOURCE);
		cycleCount.setBpartId("865-1627-05");
		cycleCount.setQty(3);
		cycleCount.setWho("jlu");	
		cycleCount.setLastupdated(ServletUtils.getCurrentDate());
		cycleCounts.add(cycleCount);
		
		cycleCount = new CycleCount();
		cycleCount.setCycleType(SharedConstants.CYCLE_TYPE_BIN);
		cycleCount.setWarehouseId(warehouse_id);
		cycleCount.setBinCodeId(bin_code_id);
		cycleCount.setSource(SharedConstants.CYCLE_TYPE_SOURCE);
		cycleCount.setBpartId("865-1657-00");
		cycleCount.setQty(3);
		cycleCount.setWho("jlu");	
		cycleCount.setLastupdated(ServletUtils.getCurrentDate());
		cycleCounts.add(cycleCount);
		
		cycleCount = new CycleCount();
		cycleCount.setCycleType(SharedConstants.CYCLE_TYPE_BIN);
		cycleCount.setWarehouseId(warehouse_id);
		cycleCount.setBinCodeId(bin_code_id);
		cycleCount.setSource(SharedConstants.CYCLE_TYPE_SOURCE);
		cycleCount.setBpartId("865-1657-01");
		cycleCount.setQty(3);
		cycleCount.setWho("jlu");		
		cycleCount.setLastupdated(ServletUtils.getCurrentDate());
		cycleCounts.add(cycleCount);
		
		for(CycleCount cc: cycleCounts)
		{
			CycleCount.saveCycleCountEntry(cc, false);
		}
		
		//update Bin Counted
		CycleCountMaster.updateCycleCountMasterBinCounted(warehouse_id, bin_code_id);
		
		
		
		//Part test		
		//clear part cycle count
		bin_code_id = "AS5C";
		String bpart_id = "09909647-00";
		boolean isPartCleared = CycleCount.clearCyclePartCount(warehouse_id, bin_code_id, bpart_id);
		Assert.assertTrue(isPartCleared);
		
		cycleCounts.clear();
		
		cycleCount = new CycleCount();
		cycleCount.setCycleType(SharedConstants.CYCLE_TYPE_PART);
		cycleCount.setWarehouseId(warehouse_id);
		cycleCount.setBinCodeId(bin_code_id);
		cycleCount.setSource(SharedConstants.CYCLE_TYPE_SOURCE);
		cycleCount.setBpartId(bpart_id);
		cycleCount.setSerialNo("12321321");
		cycleCount.setQty(1);
		cycleCount.setWho("jlu");	
		cycleCount.setLastupdated(ServletUtils.getCurrentDate());
		cycleCounts.add(cycleCount);
		
		cycleCount = new CycleCount();
		cycleCount.setCycleType(SharedConstants.CYCLE_TYPE_PART);
		cycleCount.setWarehouseId(warehouse_id);
		cycleCount.setBinCodeId(bin_code_id);
		cycleCount.setSource(SharedConstants.CYCLE_TYPE_SOURCE);
		cycleCount.setBpartId(bpart_id);
		cycleCount.setSerialNo("12321sdfa321");
		cycleCount.setQty(1);
		cycleCount.setWho("jlu");	
		cycleCount.setLastupdated(ServletUtils.getCurrentDate());
		cycleCounts.add(cycleCount);
		
		cycleCount = new CycleCount();
		cycleCount.setCycleType(SharedConstants.CYCLE_TYPE_PART);
		cycleCount.setWarehouseId(warehouse_id);
		cycleCount.setBinCodeId(bin_code_id);
		cycleCount.setSource(SharedConstants.CYCLE_TYPE_SOURCE);
		cycleCount.setBpartId(bpart_id);
		cycleCount.setQty(4);
		cycleCount.setWho("jlu");	
		cycleCount.setLastupdated(ServletUtils.getCurrentDate());
		cycleCounts.add(cycleCount);
		
		for(CycleCount cc: cycleCounts)
		{
			CycleCount.saveCycleCountEntry(cc, false);
		}
		
		//counted update
		CycleCountMaster.updateCycleCountMasterPartCounted(warehouse_id, bin_code_id, bpart_id);
	}

}
