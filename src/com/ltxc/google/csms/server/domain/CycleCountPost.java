package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CycleCountPost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String who;
	private String warehouseId;
	private String binCodeId;
	private String bpartId;
	private String cycleType;
	private String isclear = "true";
	private List<CycleCountCount> counts;
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
	public String getBinCodeId() {
		return binCodeId;
	}
	public void setBinCodeId(String binCodeId) {
		this.binCodeId = binCodeId;
	}
	public String getBpartId() {
		return bpartId;
	}
	public void setBpartId(String bpartId) {
		this.bpartId = bpartId;
	}
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	public String getIsclear() {
		return isclear;
	}
	public void setIsclear(String isclear) {
		this.isclear = isclear;
	}
	public List<CycleCountCount> getCounts() {
		return counts;
	}
	public void setCounts(List<CycleCountCount> counts) {
		this.counts = counts;
	}
	

	
	
	
}
