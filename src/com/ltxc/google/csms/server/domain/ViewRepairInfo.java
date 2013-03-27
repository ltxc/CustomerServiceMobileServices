package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.ltxc.google.csms.server.servlet.utils.EMFAstea;

@Entity
@Table(name = "_CSMobile_RepairOrder_Information")
@XmlRootElement
public class ViewRepairInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id;
	@Column(name = "request_id")
	private String request_id;
	@Column(name = "priority")
	private String priority;
	@Column(name = "cconth_id")
	private String cconth_id;
	@Column(name = "warr_type_id")
	private String warr_type_id;
	@Column(name = "po_id")
	private String po_id;
	@Column(name = "pcode_id")
	private String pcode_id;

	@Column(name = "dest_warehouse_id")
	private String dest_warehouse_id;

	@XmlTransient
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCconth_id() {
		return cconth_id;
	}

	public void setCconth_id(String cconth_id) {
		this.cconth_id = cconth_id;
	}

	public String getWarr_type_id() {
		return warr_type_id;
	}

	public void setWarr_type_id(String warr_type_id) {
		this.warr_type_id = warr_type_id;
	}

	public String getPo_id() {
		return po_id;
	}

	public void setPo_id(String po_id) {
		this.po_id = po_id;
	}

	public String getPcode_id() {
		return pcode_id;
	}

	public void setPcode_id(String pcode_id) {
		this.pcode_id = pcode_id;
	}

	public String getDest_warehouse_id() {
		return dest_warehouse_id;
	}

	public void setDest_warehouse_id(String dest_warehouse_id) {
		this.dest_warehouse_id = dest_warehouse_id;
	}

	public static final EntityManager entityManager() {
		return EMFAstea.get().createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public static List<ViewRepairInfo> findViewRepairInfoEntriesByrequest_id(
			String value) {
		EntityManager em = entityManager();
		try {
			Query query = em
					.createQuery("select o from ViewRepairInfo o WHERE o.request_id =:request_id").setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);;
			query.setParameter("request_id", value);
			List<ViewRepairInfo> resultList = query.getResultList();
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
