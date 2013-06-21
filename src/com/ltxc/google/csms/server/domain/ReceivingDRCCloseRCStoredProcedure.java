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
@NamedStoredProcedureQuery(name = ReceivingDRCCloseRCStoredProcedure.NAMED_QUERY, resultClass = ReceivingDRCCloseRCStoredProcedure.class, procedureName = ReceivingDRCCloseRCStoredProcedure.STOREDPROCEDURENAME, returnsResultSet = false, parameters = {
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_IN_TRANSACTIONTYPE, name = ReceivingDRCCloseRCStoredProcedure.PARAM_IN_TRANSACTIONTYPE, direction = Direction.IN, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_IN_OURREFNO, name = ReceivingDRCCloseRCStoredProcedure.PARAM_IN_OURREFNO, direction = Direction.IN, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONCODE, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONCODE, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONTEMPLATE, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONMESSAGE, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_OUTPUTREFNO, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_OUTPUTREFNO, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_UNDELIVEREDLINECOUNT, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_UNDELIVEREDLINECOUNT, direction = Direction.OUT, type = Integer.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONTEXT, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONTEXT, direction = Direction.OUT, type = String.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONINT, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_VALIDATIONINT, direction = Direction.OUT, type = Integer.class),
	@StoredProcedureParameter(queryParameter = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_ID, name = ReceivingDRCCloseRCStoredProcedure.PARAM_OUT_ID, direction = Direction.OUT, type = Integer.class)})
public class ReceivingDRCCloseRCStoredProcedure implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAMED_QUERY = "ReceivingDRCCloseRC";
	public static final String STOREDPROCEDURENAME = "saltx.dbo._CSMobile_Receiving_DRC_Close";
	public static final String PARAM_IN_TRANSACTIONTYPE = "TransactionType";
	public static final String PARAM_IN_OURREFNO = "OurRefno";
	public static final String PARAM_OUT_VALIDATIONCODE = "ValidationCode";
	public static final String PARAM_OUT_VALIDATIONTEMPLATE = "ValidationTemplate";
	public static final String PARAM_OUT_VALIDATIONMESSAGE = "ValidationMessage";
	public static final String PARAM_OUT_OUTPUTREFNO = "OutputRefno";
	public static final String PARAM_OUT_UNDELIVEREDLINECOUNT = "UndeliveredLineCount";
	public static final String PARAM_OUT_VALIDATIONTEXT = "ValidationText";
	public static final String PARAM_OUT_VALIDATIONINT = "ValidationInt";
	public static final String PARAM_OUT_ID = "id";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id = 0;

	// import
	private String transactionType;

	private String ourRefNo;


	// export

	private int validationCode;

	private String validationTemplate;

	private String validationMessage;

	private String outputRefNo;
	
	private int undeliveredLineCount;

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

	public int getUndeliveredLineCount() {
		return undeliveredLineCount;
	}

	public void setUndeliveredLineCount(int undeliveredLineCount) {
		this.undeliveredLineCount = undeliveredLineCount;
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
	
	public static final EntityManager entityManager() {
		return EMFAstea.get().createEntityManager();
	}
	
	public static ReceivingDRCCloseRCStoredProcedure init(InventoryTransaction transaction) {
		ReceivingDRCCloseRCStoredProcedure creation = null;
		if (transaction != null) {
			creation = new ReceivingDRCCloseRCStoredProcedure();
			creation.setTransactionType(transaction.getTransaction_type());
			creation.setOurRefNo(transaction.getOur_refno());
		}
		return creation;
	}
	

	public static int closeRC(InventoryTransaction transaction, StringBuilder log)
	{
		int validationCode = 0;
		if(transaction!=null)
		{
			ReceivingDRCCloseRCStoredProcedure sp = ReceivingDRCCloseRCStoredProcedure.init(transaction);
			sp = ReceivingDRCCloseRCStoredProcedure.callValidation(sp);
			if(sp!=null)
			{
				validationCode = sp.getValidationCode();	
				log.append(sp.getValidationMessage());
			}
			else
			{
				log.append("Close RC Store Procedure call failed. Please inform system adminstrator.");				
			}
			
			if(validationCode==1)
			{				
				transaction.setUndeliveredlinecount(sp.getUndeliveredLineCount());
			}
			else
				transaction.setUndeliveredlinecount(0);
			
		}
		return validationCode;
	}
	
	public static ReceivingDRCCloseRCStoredProcedure callValidation(
			ReceivingDRCCloseRCStoredProcedure object) {
		EntityManager em = entityManager();
		// EntityTransaction etx = em.getTransaction();
		ReceivingDRCCloseRCStoredProcedure result = null;
		try {

			// etx.begin();
			Query query = em.createNamedQuery(ReceivingDRCCloseRCStoredProcedure.NAMED_QUERY);
			query.setParameter(PARAM_IN_TRANSACTIONTYPE, object.getTransactionType());
			query.setParameter(PARAM_IN_OURREFNO, object.getOurRefNo());
			result = (ReceivingDRCCloseRCStoredProcedure) query
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
