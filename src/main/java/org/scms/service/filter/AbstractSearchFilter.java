package org.scms.service.filter;

import java.util.Date;

public abstract class AbstractSearchFilter {

	protected Long id;
	
	protected Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
