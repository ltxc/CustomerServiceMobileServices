package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.ltxc.google.csms.shared.SharedConstants;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The persistent class for the InventoryLineItem database table.
 * 
 */
@Entity
@XmlRootElement
@Table(name="InventoryLineItem")
public class InventoryLineItem implements Serializable, TemplateBase {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(InventoryLineItem.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	@Column(name="bin_code_id")
	private String bin_code_id;

	@Column(name="bpart_id")
	private String bpart_id;

	@Column(name="inv_type_id")
	private String inv_type_id;

	private int lineitemnumber;

	@Column(name="man_adj_reason_id")
	private String man_adj_reason_id;

	@Column(name="qty")
	private float qty;

	@Column(name="serial_no")
	private String serial_no;
	
	@Column(name="cause")
	private String cause;	

    @Lob()
	@Column(name="process_message")
	private String process_message;
    
	@Column(name="xmldoc")
	private String xmldoc;
	
	private int version;


    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="inventoryheaderid")
	@XmlTransient
    private InventoryTransaction inventoryHeader;
    
// the following is the validation result    
    @XmlTransient
    @Transient
    private int validationCode;
    
    @XmlTransient
    @Transient
    private String validationTemplate;
    
    @XmlTransient
    @Transient
    private String validationMessage;
    
    @XmlTransient
    @Transient
    private String outputRefNo;
    
    @XmlTransient
    @Transient
    private String origDocLineID;
    
    @XmlTransient
    @Transient
    private int undeliveredLineCount;
    
    @XmlTransient
    @Transient
    private String customerCompanyID;

    @XmlTransient
    @Transient
    private String destInvTypeID;
    
    @XmlTransient
    @Transient
    private String validationText1;
    
    @XmlTransient
    @Transient
    private int validationInt1;    
    
    @XmlTransient
    @Transient
    private String destWarehouseId;//Repair Warehouse

    @XmlTransient
    @Transient
    private String cconthId;//Contract
    
    
    @XmlTransient
    @Transient
    private String warrTypeId ; //Warranty
    
    
    @XmlTransient
    @Transient
    private String poId; //P.O.
    
    @XmlTransient
    @Transient
    private String priority; //Priority
    
    @XmlTransient
    @Transient
    private String pcodeId; //Problem ID
    
    @XmlTransient
    @Transient
    private String nodeId; //Node ID
    
    @XmlTransient
    @Transient
    public int getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(int validationCode) {
		this.validationCode = validationCode;
	}
	
	
	@XmlTransient
	public String getValidationTemplate() {
		return validationTemplate;
	}

	public void setValidationTemplate(String validationTemplate) {
		this.validationTemplate = validationTemplate;
	}

	@XmlTransient
	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	@XmlTransient
	public String getOutputRefNo() {
		return outputRefNo;
	}

	public void setOutputRefNo(String outputRefNo) {
		this.outputRefNo = outputRefNo;
	}

	@XmlTransient
	public String getOrigDocLineID() {
		return origDocLineID;
	}

	public void setOrigDocLineID(String origDocLineID) {
		this.origDocLineID = origDocLineID;
	}

	@XmlTransient
	public int getUndeliveredLineCount() {
		return undeliveredLineCount;
	}

	public void setUndeliveredLineCount(int undeliveredLineCount) {
		this.undeliveredLineCount = undeliveredLineCount;
	}

	@XmlTransient
	public String getCustomerCompanyID() {
		return customerCompanyID;
	}

	public void setCustomerCompanyID(String customerCompanyID) {
		this.customerCompanyID = customerCompanyID;
	}

	
	@XmlTransient
	public String getDestInvTypeID() {
		return destInvTypeID;
	}

	public void setDestInvTypeID(String destInvTypeID) {
		this.destInvTypeID = destInvTypeID;
	}

	@XmlTransient
	public String getValidationText1() {
		return validationText1;
	}

	public void setValidationText1(String validationText1) {
		this.validationText1 = validationText1;
	}

	@XmlTransient
	public int getValidationInt1() {
		return validationInt1;
	}

	public void setValidationInt1(int validationInt1) {
		this.validationInt1 = validationInt1;
	}

	public InventoryLineItem() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public int getLineitemnumber() {
		return this.lineitemnumber;
	}

