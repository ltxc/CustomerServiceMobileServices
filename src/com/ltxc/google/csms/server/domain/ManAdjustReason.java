package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;



@XmlRootElement
@Entity
@Table(name="ManAdjustReason")
public class ManAdjustReason implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String description;

	private String reasoncode;

	private byte version;

    public ManAdjustReason() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReasoncode() {
		return this.reasoncode;
	}

	public void setReasoncode(String reasoncode) {
		this.reasoncode = reasoncode;
	}
	@XmlTransient
	public byte getVersion() {
		return this.version;
	}

	public void setVersion(byte version) {
		this.version = version;
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
      ManAdjustReason attached = em.find(ManAdjustReason.class, this.id);
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
  public static List<ManAdjustReason> findAllManAdjustReasons() {
    EntityManager em = entityManager();
    try {
      List<ManAdjustReason> list = em.createQuery("select o from ManAdjustReason o").getResultList();
      // force to get all the ManAdjustReason
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

  public static ManAdjustReason findManAdjustReason(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      ManAdjustReason entity = em.find(ManAdjustReason.class, id);
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
  public static List<ManAdjustReason> findManAdjustReasonEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<ManAdjustReason> resultList = em.createQuery("select o from ManAdjustReason o order by o.reasoncode").setFirstResult(
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
  public static List<ManAdjustReason> findManAdjustReasonEntriesByreasoncode(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from ManAdjustReason o WHERE o.reasoncode =:reasoncode");
      query.setParameter("reasoncode", value);
      List<ManAdjustReason> resultList = query.getResultList();
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
  public static List<ManAdjustReason> findManAdjustReasonEntriesByreasoncode(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from ManAdjustReason o WHERE o.reasoncode =:reasoncode");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("reasoncode", value);
      List<ManAdjustReason> resultList = query.getResultList();
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
  
    public static ManAdjustReason findManAdjustReasonByreasoncode(String value)
  {
	    EntityManager em = entityManager();
	    ManAdjustReason result = null;
	    try {
	      Query query = em.createQuery("select o from ManAdjustReason o WHERE o.reasoncode =:reasoncode");
	      query.setParameter("reasoncode", value);
	      result = (ManAdjustReason)query.getSingleResult();
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