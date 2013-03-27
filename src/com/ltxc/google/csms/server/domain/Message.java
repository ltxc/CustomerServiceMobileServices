package com.ltxc.google.csms.server.domain;

import java.io.Serializable;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.persistence.*;

import com.ltxc.google.csms.server.servlet.utils.EMFCSMOBILE;
import com.ltxc.google.csms.server.servlet.utils.GoogleVoiceMessaging;
import com.ltxc.google.csms.server.servlet.utils.ServletUtils;
import com.ltxc.google.csms.shared.SharedConstants;
import com.ltxc.google.csms.shared.StringHelper;



import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The persistent class for the message database table.
 * 
 */
@Entity
@Table(name = "message")
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String Status_Send = "send";
	public static final String Status_Pending = "pending";

	private static Logger logger = Logger.getLogger(Message.class.getName());

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private byte[] attachment;

	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;

	private String source;

	@Lob()
	private String message;

	// store paging number
	private String param1;

	// store pushnotification token
	private String param2;

	private String param3;

	private String param4;

	private String param5;

	private String status;

	private String subject;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submitDate;

	private String submitter;

	private String target;

	@Version
	@Column(name = "version")
	private Integer version;

	public Message() {
	}

	public Message(String submitter, String source, String target, String paging, int pagingStatus, String pushnotification, int pushnotificationstatus) {
		setSubmitter(submitter);
		setSource(source);
		setTarget(target);
		if(pagingStatus==1)
			this.setParam1(paging);
		if(pushnotificationstatus==1)
			this.setParam2(pushnotification);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getAttachment() {
		return this.attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getParam1() {
		return this.param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return this.param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return this.param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return this.param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return this.param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getSubmitter() {
		return this.submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

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

	public void persist() {
		EntityManager em = entityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			this.setSubmitDate(ServletUtils.getCurrentDate());
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
			Message attached = em.find(Message.class, this.id);
			em.remove(attached);
			tx.commit();
		} catch (Throwable t) {
			t.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public void send() {
		// email
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.port", "25");
			props.setProperty("mail.smtp.host", SharedConstants.Mail_Host);
			props.setProperty("mail.from", SharedConstants.Mail_From);
			props.put("mail.debug", "true");
			// props.setProperty("mail.user", "jinsong_lu@ltxc.com");
			// props.setProperty("mail.password", "");

			Session mailSession = Session.getInstance(props, null);
			// Transport transport = mailSession.getTransport();

			MimeMessage mimeMessage = new MimeMessage(mailSession);
			mimeMessage.setFrom();
			mimeMessage.setSubject(this.getSubject());
			mimeMessage.setContent(this.getMessage(), "text/plain");
			if (this.getTarget() != null)
				mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(
						this.getTarget()));

			Transport.send(mimeMessage);
			// set sender
			this.setStatus(Status_Send);

		} catch (Exception xe) {
			xe.printStackTrace();
			logger.log(Level.WARNING,
					"Failed to send email: " + xe.getMessage());
			this.setStatus(Status_Pending);
		}

		if (this.param1 != null && !this.param1.trim().isEmpty()) {
			// paging
			try {
				GoogleVoiceMessaging.get(SharedConstants.SYSTEM_GOOGLE_VOICE_EMAIL,SharedConstants.SYSTEM_GOOGLE_VOICE_PASSWORD).sendTextMessage(this.param1,StringHelper.truncateLongName(this.getMessage(), SharedConstants.MAXSIZE_GOOGLEVOICE_MESSAGING_TEXT));
			} catch (Exception xe) {
				xe.printStackTrace();
			}
		}

		// now persist it
		this.setDeliveryDate(ServletUtils.getCurrentDate());
		this.persist();
	}

	public static final EntityManager entityManager() {
		return EMFCSMOBILE.get().createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public static List<Message> findAllMessages() {
		EntityManager em = entityManager();
		try {
			List<Message> list = em.createQuery("select o from Message o")
					.getResultList();
			// force to get all the Message
			list.size();
			return list;
		} finally {
			em.close();
		}
	}

	public static Message findMessage(Long id) {
		if (id == null) {
			return null;
		}
		EntityManager em = entityManager();
		try {
			Message entity = em.find(Message.class, id);
			return entity;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Message> findMessageEntries(int firstResult,
			int maxResults) {
		EntityManager em = entityManager();
		try {
			List<Message> resultList = em
					.createQuery("select o from Message o")
					.setFirstResult(firstResult).setMaxResults(maxResults)
					.getResultList();
			// force it to materialize
			resultList.size();
			return resultList;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Message> findMessageEntriesBySubmitter(String value) {
		EntityManager em = entityManager();
		try {
			Query query = em
					.createQuery("select o from Message o WHERE o.Submitter =:Submitter");
			query.setParameter("Submitter", value);
			List<Message> resultList = query.getResultList();
			// force it to materialize
			resultList.size();
			return resultList;
		} finally {
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public static List<Message> findMessageEntriesBySubmitter(String value,
			int firstResult, int maxResults) {
		EntityManager em = entityManager();
		try {
			Query query = em
					.createQuery("select o from Message o WHERE o.Submitter =:Submitter");
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setParameter("Submitter", value);
			List<Message> resultList = query.getResultList();
			// force it to materialize
			resultList.size();
			return resultList;
		} finally {
			em.close();
		}
	}

}