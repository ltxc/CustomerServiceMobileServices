package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;
import org.eclipse.persistence.annotations.Direction;

import com.google.gwt.dom.client.Style.Float;
import com.ltxc.google.csms.server.servlet.utils.EMFAstea;
import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

@Entity
@NamedStoredProcedureQuery(name = ReceivingValidationStoredProcedure.NAMED_QUERY, resultClass = ReceivingValidationStoredProcedure.class, procedureName = ReceivingValidationStoredProcedure.STOREDPROCEDURENAME, returnsResultSet = false, parameters = {
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_TRANSACTIONTYPE, name = ReceivingValidationStoredProcedure.PARAM_IN_TRANSACTIONTYPE, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_WAREHOUSEID, name = ReceivingValidationStoredProcedure.PARAM_IN_WAREHOUSEID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_OURREFNO, name = ReceivingValidationStoredProcedure.PARAM_IN_OURREFNO, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_LINENO, name = ReceivingValidationStoredProcedure.PARAM_IN_LINENO, direction = Direction.IN, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_BPARTID, name = ReceivingValidationStoredProcedure.PARAM_IN_BPARTID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_SERIALNO, name = ReceivingValidationStoredProcedure.PARAM_IN_SERIALNO, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_QTY, name = ReceivingValidationStoredProcedure.PARAM_IN_QTY, direction = Direction.IN, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_INVTYPID, name = ReceivingValidationStoredProcedure.PARAM_IN_INVTYPID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_COMPANYID, name = ReceivingValidationStoredProcedure.PARAM_IN_COMPANYID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_CARRIERID, name = ReceivingValidationStoredProcedure.PARAM_IN_CARRIERID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_TEXT1, name = ReceivingValidationStoredProcedure.PARAM_IN_TEXT1, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_IN_INT1, name = ReceivingValidationStoredProcedure.PARAM_IN_INT1, direction = Direction.IN, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONCODE, name = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONCODE, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, name = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, name = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_OUTPUTREFNO, name = ReceivingValidationStoredProcedure.PARAM_OUT_OUTPUTREFNO, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_OUTPUTRP, name = ReceivingValidationStoredProcedure.PARAM_OUT_OUTPUTRP, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_ORIGDOCLINEID, name = ReceivingValidationStoredProcedure.PARAM_OUT_ORIGDOCLINEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_ITEMID, name = ReceivingValidationStoredProcedure.PARAM_OUT_ITEMID, direction = Direction.OUT, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_UNDELIVEREDLINECOUNT, name = ReceivingValidationStoredProcedure.PARAM_OUT_UNDELIVEREDLINECOUNT, direction = Direction.OUT, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_CUSTOMERCOMPANYID, name = ReceivingValidationStoredProcedure.PARAM_OUT_CUSTOMERCOMPANYID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_DESTINVTYPEID, name = ReceivingValidationStoredProcedure.PARAM_OUT_DESTINVTYPEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONTEXT, name = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONTEXT, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONINT, name = ReceivingValidationStoredProcedure.PARAM_OUT_VALIDATIONINT, direction = Direction.OUT, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_NODEID, name = ReceivingValidationStoredProcedure.PARAM_OUT_NODEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_DESTWAREHOUSEID, name = ReceivingValidationStoredProcedure.PARAM_OUT_DESTWAREHOUSEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_CCONTHID , name = ReceivingValidationStoredProcedure.PARAM_OUT_CCONTHID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_WARRTYPEID, name = ReceivingValidationStoredProcedure.PARAM_OUT_WARRTYPEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_POID, name = ReceivingValidationStoredProcedure.PARAM_OUT_POID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_PRIORITY, name = ReceivingValidationStoredProcedure.PARAM_OUT_PRIORITY, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_PCODEID, name = ReceivingValidationStoredProcedure.PARAM_OUT_PCODEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingValidationStoredProcedure.PARAM_OUT_ID, name = ReceivingValidationStoredProcedure.PARAM_OUT_ID, direction = Direction.OUT, type = Integer.class)})
public class ReceivingValidationStoredProcedure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAMED_QUERY = "ReceivingValidation";
	public static final String STOREDPROCEDURENAME = "saltx.dbo._CSMobile_Receiving_Validation";
	public static final String PARAM_IN_TRANSACTIONTYPE = "TransactionType";
	public static final String PARAM_IN_WAREHOUSEID = "WarehouseID";
	public static final String PARAM_IN_OURREFNO = "OurRefno";
	public static final String PARAM_IN_LINENO = "LineNo";
	public static final String PARAM_IN_BPARTID = "BpartID";
	public static final String PARAM_IN_SERIALNO = "SerialNo";
	public static final String PARAM_IN_QTY = "Qty";
	public static final String PARAM_IN_INVTYPID = "InvTypeID";
	public static final String PARAM_IN_COMPANYID = "CompanyID";
	public static final String PARAM_IN_CARRIERID = "CarrierID";
	public static final String PARAM_IN_TEXT1 = "Text1";
	public static final String PARAM_IN_INT1 = "Int1";
	public static final String PARAM_OUT_VALIDATIONCODE = "ValidationCode";
	public static final String PARAM_OUT_VALIDATIONTEMPLATE = "ValidationTemplate";
	public static final String PARAM_OUT_VALIDATIONMESSAGE = "ValidationMessage";
	public static final String PARAM_OUT_OUTPUTREFNO = "OutputRefno";
	public static final String PARAM_OUT_OUTPUTRP = "OutputRP";
	public static final String PARAM_OUT_ORIGDOCLINEID = "OrigDocLineID";
	public static final String PARAM_OUT_ITEMID = "ItemID";
	public static final String PARAM_OUT_UNDELIVEREDLINECOUNT = "UndeliveredLineCount";
	public static final String PARAM_OUT_CUSTOMERCOMPANYID = "CustomerCompanyID";
	public static final String PARAM_OUT_DESTINVTYPEID = "DestInvTypeID";
	public static final String PARAM_OUT_VALIDATIONTEXT = "ValidationText";
	public static final String PARAM_OUT_VALIDATIONINT = "ValidationInt";
	public static final String PARAM_OUT_NODEID = "NodeId";
	public static final String PARAM_OUT_DESTWAREHOUSEID = "DestWarehouseId";
	public static final String PARAM_OUT_CCONTHID = "CconthId ";
	public static final String PARAM_OUT_WARRTYPEID = "WarrTypeId";
	public static final String PARAM_OUT_POID = "PoId";
	public static final String PARAM_OUT_PRIORITY = "Priority";
	public static final String PARAM_OUT_PCODEID = "PcodeId";
	public static final String PARAM_OUT_ID = "id";
	// import
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id = 0;

	private String transactionType;

	private String warehouseID;

	private String ourRefNo;

	private int lineNo;

	private String bPartID;

	private String serialNo;

	private float qty;

	private String invTypeID;

	private String companyID;

	private String carrierID;

	private String text1;

	private int int1;

	// export

	private int validationCode;

	private String validationTemplate;

	private String validationMessage;

	private String outputRefNo;
	
	private String outputRP;

	private String origDocLineID;
	
	private int itemID;

	private int undeliveredLineCount;

	private String customerCompanyID;
	
	private String destInvTypeID;
	
	private String nodeId;
	
	private String destWarehouseId;
	
	private String cconthId;
	
	private String warrTypeId;
	
	private String poId;
	
	private String priority;
	
	private String pcodeId;
	

	private String validationText;

	private int validationInt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getOurRefNo() {
		return ourRefNo;
	}

	public void setOurRefNo(String ourRefNo) {
		this.ourRefNo = ourRefNo;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getbPartID() {
		return bPartID;
	}

	public void setbPartID(String bPartID) {
		this.bPartID = bPartID;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getInvTypeID() {
		return invTypeID;
	}

	public void setInvTypeID(String invTypeID) {
		this.invTypeID = invTypeID;
	}
	
	

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getDestWarehouseId() {
		return destWarehouseId;
	}

	public void setDestWarehouseId(String destWarehouseId) {
		this.destWarehouseId = destWarehouseId;
	}

	public String getCconthId() {
		return cconthId;
	}

	public void setCconthId(String cconthId) {
		this.cconthId = cconthId;
	}

	public String getWarrTypeId() {
		return warrTypeId;
	}

	public void setWarrTypeId(String warrTypeId) {
		this.warrTypeId = warrTypeId;
	}

	public String getPoId() {
		return poId;
	}

	public void setPoId(String poId) {
		this.poId = poId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPcodeId() {
		return pcodeId;
	}

	public void setPcodeId(String pcodeId) {
		this.pcodeId = pcodeId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCarrierID() {
		return carrierID;
	}

	public void setCarrierID(String carrierID) {
		this.carrierID = carrierID;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public int getInt1() {
		return int1;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public int getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(int validationCode) {
		this.validationCode = validationCode;
	}

	public String getValidationTemplate() {
		return validationTemplate;
	}

	public void setValidationTemplate(String validationTemplate) {
		this.validationTemplate = validationTemplate;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public String getOutputRefNo() {
		return outputRefNo;
	}

	public void setOutputRefNo(String outputRefNo) {
		this.outputRefNo = outputRefNo;
	}
	
	

	public String getOutputRP() {
		return outputRP;
	}

	public void setOutputRP(String outputRP) {
		this.outputRP = outputRP;
	}

	public String getOrigDocLineID() {
		return origDocLineID;
	}

	public void setOrigDocLineID(String origDocLineID) {
		this.origDocLineID = origDocLineID;
	}
	
	

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getUndeliveredLineCount() {
		return undeliveredLineCount;
	}

	public void setUndeliveredLineCount(int undeliveredLineCount) {
		this.undeliveredLineCount = undeliveredLineCount;
	}

	public String getCustomerCompanyID() {
		return customerCompanyID;
	}

	public void setCustomerCompanyID(String customerCompanyID) {
		this.customerCompanyID = customerCompanyID;
	}
	
	

	public String getDestInvTypeID() {
		return destInvTypeID;
	}

	public void setDestInvTypeID(String destInvTypeID) {
		this.destInvTypeID = destInvTypeID;
	}

	public String getValidationText() {
		return validationText;
	}

	public void setValidationText(String validationText) {
		this.validationText = validationText;
	}

	public int getValidationInt() {
		return validationInt;
	}

	public void setValidationInt(int validationInt) {
		this.validationInt = validationInt;
	}

	public ReceivingValidationStoredProcedure init(InventoryTransaction it,
			int lineitemno) {
		if (it != null) {
			this.setTransactionType(it.getTransaction_type());
			Warehouse to_warehouse = it.getTo_warehouse();
			if(to_warehouse!=null)
				this.setWarehouseID(to_warehouse.getWarehouse_id());
			this.setOurRefNo(it.getOur_refno());
			Company company = it.getCompany();
			if(company!=null)
				this.setCompanyID(company.getCompany_id());
			Carrier carrier = it.getCarrier();
			if(carrier!=null)
				this.setCarrierID(carrier.getCarrier_id());
			this.setText1("Text1");
			this.setInt1(1);
			@SuppressWarnings("unchecked")
			List<InventoryLineItem> lineitems = (List<InventoryLineItem>) it.getLineItems();
			if (lineitems != null) {
				InventoryLineItem lineitem = lineitems.get(lineitemno);

				this.setLineNo(lineitemno);
				this.setbPartID(lineitem.getBpart_id());
				this.setSerialNo(lineitem.getSerial_no());
				this.setQty(lineitem.getQty());
				this.setInvTypeID(lineitem.getInv_type_id());
				
			}

		}
		return this;
	}

	public static final EntityManager entityManager() {
		return EMFAstea.get().createEntityManager();
	}

	public static boolean validateInventoryTransaction(
			InventoryTransaction transaction) {
		boolean isSuccess = true;
		StringBuilder sb = new StringBuilder();
		int count = 0;
		if (transaction.getLineItems() == null
				|| (count = transaction.getLineItems().size()) < 1) {
			transaction.setProcess_status(ProcessStatusEnum.FAILED
					.getProcessStatusName());
			transaction
					.setProcess_message("Invalid transaction data, missing line item.");
			return false;
		}
		for(int i=0; i<count;i++)
		{
			InventoryLineItem lineItem = transaction.getInventoryLineItems().get(i);
			boolean isValidated = ReceivingValidationStoredProcedure.validateInventoryTransaction(transaction, i);
			if(!isValidated)
			{
				isSuccess = false;
				sb.append("Validation of Line Item with product ").append(lineItem.getBpart_id()).append(" failed. Reason: ").append(lineItem.getValidationMessage()).append(";");
			}
		}
		if(!isSuccess)
		{
			transaction.setProcess_status(ProcessStatusEnum.VALIDATIONFAILURE
					.getProcessStatusName());
			transaction
					.setProcess_message(sb.toString());		
		}
		return isSuccess;
	}
	
	public static boolean validateInventoryTransaction(
			InventoryTransaction transaction, int lineitmeindex) {
		boolean isSuccess = false;


		InventoryLineItem lineItem = transaction.getInventoryLineItems().get(lineitmeindex);
		ReceivingValidationStoredProcedure in = new ReceivingValidationStoredProcedure()
				.init(transaction, lineitmeindex);
		ReceivingValidationStoredProcedure out = ReceivingValidationStoredProcedure
				.callValidation(in);
		if (out == null || out.getValidationCode()==0) {
			// failed
			String failedMessage = "validation stored procedure call failure, return no information";
			if (out != null)
				failedMessage = out.getValidationMessage();
			lineItem.setValidationMessage(failedMessage);
			lineItem.setValidationCode(out.getValidationCode());
			return isSuccess;
		}
		

		//populate the rest of validation return
		lineItem.setValidationTemplate(out.getValidationTemplate());
		lineItem.setValidationMessage(out.getValidationMessage());
		lineItem.setOutputRefNo(out.getOutputRefNo());
		lineItem.setOutputRP(out.getOutputRP());
		lineItem.setOrigDocLineID(out.getOrigDocLineID());
		lineItem.setItemID(out.getItemID());
		lineItem.setUndeliveredLineCount(out.getUndeliveredLineCount());
		lineItem.setCustomerCompanyID(out.getCustomerCompanyID());
		lineItem.setDestInvTypeID(out.getDestInvTypeID());
		String validationText = out.getValidationText();
		lineItem.setValidationText1((validationText==null||validationText.trim().isEmpty())?SharedConstants.Exception_ValidationText_NotExist:validationText);
		lineItem.setValidationInt1(out.getValidationInt());
		
		//repair information
		lineItem.setNodeId(out.getNodeId());
		lineItem.setDestWarehouseId(out.getDestWarehouseId());
		lineItem.setCconthId(out.getCconthId());
		lineItem.setWarrTypeId(out.getWarrTypeId());
		lineItem.setPoId(out.getPoId());
		lineItem.setPriority(out.getPriority());
		lineItem.setPcodeId(out.getPcodeId());
		isSuccess = true;
		return isSuccess;
	}

	public static ReceivingValidationStoredProcedure callValidation(
			ReceivingValidationStoredProcedure object) {
		EntityManager em = entityManager();
		// EntityTransaction etx = em.getTransaction();
		ReceivingValidationStoredProcedure result = null;
		try {

			// etx.begin();
			Query query = em.createNamedQuery(ReceivingValidationStoredProcedure.NAMED_QUERY);
			query.setParameter(PARAM_IN_TRANSACTIONTYPE, object.transactionType);
			query.setParameter(PARAM_IN_WAREHOUSEID, object.warehouseID);
			query.setParameter(PARAM_IN_OURREFNO, object.ourRefNo);
			query.setParameter(PARAM_IN_LINENO, object.lineNo);
			query.setParameter(PARAM_IN_BPARTID, object.bPartID);
			query.setParameter(PARAM_IN_SERIALNO, object.serialNo);
			query.setParameter(PARAM_IN_QTY, object.qty);
			query.setParameter(PARAM_IN_INVTYPID, object.invTypeID);
			query.setParameter(PARAM_IN_COMPANYID, object.companyID);
			query.setParameter(PARAM_IN_CARRIERID, object.carrierID);
			query.setParameter(PARAM_IN_TEXT1, object.text1);
			query.setParameter(PARAM_IN_INT1, object.int1);
			result = (ReceivingValidationStoredProcedure) query
					.getSingleResult();

			// etx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			object = null;
			// etx.rollback();
		} finally {
			em.close();

		}
		return result;
	}


}
