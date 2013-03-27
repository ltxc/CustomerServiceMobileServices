package com.ltxc.google.csms.server.domain;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;

import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * The persistent class for the InventoryHeader database table.
 * 
 */
@Entity
@Table(name = "InventoryHeader")
@XmlRootElement

public class InventoryTransaction implements Serializable, TransactionBase {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(InventoryTransaction.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	@Column(name="carrier_refno")
	private String carrier_refno;

	@Column(name="created_by")
	private String created_by;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date created_date;

	@Column(name="ipad_id")
	private String ipad_id;

	@Column(name="no_of_packages")
	private int no_of_packages;

	@Column(name="our_refno")
	private String our_refno;

	@Column(name="pdf_attachment")
	private String pdf_attachment;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="process_date")
	private Date process_date;

    @Lob()
	@Column(name="process_message")
	private String process_message;

	@Column(name="process_status")
	private String process_status;

	@Column(name="ref_doc_type_id")
	private String ref_doc_type_id;

	@Column(name="sender_refno")
	private String sender_refno;

	@Column(name="ship_instructions")
	private String ship_instructions;

	@Column(name="transaction_type")
	private String transaction_type;

	@Column(name="vendor_refno")
	private String vendor_refno;

	private int version;

	private float weight;
	
	
	@Column(name="xmldoc")
	private String xmldoc;

	@JoinColumn(name="to_company_id", referencedColumnName="company_id")
	private Company company;


	@JoinColumn(name="fr_warehouse_id", referencedColumnName="warehouse_id")
	private Warehouse fr_warehouse;


	@JoinColumn(name="to_warehouse_id", referencedColumnName="warehouse_id")
	private Warehouse to_warehouse;


	@JoinColumn(name="carrier_id", referencedColumnName="carrier_id")
	private Carrier carrier;

	//bi-directional many-to-one association to InventoryLineItem

	@OneToMany(mappedBy="inventoryHeader", cascade={CascadeType.ALL})
	private List<InventoryLineItem> inventoryLineItems;

