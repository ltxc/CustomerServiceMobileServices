package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.config.CacheIsolationType;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.ltxc.google.csms.server.servlet.utils.EMFAstea;


@Entity
@Table(name = "_CSMobile_ShippingListView")
@XmlRootElement(name = "shippingList")
//@Cache(
//		  type=CacheType.WEAK, // Cache nothing
//		  expiry=0,
//		  alwaysRefresh=true,
//		  isolation=CacheIsolationType.ISOLATED
//		)
@Cache(isolation=CacheIsolationType.ISOLATED)

public class ViewShippingList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	
	@Column(name="serverid")
	private String serverid;

	@Column(name="bpart_id")
	private String bpart_id;

	@Column(name="demand_id")
	private String demand_id;

	@XmlTransient
	@Column(name="descr")
	private String descr;

	@Column(name="doc_type_id")
	private String doc_type_id;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="due_dt")
	private Date due_dt;

	@Column(name="fr_bin_code_id")
	private String fr_bin_code_id;

	@Column(name="fr_warehouse_id")
	private String fr_warehouse_id;

	@XmlTransient
	@Column(name="is_allocated")
	private String is_allocated;

	@Column(name="item_id")
	private String item_id;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name="lastupdated")
	private Date lastupdated;

	@Column(name="ldmnd_stat_id")
	private String ldmnd_stat_id;

	@XmlTransient
	@Column(name="order_id")
	private String order_id;

	@Column(name="orig_doc_id")
	private String orig_doc_id;

	@XmlTransient
	@Column(name="original_qty")
	private int original_qty;

	@Column(name="priority")
	private String priority;

	@Column(name="qty")
	private int qty;

	@XmlTransient
	@Column(name="request_id")
	private String request_id;
	
	@Column(name="sfield1")
	private String sfield1;

	@Column(name="serial_no")
	private String serial_no;

	@Column(name="status")
	private byte status;

	@Column(name="to_company_id")
	private String to_company_id;

	@Column(name="to_warehouse_id")
	private String to_warehouse_id;
	
	@Column(name="pi_doc_id")
	private String pi_doc_id;
	
	@Column(name="is_vendor")
	private String is_vendor;

	@Column(name="version")
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerid() {
		return serverid;
	}

	public void setServerid(String serverid) {
		this.serverid = serverid;
	}

	public String getBpart_id() {
		return bpart_id;
	}

	public void setBpart_id(String bpart_id) {
		this.bpart_id = bpart_id;
	}

	public String getDemand_id() {
		return demand_id;
	}

	public void setDemand_id(String demand_id) {
		this.demand_id = demand_id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getDoc_type_id() {
		return doc_type_id;
	}

	public void setDoc_type_id(String doc_type_id) {
		this.doc_type_id = doc_type_id;
	}

	public Date getDue_dt() {
		return due_dt;
	}

	public void setDue_dt(Date due_dt) {
		this.due_dt = due_dt;
	}

	public String getFr_bin_code_id() {
		return fr_bin_code_id;
	}

	public void setFr_bin_code_id(String fr_bin_code_id) {
		this.fr_bin_code_id = fr_bin_code_id;
	}

	public String getFr_warehouse_id() {
		return fr_warehouse_id;
	}

	public void setFr_warehouse_id(String fr_warehouse_id) {
		this.fr_warehouse_id = fr_warehouse_id;
	}

	public String getIs_allocated() {
		return is_allocated;
	}

	public void setIs_allocated(String is_allocated) {
		this.is_allocated = is_allocated;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getLdmnd_stat_id() {
		return ldmnd_stat_id;
	}

	public void setLdmnd_stat_id(String ldmnd_stat_id) {
		this.ldmnd_stat_id = ldmnd_stat_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrig_doc_id() {
		return orig_doc_id;
	}

	public void setOrig_doc_id(String orig_doc_id) {
		this.orig_doc_id = orig_doc_id;
	}

	public int getOriginal_qty() {
		return original_qty;
	}

	public void setOriginal_qty(int original_qty) {
		this.original_qty = original_qty;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getSfield1() {
		return sfield1;
	}

	public void setSfield1(String sfield1) {
		this.sfield1 = sfield1;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
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
	
	
	
	public String getPi_doc_id() {
		return pi_doc_id;
	}

	public void setPi_doc_id(String pi_doc_id) {
		this.pi_doc_id = pi_doc_id;
	}

	
	
	public String getIs_vendor() {
		return is_vendor;
	}

	public void setIs_vendor(String is_vendor) {
		this.is_vendor = is_vendor;
	}

	@XmlTransient
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public static final EntityManager entityManager() {
		return EMFAstea.get().createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public static List<ViewShippingList> findViewShippingListInfoEntriesByrequest_id(
			String value) {
		EntityManager em = entityManager();
		try {
			em.clear();
			Query query = em
					.createQuery("select o from ViewShippingList o WHERE o.fr_warehouse_id =:fr_warehouse_id order by o.orig_doc_id").setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
			
			query.setParameter("fr_warehouse_id", value);
			List<ViewShippingList> resultList = query.getResultList();
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
	
}
