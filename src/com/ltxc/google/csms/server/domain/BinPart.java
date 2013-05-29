package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.config.CacheIsolationType;

import com.ltxc.google.csms.server.service.restful.DateParam;
import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * The persistent class for the Bin database table.
 * 
 */
@Entity
@XmlRootElement
@Table(name="BinPart")
@Cache(isolation=CacheIsolationType.ISOLATED)
public class BinPart implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BinPart.class.getName());
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id;

	@Column(name = "bin_code_id")
	private String binCodeId;

	@Column(name = "bpart_id")
	private String bpartId;
	
	@Column(name = "warehouse_id")
	private String warehouseId;

	@Column(name = "inv_type_id")
	private String invTypeId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_rec_date")
	private Date lastRecDate;
	
	@Column(name="qty")
	private int qty;
	
	private int version;

	public BinPart() {
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

	public Date getLastRecDate() {
		return this.lastRecDate;
	}

	public void setLastRecDate(Date lastRecDate) {
		this.lastRecDate = lastRecDate;
	}
	@XmlTransient
	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getInvTypeId() {
		return invTypeId;
	}

	public void setInvTypeId(String invTypeId) {
		this.invTypeId = invTypeId;
	}
	
	
	
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@XmlTransient
	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	// after update, you may not be able to edit the same entity proxy by call
	// edit(entityproxy). if
	// edit is required, return object again to get a new version of proxy
	// entity.
	public void update() {
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

	// after persist, you may not be able to edit the same entity proxy by call
	// edit(entityproxy). if
	// edit is required, return object again to get a new version of proxy
	// entity.
	public void persist() {
		EntityManager em = entityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.persist(this);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void remove() {
		EntityManager em = entityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			BinPart attached = em.find(BinPart.class, this.id);
			em.remove(attached);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public static final EntityManager entityManager() {
		return EMFCSMOBILE.get().createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public static List<BinPart> findAllBins() {
		EntityManager em = entityManager();
		try {
			List<BinPart> list = em.createQuery("select o from BinPart o  order by o.bpartId")
					.getResultList();
			// force to get all the BinPart
			list.size();
			return list;
		} catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			em.close();
		}
		return null;
	}

	public static BinPart findBin(Long id) {
		if (id == null) {
			return null;
		}
		EntityManager em = entityManager();
		try {
			BinPart entity = em.find(BinPart.class, id);
			return entity;
		} catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			em.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<BinPart> findBinPartEntries(int firstResult, int maxResults) {
		EntityManager em = entityManager();
		try {
			List<BinPart> resultList = em.createQuery("select o from BinPart o order by o.bpartId")
					.setFirstResult(firstResult).setMaxResults(maxResults)
					.getResultList();
			// force it to materialize
			resultList.size();
			return resultList;
		} catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			em.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<BinPart> findBinPartEntriesByWarehouseID(String value) {
		EntityManager em = entityManager();
		try {
			Query query = em
					.createQuery("select o from BinPart o WHERE o.warehouseId =:warehouse_id  order by o.bpartId");
			query.setParameter("warehouse_id", value);
			List<BinPart> resultList = query.getResultList();
			// force it to materialize
			resultList.size();
			return resultList;
		} catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			em.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<BinPart> findBinPartEntriesByWarehouseId(String value,
			int firstResult, int maxResults, DateParam last_date) {
		boolean isDate = true;
		if(last_date==null||last_date.getDate()==null)
			isDate = false;
		EntityManager em = entityManager();
		Query query = null;
		String squery = null;
		try {
			if(isDate)
			{
				squery = "select o from BinPart o WHERE o.warehouseId =:warehouse_id and o.lastRecDate > :lastdate order by o.lastRecDate  order by o.bpartId";
			}
			else
			{
				squery = "select o from BinPart o WHERE o.warehouseId =:warehouse_id";
			}
			query = em.createQuery(squery);

			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setParameter("warehouse_id", value);
			if(isDate)
				query.setParameter("lastdate", last_date.getDate());
			List<BinPart> resultList = query.getResultList();
			// force it to materialize
			resultList.size();
			return resultList;
		} catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			em.close();
		}
		return null;
	}

	public static BinPart findBinPartByBinCodeIDAndBPartID(String bin_code_id,
			String bpart_id) {
		EntityManager em = entityManager();
		BinPart result = null;
		try {
			Query query = em
					.createQuery("select o from BinPart o WHERE o.binCodeId =:bin_code_id and o.bpartId=:bpart_id  order by o.bpartId");
			query.setParameter("bin_code_id", bin_code_id);
			query.setParameter("bpart_id", bpart_id);
			result = (BinPart) query.getSingleResult();

		} catch (NoResultException ne) {
			ne.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			em.close();
		}
		return result;
	}

}