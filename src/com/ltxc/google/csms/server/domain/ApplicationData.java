package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;
import com.ltxc.google.csms.server.servlet.utils.EMFCSMobileLandscape;

@Entity
@XmlRootElement
@Table(name="ApplicationData")
@Cache(isolation=CacheIsolationType.ISOLATED)
public class ApplicationData implements Serializable {
		private static final long serialVersionUID = 1L;
		private static Logger logger = Logger.getLogger(BinPart.class.getName());
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		private String id;

		@Column(name = "attachment")
		private String attachment;

		@Column(name = "attribute1")
		private String attribute1;

		@Column(name = "attribute10")
		private String attribute10;

		@Column(name = "attribute2")
		private String attribute2;

		@Column(name = "attribute3")
		private String attribute3;

		@Column(name = "attribute4")
		private String attribute4;

		@Column(name = "attribute5")
		private String attribute5;

		@Column(name = "attribute6")
		private String attribute6;

		@Column(name = "attribute7")
		private String attribute7;

		@Column(name = "attribute8")
		private String attribute8;

		@Column(name = "attribute9")
		private String attribute9;

		@Column(name = "category")
		private String category;

		@Column(name = "description")
		private String description;
		
		@Column(name = "landscape")
		private String landscape;

		@Column(name = "name")
		private String name;

		@Column(name = "subcategory")
		private String subcategory;

		@Column(name = "value")
		private String value;

		
		private int version;

	    public ApplicationData() {
	    }

	    public String getKey()
	    {
	    	StringBuilder sb = new StringBuilder();
	    	sb.append(this.category).append(".");
	    	if(this.subcategory!=null)
	    		sb.append(this.subcategory).append(".");
	    	sb.append(this.landscape).append(".");
	    	sb.append(this.name).append(".");
	    	sb.append(this.value).append(".");
	    	return sb.toString();
	    }
	    
	    public void setKey(String key)
	    {
	    	
	    }
	    
		public String getId() {
			return this.id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAttachment() {
			return this.attachment;
		}

		public void setAttachment(String attachment) {
			this.attachment = attachment;
		}

		public String getAttribute1() {
			return this.attribute1;
		}

		public void setAttribute1(String attribute1) {
			this.attribute1 = attribute1;
		}

		public String getAttribute10() {
			return this.attribute10;
		}

		public void setAttribute10(String attribute10) {
			this.attribute10 = attribute10;
		}

		public String getAttribute2() {
			return this.attribute2;
		}

		public void setAttribute2(String attribute2) {
			this.attribute2 = attribute2;
		}

		public String getAttribute3() {
			return this.attribute3;
		}

		public void setAttribute3(String attribute3) {
			this.attribute3 = attribute3;
		}

		public String getAttribute4() {
			return this.attribute4;
		}

		public void setAttribute4(String attribute4) {
			this.attribute4 = attribute4;
		}

		public String getAttribute5() {
			return this.attribute5;
		}

		public void setAttribute5(String attribute5) {
			this.attribute5 = attribute5;
		}

		public String getAttribute6() {
			return this.attribute6;
		}

		public void setAttribute6(String attribute6) {
			this.attribute6 = attribute6;
		}

		public String getAttribute7() {
			return this.attribute7;
		}

		public void setAttribute7(String attribute7) {
			this.attribute7 = attribute7;
		}

		public String getAttribute8() {
			return this.attribute8;
		}

		public void setAttribute8(String attribute8) {
			this.attribute8 = attribute8;
		}

		public String getAttribute9() {
			return this.attribute9;
		}

		public void setAttribute9(String attribute9) {
			this.attribute9 = attribute9;
		}

		public String getCategory() {
			return this.category;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSubcategory() {
			return this.subcategory;
		}

		public void setSubcategory(String subcategory) {
			this.subcategory = subcategory;
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
		@XmlTransient
		public int getVersion() {
			return this.version;
		}

		public void setVersion(int version) {
			this.version = version;
		}
		
		    
 

  	public String getLandscape() {
			return landscape;
		}

		public void setLandscape(String landscape) {
			this.landscape = landscape;
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
			logger.log(Level.SEVERE, t.getMessage());
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
    	logger.log(Level.SEVERE, t.getMessage());
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
      ApplicationData attached = em.find(ApplicationData.class, this.id);
      em.remove(attached);
      tx.commit();
    }catch(Throwable t)
    {
    	t.printStackTrace();
    	logger.log(Level.SEVERE, t.getMessage());
    	tx.rollback();
    } 
    finally {
      em.close();
    }
  }
  
  
  
  public static final EntityManager entityManager() {
    return EMFCSMobileLandscape.get().createEntityManager();
  }
  
    @SuppressWarnings("unchecked")
  public static List<ApplicationData> findAllApplicationDatas() {
    EntityManager em = entityManager();
    try {
      List<ApplicationData> list = em.createQuery("select o from ApplicationData o").getResultList();
      // force to get all the ApplicationData
      list.size();
      return list;
    }catch (NoResultException ne) {
			//ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());

		} 
    finally {
      em.close();
    }
    return null;
  }

  public static ApplicationData findApplicationData(Long id) {
    if (id == null) {
      return null;
    }
    EntityManager em = entityManager();
    try {
      ApplicationData entity = em.find(ApplicationData.class, id);
      return entity;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
		} 
    finally {
      em.close();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public static List<ApplicationData> findApplicationDataEntries(int firstResult,
      int maxResults) {
    EntityManager em = entityManager();
    try {
      List<ApplicationData> resultList = em.createQuery("select o from ApplicationData o").setFirstResult(
          firstResult).setMaxResults(maxResults).getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
		} 
    finally {
      em.close();
    }
    return null;   
  }

  @SuppressWarnings("unchecked")
  public static List<ApplicationData> findApplicationDataEntriesBycategory(
      String value) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from ApplicationData o WHERE o.category =:category");
      query.setParameter("category", value);
      List<ApplicationData> resultList = query.getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
		} 
    finally {
      em.close();
    }
    return null;    
  }
  
  @SuppressWarnings("unchecked")
  public static List<ApplicationData> findApplicationDataEntriesBycategory(
      String value, int firstResult, int maxResults) {
    EntityManager em = entityManager();
    try {
      Query query = em.createQuery("select o from ApplicationData o WHERE o.category =:category");
      query.setFirstResult(firstResult);
      query.setMaxResults(maxResults);
      query.setParameter("category", value);
      List<ApplicationData> resultList = query.getResultList();
      // force it to materialize
      resultList.size();
      return resultList;
    }catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
		} 
    finally {
      em.close();
    }
    return null;    
  }
  
 
  
		
}