    public InventoryTransaction() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}


	
	public String getCarrier_refno() {
		return carrier_refno;
	}

	public void setCarrier_refno(String carrier_refno) {
		this.carrier_refno = carrier_refno;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		if(created_by!=null)
			this.created_by = created_by.toUpperCase();
			//this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getIpad_id() {
		return ipad_id;
	}

	public void setIpad_id(String ipad_id) {
		this.ipad_id = ipad_id;
	}

	public int getNo_of_packages() {
		return no_of_packages;
	}

	public void setNo_of_packages(int no_of_packages) {
		this.no_of_packages = no_of_packages;
	}

	public String getOur_refno() {
		return our_refno;
	}

	public void setOur_refno(String our_refno) {
		this.our_refno = our_refno;
	}

	public String getPdf_attachment() {
		return pdf_attachment;
	}

	public void setPdf_attachment(String pdf_attachment) {
		this.pdf_attachment = pdf_attachment;
	}

	public Date getProcess_date() {
		return process_date;
	}
	
	public String getSProcess_date()
	{
		String s = "";
		if(process_date!=null)
		{
			s = StringHelper.formatDate(process_date, SharedConstants.DATEFORMAT);
		}
		return s;
	}
	
	public long getLapsedSeconds()
	{
		long lapsedSeconds = 0;
		if(process_date!=null)
		{
			lapsedSeconds = (Calendar.getInstance().getTime().getTime()-process_date.getTime())/SharedConstants.DATE_SECOND;
		}
		
		return lapsedSeconds;		
	}

	public void setProcess_date(Date process_date) {
		this.process_date = process_date;
	}

	public String getProcess_message() {
		return process_message;
	}

	public void setProcess_message(String process_message) {
		this.process_message = process_message;
	}

	public String getProcess_status() {
		return process_status;
	}

	public void setProcess_status(String process_status) {
		this.process_status = process_status;
	}

	public String getRef_doc_type_id() {
		return ref_doc_type_id;
	}

	public void setRef_doc_type_id(String ref_doc_type_id) {
		this.ref_doc_type_id = ref_doc_type_id;
	}

	public String getSender_refno() {
		return sender_refno;
	}

	public void setSender_refno(String sender_refno) {
		this.sender_refno = sender_refno;
	}

	public String getShip_instructions() {
		return ship_instructions;
	}

	public void setShip_instructions(String ship_instructions) {
		this.ship_instructions = ship_instructions;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getVendor_refno() {
		return vendor_refno;
	}

	public void setVendor_refno(String vendor_refno) {
		this.vendor_refno = vendor_refno;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Warehouse getFr_warehouse() {
		return fr_warehouse;
	}

	public void setFr_warehouse(Warehouse fr_warehouse) {
		this.fr_warehouse = fr_warehouse;
	}

	public Warehouse getTo_warehouse() {
		return to_warehouse;
	}

	public void setTo_warehouse(Warehouse to_warehouse) {
		this.to_warehouse = to_warehouse;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public List<InventoryLineItem> getInventoryLineItems() {
		if(inventoryLineItems==null)
			inventoryLineItems = new ArrayList<InventoryLineItem>();
		return this.inventoryLineItems;
	}

	public void setInventoryLineItems(List<InventoryLineItem> inventoryLineItems) {
		this.inventoryLineItems = inventoryLineItems;
	}
	
	

	public String getXmldoc() {
		return xmldoc;
	}

	public void setXmldoc(String xmldoc) {
		this.xmldoc = xmldoc;
	}

	@Override
	public Map<String, Object> getMappedValues() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put(SharedConstants.Attribute_Inventory_Carrier, carrier!=null?carrier.getCarrier_id():"");
		
		maps.put(SharedConstants.Attribute_Inventory_Carrier_Refno, carrier_refno!=null?carrier_refno:"");
		
		maps.put(SharedConstants.Attribute_Inventory_Sender_Refno, sender_refno!=null?sender_refno:"");

		maps.put(SharedConstants.Attribute_Inventory_FR_Warehouse_ID, fr_warehouse!=null?fr_warehouse.getWarehouse_id():"");

		maps.put(SharedConstants.Attribute_Inventory_No_Of_Package, no_of_packages>=0?new Integer(no_of_packages):new Integer(0));


		maps.put(SharedConstants.Attribute_Inventory_Weight, weight>0?new Float(weight):new Float(0));

		
		maps.put(SharedConstants.Attribute_Inventory_Ship_Instructions, ship_instructions!=null?ship_instructions:"");
		
		
		maps.put(SharedConstants.Attribute_Inventory_Ref_Doc_Type_Id, ref_doc_type_id!=null?ref_doc_type_id:"");
		
		
		maps.put(SharedConstants.Attribute_Inventory_To_Warehouse_ID, to_warehouse!=null?to_warehouse.getWarehouse_id():"");
		
		maps.put(SharedConstants.Attribute_Inventory_To_Company_ID, company!=null?company.getCompany_id():"");
		maps.put(SharedConstants.Attribute_Inventory_Fr_Company_ID, company!=null?company.getCompany_id():"");
		
		maps.put(SharedConstants.Attribute_Inventory_Shipped_By, created_by!=null?created_by:"");
		maps.put(SharedConstants.Attribute_Inventory_Received_By, created_by!=null?created_by:"");
		
		maps.put(SharedConstants.Attribute_Inventory_PDF_Attachment, pdf_attachment!=null?pdf_attachment:"");


		return maps;
	}

	@Override
	public List<? extends TemplateBase> getLineItems() {
		return getInventoryLineItems();
	}
	
	@Override
	public TransactionTypeEnum getTransactionType() {
		TransactionTypeEnum type = TransactionTypeEnum.NONE;
		try{
			type = TransactionTypeEnum.parse(this.getTransaction_type());
		}catch(Exception xe)
		{
			xe.printStackTrace();
		}
		return type;
	}
	  
  
  	//after update, you may not be able to edit the same entity proxy by call edit(entityproxy). if 
	//edit is required, return object again to get a new version of proxy entity.
  	public void update()
	{
		EntityManager em = entityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(this);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	//after persist, you may not be able to edit the same entity proxy by call edit(entityproxy). if 
	//edit is required, return object again to get a new version of proxy entity.
  public void persist() {
    EntityManager em = entityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      em.persist(this);
      tx.commit();
    }catch(Throwable t)
    {
    	t.printStackTrace();
    	tx.rollback();
    }
    finally {
      em.close();
    }
  }

  public void remove() {
    EntityManager em = entityManager();
    EntityTransaction tx = em.getTransaction();
    try {
      tx.begin();
      InventoryTransaction attached = em.find(InventoryTransaction.class, this.id);
      em.remove(attached);
      tx.commit();
    }catch(Throwable t)
    {
    	t.printStackTrace();
    	tx.rollback();
    } 
    finally {
      em.close();
    }
  }
  
  public String toString()
  {
	  return String.format(SharedConstants.FORMAT_PROCESSINGMESSAGE,"inventory", getTransaction_type(),getIpad_id(),getSProcess_date(),getProcess_status(),getProcess_message());
  }
  
  
  public static final EntityManager entityManager() {
    return EMFCSMOBILE.get().createEntityManager();
  }
  
    @SuppressWarnings("unchecked")
  public static List<InventoryTransaction> findAllInventoryTransaction() {
    EntityManager em = entityManager();
    try {
      List<InventoryTransaction> list = em.createQuery("select o from InventoryTransaction o").getResultList();
      // force to get all the InventoryHeader
      list.size();
      return list;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;
  }

  public static InventoryTransaction findInventoryTransaction(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      InventoryTransaction entity = em.find(InventoryTransaction.class, id);
      if (entity!=null)
    	  entity.getLineItems().size();
      return entity;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;
  }
  
  public int getLineItemNumber(int index)
  {
	  int lineitemnumber = -1;
	  if(this.inventoryLineItems!=null&&index<this.inventoryLineItems.size())
	  {
		  lineitemnumber = this.inventoryLineItems.get(index).getLineitemnumber();
	  }
	  return lineitemnumber;
  }

  @SuppressWarnings("unchecked")
  public static List<InventoryTransaction> findInventoryTransactionEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<InventoryTransaction> resultList = em.createQuery("select o from InventoryTransaction o").setFirstResult(
          firstResult).setMaxResults(maxResults).getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;   
  }

  @SuppressWarnings("unchecked")
  public static List<InventoryTransaction> findInventoryTransactionEntriesByCreatedBy(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from InventoryTransaction o WHERE o.created_by =:CreatedBy");
      query.setParameter("CreatedBy", value);
      List<InventoryTransaction> resultList = query.getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;    
  }
  
  @SuppressWarnings("unchecked")
  public static List<InventoryTransaction> findInventoryTransactionEntriesByCreatedBy(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from InventoryTransaction o WHERE o.created_by =:CreatedBy");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("CreatedBy", value);
      List<InventoryTransaction> resultList = query.getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;    
  }
  
  public static void materialize(List<InventoryTransaction> list)
  {
	  if (list!=null&&list.size()>0)
	  {
		  for(InventoryTransaction item: list)
		  {
			  item.getLineItems().size();
		  }
	  }
  }
  
    public static InventoryTransaction findInventoryTransactionByIPadID(String value)
  {
	    EntityManager em = entityManager();
	    InventoryTransaction result = null;
	    try {
	      Query query = em.createQuery("select o from InventoryTransaction o WHERE o.ipad_id =:IPADID");
	      query.setParameter("IPADID", value);
	      result = (InventoryTransaction)query.getSingleResult();
	    
	      if (result!=null)
	      	  result.getLineItems().size();
	      
	    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}
	    finally {
	      em.close();
	    }
	    return result;
  }

	@Override
	public void afterUnmarshal(Unmarshaller u, Object parent) {
		//leave it empaty intentionally
	}

	@Override
	public String getTransactionID() {
		
		return this.getIpad_id();
	}

	@Override
	public Date getProcessDate() {
		return this.process_date;
	}

	@Override
	public String getProcessMessage() {
		return this.process_message;
	}

	@Override
	public String getProcessStatus() {
		return this.process_status;
	}


  
	
}