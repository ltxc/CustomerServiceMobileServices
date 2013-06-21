package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;

import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

import com.ltxc.google.csms.server.servlet.utils.EMFAstea;

@Entity
@NamedStoredProcedureQuery(name = ReceivingDRCValidationStoredProcedure.NAMED_QUERY, resultClass = ReceivingDRCValidationStoredProcedure.class, procedureName = ReceivingDRCValidationStoredProcedure.STOREDPROCEDURENAME, returnsResultSet = false, parameters = {
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_TRANSACTIONTYPE, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_TRANSACTIONTYPE, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_WAREHOUSEID, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_WAREHOUSEID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_OURREFNO, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_OURREFNO, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_LINENO, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_LINENO, direction = Direction.IN, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_BPARTID, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_BPARTID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_SERIALNO, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_SERIALNO, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_QTY, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_QTY, direction = Direction.IN, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_IN_INVTYPID, name = ReceivingDRCValidationStoredProcedure.PARAM_IN_INVTYPID, direction = Direction.IN, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONCODE, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONCODE, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_OUTPUTREFNO, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_OUTPUTREFNO, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_ORIGDOCLINEID, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_ORIGDOCLINEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_DESTINVTYPEID, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_DESTINVTYPEID, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONTEXT, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONTEXT, direction = Direction.OUT, type = String.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONINT, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_VALIDATIONINT, direction = Direction.OUT, type = Integer.class),
		@StoredProcedureParameter(queryParameter = ReceivingDRCValidationStoredProcedure.PARAM_OUT_ID, name = ReceivingDRCValidationStoredProcedure.PARAM_OUT_ID, direction = Direction.OUT, type = Integer.class), })
