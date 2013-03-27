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
 * The persistent class for the Company database table.
 * 
 */
@XmlRootElement
@Table(name="Company")
@Entity
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(Company.class.getName());
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	private String company_id;	
	
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="last_change_date")
	private Date last_change_date;
    
    

	private byte version;


    public Company() {
    }

	public String getId() {
		return this.id;
	}


	public void setId(String id) {
		this.id = id;
	}
	@XmlTransient
	public byte getVersion() {
		return this.version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}
	
	

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public Date getLast_change_date() {
		return last_change_date;
	}

	public void setLast_change_date(Date last_change_date) {
		this.last_change_date = last_change_date;
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
      Company attached = em.find(Company.class, this.id);
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
  public static List<Company> findAllCompanys() {
    EntityManager em = entityManager();
    try {
      List<Company> list = em.createQuery("select o from Company o order by o.company_id").getResultList();
      // force to get all the Company
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

  public static Company findCompany(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      Company entity = em.find(Company.class, id);
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
  public static List<Company> findCompanyEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<Company> resultList = em.createQuery("select o from Company o order by o.company_id").setFirstResult(
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
  public static List<Company> findCompanyEntriesByCompanyID(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Company o WHERE o.company_id =:CompanyID");
      query.setParameter("CompanyID", value);
      List<Company> resultList = query.getResultList();
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
  

  
    public static Company findCompanyByCompanyID(String value)
  {
	    EntityManager em = entityManager();
	    Company result = null;
	    try {
	      Query query = em.createQuery("select o from Company o WHERE o.company_id =:CompanyID");
	      query.setParameter("CompanyID", value);
	      result = (Company)query.getSingleResult();
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