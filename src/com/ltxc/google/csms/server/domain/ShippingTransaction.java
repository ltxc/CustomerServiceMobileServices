package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.ltxc.google.csms.server.service.InventoryShippingLoader;
import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;
import com.ltxc.google.csms.shared.TransactionTypeEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@Entity
@Table(name = "ShippingHeader")
@XmlRootElement
public class ShippingTransaction implements Serializable, TransactionBase {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(ShippingTransaction.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	@Column(name="carrier_id")
	private String carrier_id;

	@Column(name="carrier_refno")
	private String carrier_refno;

	@Column(name="created_by")
	private String created_by;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date created_date;

	@Column(name="fr_warehouse_id")
	private String fr_warehouse_id;

	@Column(name="orig_doc_id")
	private String orig_doc_id;

	@Column(name="doc_type_id")
	private String doc_type_id;
	
	@Column(name="printer")
	private String printer;
	
	@Column(name="isprinting")
	private String isprinting;
	
	private int ifield1=0;

	@Column(name="ipad_id")
	private String ipad_id;

	@Column(name="no_of_packages")
	private int no_of_packages=0;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="process_date")
	private Date process_date;

    @Lob()
	@Column(name="process_message")
	private String process_message;



	@Column(name="process_status")
	private String process_status;

	private String sfield1;
	
	@Column(name="shipped_by")
	private String shipped_by;

	@Column(name="ship_instructions")
	private String ship_instructions;

	@Column(name="to_company_id")
	private String to_company_id;

	@Column(name="to_warehouse_id")
	private String to_warehouse_id;

	@Column(name="transaction_type")
	private String transaction_type;
	
	@Column(name="is_vendor")
	private String is_vendor;


	private int version;

	private float weight;

    @Lob()
	private String xmldoc;
    
    @Lob()
	private String xmldoc1;
    
    @Transient
    //0 - check process status, 1 - process
    private int actioncode = 1; 

	//bi-directional many-to-one association to ShippingLineItem
	@OneToMany(mappedBy="shippingHeader", cascade={CascadeType.ALL})
	private List<ShippingLineItem> shippingLineItems;

