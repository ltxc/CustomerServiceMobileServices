package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;

import java.util.List;
import java.util.logging.Logger;


/**
 * The persistent class for the Warehouse database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name="Warehouse")
public class Warehouse implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(Warehouse.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	private String warehouse_id;
	
	private String descr;

	private int version;


    public Warehouse() {
    }
	@XmlTransient
	public String getId() {
		return this.id;
	}


	public void setId(String id) {
		this.id = id;
	}
	

	public String getWarehouse_id() {
		return warehouse_id;
	}

	public void setWarehouse_id(String warehouse_id) {
		this.warehouse_id = warehouse_id;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	@XmlTransient
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
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
      Warehouse attached = em.find(Warehouse.class, this.id);
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
  public static List<Warehouse> findAllWarehouses() {
    EntityManager em = entityManager();
    try {
      List<Warehouse> list = em.createQuery("select o from Warehouse o").getResultList();
      // force to get all the Warehouse
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

  public static Warehouse findWarehouse(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      Warehouse entity = em.find(Warehouse.class, id);
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
  public static List<Warehouse> findWarehouseEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<Warehouse> resultList = em.createQuery("select o from Warehouse o order by o.warehouse_id").setFirstResult(
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
  public static List<Warehouse> findWarehouseEntriesByWarehouseID(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Warehouse o WHERE o.warehouse_id =:WarehouseID");
      query.setParameter("WarehouseID", value);
      List<Warehouse> resultList = query.getResultList();
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
  public static List<Warehouse> findWarehouseEntriesByWarehouseID(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Warehouse o WHERE o.warehouse_id =:WarehouseID");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("WarehouseID", value);
      List<Warehouse> resultList = query.getResultList();
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
  
    public static Warehouse findWarehouseByWarehouseID(String value)
  {
	    EntityManager em = entityManager();
	    Warehouse result = null;
	    try {
	      Query query = em.createQuery("select o from Warehouse o WHERE o.warehouse_id =:WarehouseID");
	      query.setParameter("WarehouseID", value);
	      result = (Warehouse)query.getSingleResult();
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