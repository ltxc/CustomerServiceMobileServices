package com.ltxc.google.csms.server.domain;
/***
 * Only for restful interface
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CycleCountCount implements Serializable {
	private String target;
	private float qty = 1;
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public float getQty() {
		return qty;
	}
	public void setQty(float qty) {
		this.qty = qty;
	}
	
	
}
