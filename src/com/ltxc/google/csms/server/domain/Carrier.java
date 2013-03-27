package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


/**
 * The persistent class for the Carrier database table.
 * 
 */
@Entity
@XmlRootElement
@Table(name="Carrier")
public class Carrier implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(Carrier.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;
	
	private String carrier_id;
	
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="last_change_date")
	private Date last_change_date;
  
	private int version;


    public Carrier() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlTransient
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	
	
	public String getCarrier_id() {
		return carrier_id;
	}

	public void setCarrier_id(String carrier_id) {
		this.carrier_id = carrier_id;
	}

	public Date getLast_change_date() {
		return last_change_date;
	}

	public void setLast_change_date(Date last_change_date) {
		this.last_change_date = last_change_date;
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
      Carrier attached = em.find(Carrier.class, this.id);
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
  public static List<Carrier> findAllCarriers() {
    EntityManager em = entityManager();
    try {
      List<Carrier> list = em.createQuery("select o from Carrier o order by o.carrier_id").getResultList();
      // force to get all the Carrier
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

  public static Carrier findCarrier(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      Carrier entity = em.find(Carrier.class, id);
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
  public static List<Carrier> findCarrierEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<Carrier> resultList = em.createQuery("select o from Carrier o order by o.carrier_id").setFirstResult(
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
  public static List<Carrier> findCarrierEntriesByCarrierID(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Carrier o WHERE o.CarrierID =:CarrierID");
      query.setParameter("CarrierID", value);
      List<Carrier> resultList = query.getResultList();
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
  public static List<Carrier> findCarrierEntriesByCarrierID(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Carrier o WHERE o.carrier_id =:CarrierID");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("CarrierID", value);
      List<Carrier> resultList = query.getResultList();
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
  
    public static Carrier findCarrierByCarrierID(String value)
  {
	    EntityManager em = entityManager();
	    Carrier result = null;
	    try {
	      Query query = em.createQuery("select o from Carrier o WHERE o.carrier_id =:CarrierID");
	      query.setParameter("CarrierID", value);
	      result = (Carrier)query.getSingleResult();
	      //for lazy fetch.
	      //if (result!=null)
	      //	  result.getUsagetemplates();
	      
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
  
	
} 