    public ShippingTransaction() {
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



	public String getIpad_id() {
		return ipad_id;
	}

	public void setIpad_id(String ipad_id) {
		this.ipad_id = ipad_id;
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

	public String getSfield1() {
		return this.sfield1;
	}

	public void setSfield1(String sfield1) {
		this.sfield1 = sfield1;
	}

	public String getCarrier_id() {
		return carrier_id;
	}

	public void setCarrier_id(String carrier_id) {
		this.carrier_id = carrier_id;
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
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getFr_warehouse_id() {
		return fr_warehouse_id;
	}

	public void setFr_warehouse_id(String fr_warehouse_id) {
		this.fr_warehouse_id = fr_warehouse_id;
	}

	public int getNo_of_packages() {
		return no_of_packages;
	}

	public void setNo_of_packages(int no_of_packages) {
		this.no_of_packages = no_of_packages;
	}

	public Date getProcess_date() {
		return process_date;
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
	
	
	

	public int getActioncode() {
		return actioncode;
	}

	public void setActioncode(int actioncode) {
		this.actioncode = actioncode;
	}

	public String getShipped_by() {
		return shipped_by;
	}

	public void setShipped_by(String shipped_by) {
		this.shipped_by = shipped_by;
	}

	public String getShip_instructions() {
		return ship_instructions;
	}

	public void setShip_instructions(String ship_instructions) {
		this.ship_instructions = ship_instructions;
	}

	public String getTo_company_id() {
		return to_company_id;
	}

	public void setTo_company_id(String to_company_id) {
		this.to_company_id = to_company_id;
	}

	public String getTo_warehouse_id() {
		return to_warehouse_id;
	}

	public void setTo_warehouse_id(String to_warehouse_id) {
		this.to_warehouse_id = to_warehouse_id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public float getWeight() {
		return this.weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	
	
	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	
	
	public String getIsprinting() {
		return isprinting;
	}

	public void setIsprinting(String isprinting) {
		this.isprinting = isprinting;
	}

	public String getXmldoc() {
		return this.xmldoc;
	}

	public void setXmldoc(String xmldoc) {
		this.xmldoc = xmldoc;
	}

	
	
	public String getXmldoc1() {
		return xmldoc1;
	}

	public void setXmldoc1(String xmldoc1) {
		this.xmldoc1 = xmldoc1;
	}

	public List<ShippingLineItem> getShippingLineItems() {
		return this.shippingLineItems;
	}

	public void setShippingLineItems(List<ShippingLineItem> shippingLineItems) {
		this.shippingLineItems = shippingLineItems;
	}
	
	
	  

  	public String getIs_vendor() {
		return is_vendor;
	}

	public void setIs_vendor(String is_vendor) {
		this.is_vendor = is_vendor;
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
      ShippingTransaction attached = em.find(ShippingTransaction.class, this.id);
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
  
  
  
  public static final EntityManager entityManager() {
    return EMFCSMOBILE.get().createEntityManager();
  }
  
    @SuppressWarnings("unchecked")
  public static List<ShippingTransaction> findAllShippingTransactions() {
    EntityManager em = entityManager();
    try {
      List<ShippingTransaction> list = em.createQuery("select o from ShippingTransaction o").getResultList();
      // force to get all the ShippingTransaction
      list.size();
      return list;
    }catch (NoResultException ne) {
			//ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;
  }

  public static ShippingTransaction findShippingTransaction(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      ShippingTransaction entity = em.find(ShippingTransaction.class, id);
      return entity;
    }catch (NoResultException ne) {
			//ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} 
    finally {
      em.close();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static List<ShippingTransaction> findShippingTransactionEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<ShippingTransaction> resultList = em.createQuery("select o from ShippingTransaction o").setFirstResult(
          firstResult).setMaxResults(maxResults).getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			//ne.printStackTrace();

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
	  if(this.shippingLineItems!=null&&index<this.shippingLineItems.size())
	  {
		  lineitemnumber = this.shippingLineItems.get(index).getLineitemnumber();
	  }
	  return lineitemnumber;
  }

	public long getLapsedMinutes()
	{
		long lapsedMinutes = 0;
		if(process_date!=null)
		{
			Date current = Calendar.getInstance().getTime();
			lapsedMinutes = (current.getTime()-process_date.getTime())/SharedConstants.DATE_SECOND;
		}
		
		return lapsedMinutes;		
	}
    public static ShippingTransaction findShippingTransactionByIPadID(String value)
  {
	    EntityManager em = entityManager();
	    ShippingTransaction result = null;
	    try {
	      Query query = em.createQuery("select o from ShippingTransaction o WHERE o.ipad_id =:ipad_id");
	      query.setParameter("ipad_id", value);
	      result = (ShippingTransaction)query.getSingleResult();

	      
	    }catch (NoResultException ne) {
			//ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}
	    finally {
	      em.close();
	    }
	    return result;
  }

	@Override
	public Map<String, Object> getMappedValues() {
		Map<String, Object> maps = new HashMap<String, Object>();
		
		maps.put(SharedConstants.Attribute_CreatedBy, created_by!=null?created_by:"");
		String createddt = StringHelper.formatDate(Calendar.getInstance().getTime(), SharedConstants.APIDATEFORMAT);
		maps.put(SharedConstants.Attribute_CreatedDate, createddt!=null?createddt:"");
		
		maps.put(SharedConstants.Attribute_Printer, printer!=null?printer:"");
		
		maps.put(SharedConstants.Attribute_IsPrinting, (isprinting!=null&&!isprinting.trim().isEmpty())?isprinting:"Y");
		
		maps.put(SharedConstants.Attribute_Shipping_Transaction_Type, transaction_type!=null?transaction_type:"");
		
		maps.put(SharedConstants.Attribute_Shipping_Carrier, carrier_id!=null?carrier_id:"");
		
		maps.put(SharedConstants.Attribute_Shipping_Carrier_Refno, carrier_refno!=null?carrier_refno:"");

		maps.put(SharedConstants.Attribute_Shipping_Fr_Warehouse_ID, fr_warehouse_id!=null?fr_warehouse_id:"");

		maps.put(SharedConstants.Attribute_Shipping_No_Of_Packages, no_of_packages>=0?new Integer(no_of_packages):new Integer(0));

		

		maps.put(SharedConstants.Attribute_Shipping_Weight, weight>0?new Float(weight):new Float(0));

		
		maps.put(SharedConstants.Attribute_Shipping_To_Warehouse_ID, to_warehouse_id!=null?to_warehouse_id:"");
		
		maps.put(SharedConstants.Attribute_Shipping_Ship_Instructions, ship_instructions!=null?ship_instructions:"");
		
		maps.put(SharedConstants.Attribute_Shipping_Shipped_By, shipped_by!=null?shipped_by:"");
		
		maps.put(SharedConstants.Attribute_Shipping_ORIG_DOC_ID, orig_doc_id!=null?orig_doc_id:"");
		
		maps.put(SharedConstants.Attribute_Shipping_DOC_TYPE_ID, doc_type_id!=null?doc_type_id:"");
		
		
		//prepare the following for the creation template
		if(to_company_id!=null&&!to_company_id.trim().isEmpty())
		{
			maps.put(SharedConstants.Attribute_Shipping_Target, SharedConstants.Attribute_Shipping_To_Company_ID);
			maps.put(SharedConstants.Attribute_Shipping_Target_Value, to_company_id);
		}
		else
		{
			maps.put(SharedConstants.Attribute_Shipping_Target, SharedConstants.Attribute_Shipping_To_Warehouse_ID);
			maps.put(SharedConstants.Attribute_Shipping_Target_Value, to_warehouse_id);
		}
		
		StringBuilder demand_id_list = new StringBuilder();
		StringBuilder doc_type_id_list = new StringBuilder();
		int i = 0;
		if (getShippingLineItems()!=null)
		{
			for(ShippingLineItem lineItem: getShippingLineItems())
			{
				if(i>0)
				{
					demand_id_list.append(",");
					doc_type_id_list.append(",");
				}
				if(i==0)
				{
					maps.put(SharedConstants.Attribute_Shipping_DEMAND_ID, lineItem.getDemand_id());
					maps.put(SharedConstants.Attribute_Shipping_BPart_ID, lineItem.getBpart_id());
				}
				demand_id_list.append(lineItem.getDemand_id());
				doc_type_id_list.append(lineItem.getDoc_type_id());
				i=i+1;
			}
			
			maps.put(SharedConstants.Attribute_Shipping_Demand_Id_List, demand_id_list.toString());
			maps.put(SharedConstants.Attribute_Shipping_Doc_Type_Id_List, demand_id_list.toString());
		}
		
		return maps;
	}

	@Override
	public List<? extends TemplateBase> getLineItems() {
		return getShippingLineItems();
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

	@Override
	public void afterUnmarshal(Unmarshaller u, Object parent) {
		// leave it empty intentionally
		
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

	@Override
	public int getProcessActionCode() {
		return this.actioncode;
	}

	@Override
	public Date getCreatedDate() {
		
		return this.created_date;
	}

	@Override
	public void setCreatedDate(Date date) {
		this.created_date = date;
	}

	@Override
	public void setProcessDate(Date date) {
		this.process_date = date;
	}	
	@Override
	public void setProcessActionCode(int actioncode) {
		this.actioncode = actioncode;
		
	}

	@Override
	public TransactionBase searchTransactionByIPADID(String ipadid) {
		return ShippingTransaction.findShippingTransactionByIPadID(ipadid);
	}
}