public class ReceivingDRCValidationStoredProcedure implements
		IValidationStoredProcedure, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAMED_QUERY = "ReceivingDRCValidation";
	public static final String STOREDPROCEDURENAME = "saltx.dbo._CSMobile_Receiving_DRC_Validation";
	public static final String PARAM_IN_TRANSACTIONTYPE = "TransactionType";
	public static final String PARAM_IN_WAREHOUSEID = "WarehouseID";
	public static final String PARAM_IN_OURREFNO = "OurRefno";
	public static final String PARAM_IN_LINENO = "LineNo";
	public static final String PARAM_IN_BPARTID = "BpartID";
	public static final String PARAM_IN_SERIALNO = "SerialNo";
	public static final String PARAM_IN_QTY = "Qty";
	public static final String PARAM_IN_INVTYPID = "InvTypeID";

	public static final String PARAM_OUT_VALIDATIONCODE = "ValidationCode";
	public static final String PARAM_OUT_VALIDATIONTEMPLATE = "ValidationTemplate";
	public static final String PARAM_OUT_VALIDATIONMESSAGE = "ValidationMessage";
	public static final String PARAM_OUT_OUTPUTREFNO = "OutputRefno";
	public static final String PARAM_OUT_ORIGDOCLINEID = "OrigDocLineID";
	public static final String PARAM_OUT_DESTINVTYPEID = "DestInvTypeID";
	public static final String PARAM_OUT_VALIDATIONTEXT = "ValidationText";
	public static final String PARAM_OUT_VALIDATIONINT = "ValidationInt";

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



	// export

	private int validationCode;

	private String validationTemplate;

	private String validationMessage;

	private String outputRefNo;

	private String origDocLineID;
	
	private String destInvTypeID;
	
	private String validationText;

	private int validationInt = 0;	
	
	
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

	public String getOutputRefNo() {
		return outputRefNo;
	}

	public void setOutputRefNo(String outputRefNo) {
		this.outputRefNo = outputRefNo;
	}

	public String getOrigDocLineID() {
		return origDocLineID;
	}

	public void setOrigDocLineID(String origDocLineID) {
		this.origDocLineID = origDocLineID;
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


	
public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

/****************** static methods ***************************************************/
	public static ReceivingDRCValidationStoredProcedure init(InventoryTransaction transaction,
			int lineitemno) {
		ReceivingDRCValidationStoredProcedure itemValidation = null;
		if (transaction != null) {
			itemValidation = new ReceivingDRCValidationStoredProcedure();
			itemValidation.setTransactionType(transaction.getTransaction_type());
			Warehouse to_warehouse = transaction.getTo_warehouse();
			if(to_warehouse!=null)
				itemValidation.setWarehouseID(to_warehouse.getWarehouse_id());
			itemValidation.setOurRefNo(transaction.getOur_refno());

			@SuppressWarnings("unchecked")
			List<InventoryLineItem> lineitems = (List<InventoryLineItem>) transaction.getLineItems();
			if (lineitems != null) {
				InventoryLineItem lineitem = lineitems.get(lineitemno);

				itemValidation.setLineNo(lineitemno);
				itemValidation.setbPartID(lineitem.getBpart_id());
				itemValidation.setSerialNo(lineitem.getSerial_no());
				itemValidation.setQty(lineitem.getQty());
				itemValidation.setInvTypeID(lineitem.getInv_type_id());
				
			}

		}
		return itemValidation;
	}
	
	public static boolean validate(InventoryTransaction transaction, StringBuilder log)
	{
		boolean isValidated = true;
		int count = transaction.getLineItems().size();
		for (int i = 0; i < count; i++) {
			// loop through each line item
			InventoryLineItem lineItem = transaction.getInventoryLineItems().get(i);
			if(lineItem==null)
				continue;
			// validate each one and add it into the list
			ReceivingDRCValidationStoredProcedure lineItemValidation = ReceivingDRCValidationStoredProcedure.init(transaction, i);
			ReceivingDRCValidationStoredProcedure lineItemValidationResult = ReceivingDRCValidationStoredProcedure.callValidation(lineItemValidation);
			String message = "Failed to validate line item "+i+" with part number:"+lineItemValidation.getbPartID()+";";
			if(lineItemValidationResult==null)
			{
				message = message+" Failed on store procedure call. Please contact system administrator;";
				lineItemValidation.setValidationMessage(message);
				lineItemValidation.setValidationCode(0);
				log.append(message);
				isValidated = false;
			}
			else
			{
				
				lineItem.setOutputRefNo(lineItemValidationResult.getOutputRefNo());
				lineItem.setDestInvTypeID(lineItemValidationResult.getDestInvTypeID());
				lineItem.setOrigDocLineID(lineItemValidationResult.getOrigDocLineID());
				
				if(!lineItemValidationResult.isValidated())
				{					
					log.append(message).append(lineItemValidationResult.getValidationMessage()).append(";");
					isValidated = false;
				}
				else
				{
					//retrieve the RC number
					transaction.setOutputrefno(lineItemValidationResult.getOutputRefNo());
				}
				
				lineItem.setValidationCode(lineItemValidationResult.getValidationCode());
			}
		}
		
		return isValidated;
	}

	
	
	public static final EntityManager entityManager() {
		return EMFAstea.get().createEntityManager();
	}
	
	public static ReceivingDRCValidationStoredProcedure callValidation(
			ReceivingDRCValidationStoredProcedure object) {
		EntityManager em = entityManager();
		// EntityTransaction etx = em.getTransaction();
		ReceivingDRCValidationStoredProcedure result = null;
		try {

			// etx.begin();
			Query query = em.createNamedQuery(ReceivingDRCValidationStoredProcedure.NAMED_QUERY);
			query.setParameter(PARAM_IN_TRANSACTIONTYPE, object.getTransactionType());
			query.setParameter(PARAM_IN_WAREHOUSEID, object.getWarehouseID());
			query.setParameter(PARAM_IN_OURREFNO, object.getOurRefNo());
			query.setParameter(PARAM_IN_LINENO, object.getLineNo());
			query.setParameter(PARAM_IN_BPARTID, object.getbPartID());
			query.setParameter(PARAM_IN_SERIALNO, object.getSerialNo());
			query.setParameter(PARAM_IN_QTY, object.getQty());
			query.setParameter(PARAM_IN_INVTYPID, object.getInvTypeID());

			result = (ReceivingDRCValidationStoredProcedure) query
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

/****************** Implementation of IValidationStoredProcedure *********************/	
	@Override
	public boolean isValidated() {
		if(this.validationCode==0)
			return false;
		else
			return true;
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return this.validationMessage;
	}

}
