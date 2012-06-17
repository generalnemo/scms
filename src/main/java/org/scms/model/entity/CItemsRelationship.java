package org.scms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.scms.enumerate.citem.CItemRelationshipType;

@Entity
@Table(name = "citems_relationship")
public class CItemsRelationship extends AbstractTemporalModel {

	private static final long serialVersionUID = -4408260984086348939L;

	@ManyToOne
	@JoinColumn(name = "citem_revision_from_id", nullable = false)
	private CItemRevision cItemRevisionFrom;

	@Column(name = "relationship_type", nullable = false)
	private CItemRelationshipType type;

	@ManyToOne
	@JoinColumn(name = "citem_revision_to_id", nullable = false)
	private CItemRevision cItemRevisionTo;

	public CItemRevision getcItemRevisionFrom() {
		return cItemRevisionFrom;
	}

	public void setcItemRevisionFrom(CItemRevision cItemRevisionFrom) {
		this.cItemRevisionFrom = cItemRevisionFrom;
	}

	public CItemRevision getcItemRevisionTo() {
		return cItemRevisionTo;
	}

	public void setcItemRevisionTo(CItemRevision cItemRevisionTo) {
		this.cItemRevisionTo = cItemRevisionTo;
	}

	public CItemRelationshipType getType() {
		return type;
	}

	public void setType(CItemRelationshipType type) {
		this.type = type;
	}

}
