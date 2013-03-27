package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CycleCountMaster database table.
 * 
 */
@XmlRootElement
@Table(name="CycleCountMaster")
@Entity
public class CycleCountMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	@XmlTransient
	@Column(name="abc_id")
	private String abcId;

	private int assigned;

	@Column(name="bin_code_id")
	private String binCodeId;

	@Column(name="bpart_id")
	private String bpartId;

	private int counted;

	@Column(name="cycle_type")
	private String cycleType;
	
    @Temporal( TemporalType.TIMESTAMP)
	private Date assigneddate;

    @XmlTransient
    @Temporal( TemporalType.TIMESTAMP)
	private Date lastupdated;
    
	@Column(name="qty")
	private int qty;

    @XmlTransient
	private byte version;

	@Column(name="warehouse_id")
	private String warehouseId;
	
	

    public CycleCountMaster() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlTransient
	public String getAbcId() {
		return this.abcId;
	}

	public void setAbcId(String abcId) {
		this.abcId = abcId;
	}

	@XmlTransient
	public int getAssigned() {
		return this.assigned;
	}

	public void setAssigned(int assigned) {
		this.assigned = assigned;
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

	@XmlTransient
	public int getCounted() {
		return this.counted;
	}

	public void setCounted(int counted) {
		this.counted = counted;
	}

	public String getCycleType() {
		return this.cycleType;
	}

	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}

	
	
	public Date getAssigneddate() {
		return assigneddate;
	}

	public void setAssigneddate(Date assigneddate) {
		this.assigneddate = assigneddate;
	}

	@XmlTransient
	public Date getLastupdated() {
		return this.lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	
	
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@XmlTransient
	public byte getVersion() {
		return this.version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	@XmlTransient
	public String getWarehouseId() {
		return this.warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}
  
  //default constructor is required. Don't delete it.
  

  
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
      CycleCountMaster attached = em.find(CycleCountMaster.class, this.id);
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
  public static List<CycleCountMaster> findAllCycleCountMasters() {
    EntityManager em = entityManager();
    try {
      List<CycleCountMaster> list = em.createQuery("select o from CycleCountMaster o").getResultList();
      // force to get all the CycleCountMaster
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

  public static CycleCountMaster findCycleCountMaster(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      CycleCountMaster entity = em.find(CycleCountMaster.class, id);
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
  public static List<CycleCountMaster> findCycleCountMasterEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<CycleCountMaster> resultList = em.createQuery("select o from CycleCountMaster o").setFirstResult(
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
  public static List<CycleCountMaster> findCycleCountMasterEntriesBywarehouseId(
      String warehouseID, int assigned, int counted) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from CycleCountMaster o WHERE o.warehouseId =:warehouse_id and o.assigned =:assigned and o.counted = :counted");//
      query.setParameter("warehouse_id", warehouseID);
      query.setParameter("assigned", assigned);
      query.setParameter("counted", counted);
      List<CycleCountMaster> resultList = query.getResultList();
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
  
  
  public static boolean updateCycleCountMasterBinCounted(String warehouse_id, String bin_code_id)
  {
	  boolean isUpdated = false;
	  EntityManager em = entityManager();
	  EntityTransaction tx = null;
	    try {
	  	  tx = em.getTransaction();
		  tx.begin();
	      Query query = em.createNativeQuery("update CSMobile.CycleCountMaster set counted = 1 where cycle_type = 'BIN' and warehouse_id = ? and bin_code_id = ?");//
	      query.setParameter(1,warehouse_id);
	      query.setParameter(2,bin_code_id);
	      query.executeUpdate();
		  tx.commit();
	      isUpdated = true;
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
	  return isUpdated;	  
  }
  
  
  public static boolean updateCycleCountMasterPartCounted(String warehouse_id, String bin_code_id, String bpart_id )
  {
	  boolean isUpdated = false;
	  EntityManager em = entityManager();
	  EntityTransaction tx = null;
	    try {
		  	  tx = em.getTransaction();
			  tx.begin();
	      Query query = em.createNativeQuery("update CSMobile.CycleCountMaster set counted = 1 where cycle_type = 'PART' and warehouse_id = ? and bin_code_id = ? and bpart_id = ?");//
	      query.setParameter(1,warehouse_id);
	      query.setParameter(2,bin_code_id);
	      query.setParameter(3, bpart_id);
	      int i = query.executeUpdate();
	      tx.commit();
	      isUpdated = true;
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
	  return isUpdated;	
	  
  }
}