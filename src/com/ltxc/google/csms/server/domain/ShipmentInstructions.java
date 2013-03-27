package com.ltxc.google.csms.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;

@XmlRootElement
@Table(name = "ShipmentInstructions")
@Entity
public class ShipmentInstructions implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private String id;

	@XmlTransient
	private byte version;

	@Column(name = "")
	private String ap_table_key;

	@Column(name = "")
	private String ap_description;

	@Column(name = "")
	private String ap_table_name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "")
	private Date lastupdated;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@XmlTransient
	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public String getAp_table_key() {
		return ap_table_key;
	}

	public void setAp_table_key(String ap_table_key) {
		this.ap_table_key = ap_table_key;
	}

	public String getAp_description() {
		return ap_description;
	}

	public void setAp_description(String ap_description) {
		this.ap_description = ap_description;
	}

	public String getAp_table_name() {
		return ap_table_name;
	}

	public void setAp_table_name(String ap_table_name) {
		this.ap_table_name = ap_table_name;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public static final EntityManager entityManager() {
		return EMFCSMOBILE.get().createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public static List<ShipmentInstructions> findAllShipmentInstructionss() {
		EntityManager em = entityManager();
		try {
			List<ShipmentInstructions> list = em.createQuery(
					"select o from ShipmentInstructions o").getResultList();
			// force to get all the ShipmentInstructions
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

}
