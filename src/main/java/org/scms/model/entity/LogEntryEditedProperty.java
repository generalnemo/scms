package org.scms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.scms.enumerate.citem.CItemEditableProperties;

@Entity
@Table(name = "log_entry_edited_property")
public class LogEntryEditedProperty extends AbstractIdentityModel {

	private static final long serialVersionUID = 4045086791789327769L;

	@ManyToOne
	@JoinColumn(name = "log_entry_id", nullable = false, updatable = false)
	private LogEntry logEntry;

	@Enumerated(EnumType.STRING)
	@Column(name = "property_name", nullable = false, updatable = false)
	private CItemEditableProperties property;

	@Column(name = "property_value", nullable = false, updatable = false)
	private String propertyValue;

	@Column(name = "property_label", nullable = false, updatable = false)
	private String propertyLabel;

	public LogEntry getLogEntry() {
		return logEntry;
	}

	public void setLogEntry(LogEntry logEntry) {
		this.logEntry = logEntry;
	}

	public CItemEditableProperties getProperty() {
		return property;
	}

	public void setProperty(CItemEditableProperties property) {
		this.property = property;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyLabel() {
		return propertyLabel;
	}

	public void setPropertyLabel(String propertyLabel) {
		this.propertyLabel = propertyLabel;
	}

}
