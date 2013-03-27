package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.*;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;



import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the Queries database table.
 * 
 */
@XmlRootElement
@Entity
@Table(name="Queries")
public class Queries implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(Queries.class.getName());
	@Id

	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private String id;

	private String descr;

	private String groupname;

	private String queryname;

	private int tag;

	private String url;

	private int version;

    public Queries() {
    }
	@XmlTransient
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getQueryname() {
		return this.queryname;
	}

	public void setQueryname(String queryname) {
		this.queryname = queryname;
	}

	public int getTag() {
		return this.tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@XmlTransient
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
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
      Queries attached = em.find(Queries.class, this.id);
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
  public static List<Queries> findAllQueriess() {
    EntityManager em = entityManager();
    try {
      List<Queries> list = em.createQuery("select o from Queries o").getResultList();
      // force to get all the Queries
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

  public static Queries findQueries(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      Queries entity = em.find(Queries.class, id);
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
  public static List<Queries> findQueriesEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<Queries> resultList = em.createQuery("select o from Queries o").setFirstResult(
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
  public static List<Queries> findQueriesEntriesByQueryName(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Queries o WHERE o.queryname =:QueryName");
      query.setParameter("QueryName", value);
      List<Queries> resultList = query.getResultList();
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
  public static List<Queries> findQueriesEntriesByQueryName(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from Queries o WHERE o.queryname =:QueryName");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("QueryName", value);
      List<Queries> resultList = query.getResultList();
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
  
    public static Queries findQueriesByQueryName(String value)
  {
	    EntityManager em = entityManager();
	    Queries result = null;
	    try {
	      Query query = em.createQuery("select o from Queries o WHERE o.queryname =:QueryName");
	      query.setParameter("QueryName", value);
	      result = (Queries)query.getSingleResult();
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