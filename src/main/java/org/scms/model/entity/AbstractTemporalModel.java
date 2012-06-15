package org.scms.model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractTemporalModel extends AbstractIdentityModel {

	private static final long serialVersionUID = 2434458957714267908L;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false, updatable = false)
	private User createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	public String getFormattedCreatedAtDate(){
		SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return format.format(createdAt);
	}

}
