package org.scms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.scms.enumerate.citem.CItemRelationshipType;

@Entity
@Table(name = "citems_relationship")
public class CItemsRelationship extends AbstractTemporalModel {

	private static final long serialVersionUID = -4408260984086348939L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "context_citem_revision_id")
	private CItemRevision contexCItemRevision;

	@ManyToOne
	@JoinColumn(name = "citem_revision_from_id")
	private CItemRevision cItemRevisionFrom;

	@Column(name = "relationship_type")
	private CItemRelationshipType type;

	@ManyToOne
	@JoinColumn(name = "citem_revision_to_id")
	private CItemRevision cItemRevisionTo;

	@Override
	public Object getPrimaryKey() {
		return id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CItemRevision getContexCItemRevision() {
		return contexCItemRevision;
	}

	public void setContexCItemRevision(CItemRevision contexCItemRevision) {
		this.contexCItemRevision = contexCItemRevision;
	}

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