	public void setLineitemnumber(int lineitemnumber) {
		this.lineitemnumber = lineitemnumber;
	}


	public String getBin_code_id() {
		return bin_code_id;
	}

	public void setBin_code_id(String bin_code_id) {
		this.bin_code_id = bin_code_id;
	}

	public String getBpart_id() {
		return bpart_id;
	}

	public void setBpart_id(String bpart_id) {
		this.bpart_id = bpart_id;
	}

	public String getInv_type_id() {
		return inv_type_id;
	}

	public void setInv_type_id(String inv_type_id) {
		this.inv_type_id = inv_type_id;
	}

	public String getMan_adj_reason_id() {
		return man_adj_reason_id;
	}

	public void setMan_adj_reason_id(String man_adj_reason_id) {
		this.man_adj_reason_id = man_adj_reason_id;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	//repair information
	@XmlTransient
	public String getDestWarehouseId() {
		return destWarehouseId;
	}

	public void setDestWarehouseId(String destWarehouseId) {
		this.destWarehouseId = destWarehouseId;
	}

	@XmlTransient
	public String getCconthId() {
		return cconthId;
	}

	public void setCconthId(String cconthId) {
		this.cconthId = cconthId;
	}

	@XmlTransient
	public String getWarrTypeId() {
		return warrTypeId;
	}

	public void setWarrTypeId(String warrTypeId) {
		this.warrTypeId = warrTypeId;
	}

	@XmlTransient
	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	@XmlTransient
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@XmlTransient
	public String getPcodeId() {
		return pcodeId;
	}

	public void setPcodeId(String pcodeId) {
		this.pcodeId = pcodeId;
	}

	@XmlTransient
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getProcess_message() {
		return process_message;
	}

	public void setProcess_message(String process_message) {
		this.process_message = process_message;
	}

	public String getXmldoc() {
		return xmldoc;
	}

	public void setXmldoc(String xmldoc) {
		this.xmldoc = xmldoc;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@XmlTransient
	public InventoryTransaction getInventoryHeader() {
		return this.inventoryHeader;
	}


	public void setInventoryHeader(InventoryTransaction inventoryHeader) {
		this.inventoryHeader = inventoryHeader;
	}
	

	@Override
	public Map<String, Object> getMappedValues() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put(SharedConstants.Attribute_Inventory_Bin_Code_ID, bin_code_id!=null?bin_code_id:"");
		
		maps.put(SharedConstants.Attribute_Inventory_BPart_ID, bpart_id!=null?bpart_id:"");

		maps.put(SharedConstants.Attribute_Inventory_Inv_Type_ID, inv_type_id!=null?inv_type_id:"");

		maps.put(SharedConstants.Attribute_Inventory_LineItemNo, lineitemnumber>0?(new Integer(lineitemnumber)):0);

		maps.put(SharedConstants.Attribute_Inventory_Man_ADJ_Reason_ID, man_adj_reason_id!=null?man_adj_reason_id:"");

		maps.put(SharedConstants.Attribute_Inventory_QTY, qty>0?new Float(qty):new Float(0));

		maps.put(SharedConstants.Attribute_Inventory_Serial_No, serial_no!=null?serial_no:"");

		//repair information
		maps.put(SharedConstants.Attribute_Inventory_Node_ID, nodeId!=null?nodeId:"");
		maps.put(SharedConstants.Attribute_Inventory_Dest_Warehouse_ID, destWarehouseId!=null?destWarehouseId:"");
		maps.put(SharedConstants.Attribute_Inventory_Priority, priority!=null?priority:"");
		maps.put(SharedConstants.Attribute_Inventory_Cconth_ID, cconthId!=null?cconthId:"");
		maps.put(SharedConstants.Attribute_Inventory_War_Type_ID, warrTypeId!=null?warrTypeId:"");
		maps.put(SharedConstants.Attribute_Inventory_PO_ID, poId!=null?poId:"");
		maps.put(SharedConstants.Attribute_Inventory_Pcode_ID, pcodeId!=null?pcodeId:"");
		
		
		return maps;
	}
	
	
	public void afterUnmarshal(Unmarshaller u, Object parent) {
		   this.inventoryHeader = (InventoryTransaction) parent;
		} 

}