package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.ltxc.google.csms.shared.SharedConstants;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@XmlRootElement
@Table(name="ShippingLineItem")
@Entity
public class ShippingLineItem implements Serializable, TemplateBase{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(ShippingLineItem.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	@Column(name="bpart_id")
	private String bpart_id;

	@Column(name="demand_id")
	private String demand_id;

	@Column(name="fr_inv_type_id")
	private String fr_inv_type_id;

	private int ifield1;
	
	@Column(name="item_id")
	private String item_id;

	@Column(name="ldmnd_stat_id")
	private String ldmnd_stat_id;

	private int lineitemnumber;

	@Column(name="orig_doc_id")
	private String orig_doc_id;

	@Column(name="doc_type_id")
	private String doc_type_id;
	
	@Column(name="serial_no")
	private String serial_no;
	

	private String sfield1;

	@Column(name="shipped_qty")
	private float shipped_qty;
	
	//id to the shippinglist table
	@Column(name="shippinglist_id")
	private Long shippinglist_id;

	private int version;

    @Lob()
	private String xmldoc;

	//bi-directional many-to-one association to ShippingHeader
    @ManyToOne
	@JoinColumn(name="shippingheaderid")
	private ShippingTransaction shippingHeader;

    public ShippingLineItem() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getIfield1() {
		return this.ifield1;
	}

	public void setIfield1(int ifield1) {
		this.ifield1 = ifield1;
	}



	public int getLineitemnumber() {
		return this.lineitemnumber;
	}

	public void setLineitemnumber(int lineitemnumber) {
		this.lineitemnumber = lineitemnumber;
	}



	public String getSfield1() {
		return this.sfield1;
	}

	public void setSfield1(String sfield1) {
		this.sfield1 = sfield1;
	}




	public Long getShippinglist_id() {
		return shippinglist_id;
	}

	public void setShippinglist_id(Long shippinglist_id) {
		this.shippinglist_id = shippinglist_id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getXmldoc() {
		return this.xmldoc;
	}

	public void setXmldoc(String xmldoc) {
		this.xmldoc = xmldoc;
	}
	
	
	

	public String getBpart_id() {
		return bpart_id;
	}

	public void setBpart_id(String bpart_id) {
		this.bpart_id = bpart_id;
	}

	public String getDemand_id() {
		return demand_id;
	}

	public void setDemand_id(String demand_id) {
		this.demand_id = demand_id;
	}



	public String getFr_inv_type_id() {
		return fr_inv_type_id;
	}

	public void setFr_inv_type_id(String fr_inv_type_id) {
		this.fr_inv_type_id = fr_inv_type_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getLdmnd_stat_id() {
		return ldmnd_stat_id;
	}

	public void setLdmnd_stat_id(String ldmnd_stat_id) {
		this.ldmnd_stat_id = ldmnd_stat_id;
	}

	public String getOrig_doc_id() {
		return orig_doc_id;
	}

	public void setOrig_doc_id(String orig_doc_id) {
		this.orig_doc_id = orig_doc_id;
	}
	
	

	public String getDoc_type_id() {
		return doc_type_id;
	}

	public void setDoc_type_id(String doc_type_id) {
		this.doc_type_id = doc_type_id;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public float getShipped_qty() {
		return shipped_qty;
	}

	public void setShipped_qty(float shipped_qty) {
		this.shipped_qty = shipped_qty;
	}

	public ShippingTransaction getShippingHeader() {
		return this.shippingHeader;
	}

	public void setShippingHeader(ShippingTransaction shippingHeader) {
		this.shippingHeader = shippingHeader;
	}
	
	


	@Override
	public Map<String, Object> getMappedValues() {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		
		maps.put(SharedConstants.Attribute_Shipping_LDMND_STAT_ID, ldmnd_stat_id!=null?ldmnd_stat_id:"");
		
		maps.put(SharedConstants.Attribute_Shipping_BPart_ID, bpart_id!=null?bpart_id:"");

		maps.put(SharedConstants.Attribute_Shipping_DEMAND_ID, demand_id!=null?demand_id:"");

		maps.put(SharedConstants.Attribute_Inventory_LineItemNo, lineitemnumber>0?(new Integer(lineitemnumber)):0);

		maps.put(SharedConstants.Attribute_Shipping_ORIG_DOC_ID, orig_doc_id!=null?orig_doc_id:"");
		
		maps.put(SharedConstants.Attribute_Shipping_FR_INV_TYPE_ID, fr_inv_type_id!=null?fr_inv_type_id:"");

		maps.put(SharedConstants.Attribute_Shipping_QTY, shipped_qty>0?new Float(shipped_qty):new Float(0));

		maps.put(SharedConstants.Attribute_Inventory_Serial_No, serial_no!=null?serial_no:"");
		


		//validation result
		
		
		return maps;
	}

	@Override
	public void afterUnmarshal(Unmarshaller u, Object parent) {
		this.shippingHeader = (ShippingTransaction)parent;
		
	}
	
}