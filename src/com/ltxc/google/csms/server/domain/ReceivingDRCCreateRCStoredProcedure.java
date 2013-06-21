package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;

import org.eclipse.persistence.annotations.Direction;
import org.eclipse.persistence.annotations.NamedStoredProcedureQuery;
import org.eclipse.persistence.annotations.StoredProcedureParameter;

import com.ltxc.google.csms.server.aa.api.WebServiceSoap;
import com.ltxc.google.csms.server.resource.ITemplateLoader;
import com.ltxc.google.csms.server.resource.ReceiveDRCCreateRCTemplateLoader;
import com.ltxc.google.csms.server.resource.TemplateLoaderFactory;
import com.ltxc.google.csms.server.service.InventoryReceivingDRCLoader;
import com.ltxc.google.csms.server.service.AAWebServiceResult;
import com.ltxc.google.csms.server.servlet.utils.EMFAstea;
import com.ltxc.google.csms.shared.ProcessStatusEnum;
import com.ltxc.google.csms.shared.TransactionTypeEnum;
import com.ltxc.google.csms.server.service.IAAWebServiceResultHandler;

@Entity
@NamedStoredProcedureQuery(name = ReceivingDRCCreateRCStoredProcedure.NAMED_QUERY, resultClass = ReceivingDRCCreateRCStoredProcedure.class, procedureName = ReceivingDRCCreateRCStoredProcedure.STOREDPROCEDURENAME, returnsResultSet = false, parameters = {
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_IN_TRANSACTIONTYPE, name = ReceivingDRCCreateRCStoredProcedure.PARAM_IN_TRANSACTIONTYPE, direction = Direction.IN, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_IN_WAREHOUSEID, name = ReceivingDRCCreateRCStoredProcedure.PARAM_IN_WAREHOUSEID, direction = Direction.IN, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_IN_OURREFNO, name = ReceivingDRCCreateRCStoredProcedure.PARAM_IN_OURREFNO, direction = Direction.IN, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONCODE, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONCODE, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_OUTPUTREFNO, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_OUTPUTREFNO, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONTEXT, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONTEXT, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONINT, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_VALIDATIONINT, direction = Direction.OUT, type = Integer.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_ID, name = ReceivingDRCCreateRCStoredProcedure.PARAM_OUT_ID, direction = Direction.OUT, type = Integer.class)})

public class ReceivingDRCCreateRCStoredProcedure implements
		IValidationStoredProcedure, Serializable {
	private static Logger logger = Logger
			.getLogger(ReceivingDRCCreateRCStoredProcedure.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAMED_QUERY = "ReceivingDRCCreateRC";
	public static final String STOREDPROCEDURENAME = "saltx.dbo._CSMobile_Receiving_DRC_Create";
	public static final String PARAM_IN_TRANSACTIONTYPE = "TransactionType";
	public static final String PARAM_IN_WAREHOUSEID = "WarehouseID";
	public static final String PARAM_IN_OURREFNO = "OurRefno";
	public static final String PARAM_OUT_VALIDATIONCODE = "ValidationCode";
	public static final String PARAM_OUT_VALIDATIONTEMPLATE = "ValidationTemplate";
	public static final String PARAM_OUT_VALIDATIONMESSAGE = "ValidationMessage";
	public static final String PARAM_OUT_OUTPUTREFNO = "OutputRefno";
	public static final String PARAM_OUT_VALIDATIONTEXT = "ValidationText";
	public static final String PARAM_OUT_VALIDATIONINT = "ValidationInt";
	public static final String PARAM_OUT_ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id = 0;

	// import
	private String transactionType;
	
	private String warehouseID;

	private String ourRefNo;


	// export

	private int validationCode;

	private String validationTemplate;

	private String validationMessage;

	private String outputRefNo;

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

/******************** static methods *********************************/
	public static ReceivingDRCCreateRCStoredProcedure init(InventoryTransaction transaction) {
		ReceivingDRCCreateRCStoredProcedure creation = null;
		if (transaction != null) {
			creation = new ReceivingDRCCreateRCStoredProcedure();
			creation.setTransactionType(transaction.getTransaction_type());
			Warehouse to_warehouse = transaction.getTo_warehouse();
			if(to_warehouse!=null)
				creation.setWarehouseID(to_warehouse.getWarehouse_id());
			creation.setOurRefNo(transaction.getOur_refno());
		}
		return creation;
	}

	public static final EntityManager entityManager() {
		return EMFAstea.get().createEntityManager();
	}
	
	public static ReceivingDRCCreateRCStoredProcedure callValidation(
			ReceivingDRCCreateRCStoredProcedure object) {
		EntityManager em = entityManager();
		// EntityTransaction etx = em.getTransaction();
		ReceivingDRCCreateRCStoredProcedure result = null;
		try {

			// etx.begin();
			Query query = em.createNamedQuery(ReceivingDRCCreateRCStoredProcedure.NAMED_QUERY);
			query.setParameter(PARAM_IN_TRANSACTIONTYPE, object.getTransactionType());
			query.setParameter(PARAM_IN_WAREHOUSEID, object.getWarehouseID());
			query.setParameter(PARAM_IN_OURREFNO, object.getOurRefNo());
			result = (ReceivingDRCCreateRCStoredProcedure) query
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
	
	public static int validate(InventoryTransaction transaction, StringBuilder log)
	{
		int validationCode = 0;
		
		// validate header first
		ReceivingDRCCreateRCStoredProcedure creation = ReceivingDRCCreateRCStoredProcedure.init(transaction);
		ReceivingDRCCreateRCStoredProcedure creationresult = ReceivingDRCCreateRCStoredProcedure.callValidation(creation);
		String message = "";
		if(creationresult==null)
		{
			message = "It failed when calling RC Creation Validation SP; Please inform the system adminstrator;";
			creation.setValidationMessage(message);
			creation.setValidationCode(validationCode);			
			log.append(message);
		}
		else
		{
			transaction.setOutputrefno(creationresult.getOutputRefNo());
			validationCode = creationresult.getValidationCode();
			
			if(validationCode==0) //not validated
			{
				message = "It failed to pass the validation process. "+creationresult.getValidationMessage();
				log.append(message);
			}else if (validationCode == 1) //validated
				message = "It pass the validation process. Ready to create RC.";
			else if (validationCode == 2) //no need to create RC. RC# should be in the 
			{
				//RC has been already created. Expect the rc# in the validationmessage
				message = creationresult.getValidationMessage();
				log.append("RC has already been created. RC#:").append(message).append(";");
			}			
		}
		transaction.setProcess_message(message);
		return validationCode;
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
		return this.validationMessage;
	}

}
