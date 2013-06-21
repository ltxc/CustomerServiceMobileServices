package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;
import com.ltxc.google.csms.shared.SharedConstants;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CycleCount database table.
 * 
 */
@XmlRootElement
@Table(name="CycleCount")
@Entity
@Cache(isolation=CacheIsolationType.ISOLATED)
public class CycleCount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	@Column(name="bin_code_id")
	private String binCodeId;

	@Column(name="bpart_id")
	private String bpartId;

	@Column(name="cycle_type")
	private String cycleType;

	@Column(name="originalid")
	private int originalid=0;
	
    @Temporal( TemporalType.TIMESTAMP)
	private Date lastupdated;

	private float qty;

	@Column(name="serial_no")
	private String serialNo;

	@Column(name="source")
	private String source = "IPAD";

	private byte version;

	@Column(name="warehouse_id")
	private String warehouseId;

	@Column(name="who")
	private String who;
	
	@Column(name="comment")
	private String comment;

    public CycleCount() {
    }

    //for bin count
    public CycleCount(String warehouse_id, String bin_code_id, String who, CycleCountCount count, int originalid)
    {
    	this.cycleType = SharedConstants.CYCLE_TYPE_BIN;
    	this.warehouseId = warehouse_id;
    	this.binCodeId = bin_code_id;
    	this.who = who;
    	this.bpartId = count.getTarget();
    	this.qty = count.getQty();
    	this.comment = count.getComment();
    	this.originalid = originalid;
    }
    
    //for part count
    public CycleCount(String warehouse_id, String bin_code_id, String bpart_id,String who,  CycleCountCount count, int originalid)
    {
    	this.cycleType = SharedConstants.CYCLE_TYPE_PART;
    	this.warehouseId = warehouse_id;
    	this.binCodeId = bin_code_id;
    	this.who = who;
    	this.bpartId = bpart_id;
    	this.serialNo = count.getTarget();
    	if(this.serialNo!=null&&!this.serialNo.trim().isEmpty())
    		this.qty = 1;
    	else
    		this.qty = count.getQty();
    	this.comment = count.getComment();
    	this.originalid = originalid;
    }
    
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBinCodeId() {
		return this.binCodeId;
	}

	public void setBinCodeId(String binCodeId) {
		this.binCodeId = binCodeId;
	}

	public String getBpartId() {
		return this.bpartId;
	}

	public void setBpartId(String bpartId) {
		this.bpartId = bpartId;
	}

	public String getCycleType() {
		return this.cycleType;
	}

	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public float getQty() {
		return this.qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public byte getVersion() {
		return this.version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWho() {
		return this.who;
	}

	public void setWho(String who) {
		this.who = who;
	}
	
	
  
  //default constructor is required. Don't delete it.
  
  


	public String getComment() {
		return comment;
	}


	public int getOriginalid() {
		return originalid;
	}

	public void setOriginalid(int originalid) {
		this.originalid = originalid;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
      CycleCount attached = em.find(CycleCount.class, this.id);
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
  public static List<CycleCount> findAllCycleCounts() {
    EntityManager em = entityManager();
    try {
      List<CycleCount> list = em.createQuery("select o from CycleCount o").getResultList();
      // force to get all the CycleCount
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

  public static CycleCount findCycleCount(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      CycleCount entity = em.find(CycleCount.class, id);
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

  @SuppressWarnings("unchecked")
  public static List<CycleCount> findCycleCountEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<CycleCount> resultList = em.createQuery("select o from CycleCount o").setFirstResult(
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
  public static List<CycleCount> findCycleCountEntriesByWarehouseID(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from CycleCount o WHERE o.warehouseId =:warehouse_id");
      query.setParameter("warehouse_id", value);
      List<CycleCount> resultList = query.getResultList();
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
  public static List<CycleCount> findCycleCountEntriesBywarehouse_id(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from CycleCount o WHERE o.warehouseId =:warehouse_id");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("warehouse_id", value);
      List<CycleCount> resultList = query.getResultList();
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

  public static boolean saveCycleCountEntry(CycleCount cycleCount, boolean isDeleteBeforeInsert)
  {
	  boolean isSaved = false;
	  if(cycleCount==null||cycleCount.getCycleType()==null)
		  return isSaved;
	  //first find the cyclecount entity
	  CycleCount found = null;
	  try{
		  if(isDeleteBeforeInsert)
		  {
		  if(SharedConstants.CYCLE_TYPE_BIN.equalsIgnoreCase(cycleCount.getCycleType()))
		  {
			  found = CycleCount.findCycleBinCountByKey(cycleCount.getWarehouseId(), cycleCount.getSource(), cycleCount.getBinCodeId(), cycleCount.getBpartId());
		  }
		  else if(SharedConstants.CYCLE_TYPE_PART.equalsIgnoreCase(cycleCount.getCycleType()))
		  {
			  found = CycleCount.findCyclePartCountByKey(cycleCount.getWarehouseId(), cycleCount.getSource(), cycleCount.getBpartId(), cycleCount.getSerialNo());
		  }
		  else
			  return isSaved;
		  //if found, delete it.
		  if(found!=null)
		  {
			  found.remove();
		  }
		  }
		  //now persist cycleCount
		  cycleCount.persist();
		  isSaved = true;
	  }catch(Exception xe)
	  {
		  xe.printStackTrace();
	  }
	  return isSaved;	  
  }
  
    public static CycleCount findCyclePartCountByKey(String warehouse_id, String source, String bpart_id,String serial_no )
  {
	    EntityManager em = entityManager();
	    CycleCount result = null;
	    String queryString = null;
	    if(serial_no!=null)
	    	queryString = "select o from CycleCount o WHERE o.cycleType=:cycleType and o.warehouseId =:warehouse_id and o.source=:source and o.bpartId=:bpart_id and o.serialNo=:serialNo order by o.lastupdated desc";
	    else
	    	queryString = "select o from CycleCount o WHERE o.cycleType=:cycleType and o.warehouseId =:warehouse_id and o.source=:source and o.bpartId=:bpart_id order by o.lastupdated desc";
	    try {
	      Query query = em.createQuery(queryString);
	      query.setParameter("cycleType", SharedConstants.CYCLE_TYPE_PART);
	      query.setParameter("warehouse_id", warehouse_id);
	      query.setParameter("source", SharedConstants.CYCLE_TYPE_SOURCE);
	      query.setParameter("bpartId", bpart_id);
	      if(serial_no!=null)
		      query.setParameter("serialNo", serial_no);
	      result = (CycleCount)query.getSingleResult();
	      
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
    
    public static CycleCount findCycleBinCountByKey(String warehouse_id, String source, String bin_code_id, String bpart_id)
  {
	    EntityManager em = entityManager();
	    CycleCount result = null;
	    try {
	      Query query = em.createQuery("select o from CycleCount o WHERE o.cycleType=:cycleType and o.warehouseId =:warehouse_id and o.source=:source and o.bpartId=:bpart_id and o.binCodeId=:binCodeId order by o.lastupdated desc");
	      query.setParameter("cycleType", SharedConstants.CYCLE_TYPE_BIN);
	      query.setParameter("warehouse_id", warehouse_id);
	      query.setParameter("source", SharedConstants.CYCLE_TYPE_SOURCE);
	      query.setParameter("bpart_id", bpart_id);
	      query.setParameter("binCodeId", bin_code_id);
	      result = (CycleCount)query.getSingleResult();
	      
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
    
    public static boolean clearCycleBinCount(String warehouse_id, String bin_code_id)
    {
    	boolean isCleared = false;

  	  EntityManager em = entityManager();
  	  EntityTransaction tx = null;
  	    try {
  	  	  tx = em.getTransaction();
  		  tx.begin();
  	      Query query = em.createNativeQuery("delete from CSMobile.CycleCount where source='"+SharedConstants.CYCLE_TYPE_SOURCE+"' and cycle_type = 'BIN' and warehouse_id = ? and bin_code_id = ?");//
  	      query.setParameter(1,warehouse_id);
  	      query.setParameter(2,bin_code_id);
  	      query.executeUpdate();
  		  tx.commit();
  	      isCleared = true;
  	    }catch (NoResultException ne) {
  				ne.printStackTrace();

  			} catch (Exception e) {
  				e.printStackTrace();
  				if(tx!=null)
  				tx.rollback();
  			} 
  	    finally {
  	      em.close();
  	    } 	
    	
    	return isCleared;
    	
    }
    
    public static boolean clearCyclePartCount(String warehouse_id, String bin_code_id, String bpart_id)
    {
    	boolean isCleared = false;

  	  EntityManager em = entityManager();
  	  EntityTransaction tx = null;
  	    try {
  	  	  tx = em.getTransaction();
  		  tx.begin();
  	      Query query = em.createNativeQuery("delete from CSMobile.CycleCount where source='"+SharedConstants.CYCLE_TYPE_SOURCE+"' and cycle_type = 'PART' and warehouse_id = ? and bin_code_id = ? and bpart_id = ?");//
  	      query.setParameter(1,warehouse_id);
  	      query.setParameter(2,bin_code_id);
  	      query.setParameter(3,bpart_id);
  	      query.executeUpdate();
  		  tx.commit();
  	      isCleared = true;
  	    }catch (NoResultException ne) {
  				ne.printStackTrace();

  			} catch (Exception e) {
  				e.printStackTrace();
  				if(tx!=null)
  				tx.rollback();
  			} 
  	    finally {
  	      em.close();
  	    } 	
    	
    	return isCleared;
    }
}