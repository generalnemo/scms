package org.scms.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.scms.enumerate.citem.CItemOperationType;

@Entity
@Table(name = "log_entry")
public class LogEntry extends AbstractTemporalModel {

	private static final long serialVersionUID = -7564945046327374762L;

	@ManyToOne
	@JoinColumn(name = "citem_id", nullable = false, updatable = false)
	private CItem cItem;

	@Column(name = "operation_type")
	private CItemOperationType type;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "logEntry", fetch = FetchType.LAZY)
	private List<LogEntryEditedProperty> editedProperties;
	
	private transient CItem cItemForEntryCreatedDate;

	public CItem getcItem() {
		return cItem;
	}

	public void setcItem(CItem cItem) {
		this.cItem = cItem;
	}

	public CItem getcItemForEntryCreatedDate() {
		return cItemForEntryCreatedDate;
	}

	public void setcItemForEntryCreatedDate(CItem cItemForEntryCreatedDate) {
		this.cItemForEntryCreatedDate = cItemForEntryCreatedDate;
	}

	public CItemOperationType getType() {
		return type;
	}

	public void setType(CItemOperationType type) {
		this.type = type;
	}

	public List<LogEntryEditedProperty> getEditedProperties() {
		return editedProperties;
	}

	public void setEditedProperties(List<LogEntryEditedProperty> editedProperties) {
		this.editedProperties = editedProperties;
	}

}
