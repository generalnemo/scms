package org.scms.model.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.scms.enumerate.citem.CItemState;

@Entity
@Table(name = "configuration_item_revision")
public class CItemRevision extends AbstractTemporalModel {

	private static final long serialVersionUID = 7061569905854571059L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "revision_name", length = 1024, nullable = false)
	private String name;

	@Column(name = "revision_description", length = 2048, nullable = false)
	private String description;

	@ManyToOne
	@JoinColumn(name = "citem_id", nullable = false)
	private CItem cItem;

	@ManyToOne
	@JoinColumn(name = "citem_prev_revision_id")
	private CItemRevision prevRevision;

	@Column(name = "citem_state")
	private CItemState state = CItemState.CREATED;

	@Column(name = "citem_readiness")
	private double readiness;

	@Lob
	@Basic
	@Column(name = "revision_data")
	private byte[] data;

	@Column(name = "revision_data_content_type")
	private String contentType;

	@Column(name = "file_name")
	private String fileName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contexCItemRevision", fetch = FetchType.LAZY)
	private List<CItemsRelationship> relationships;

	public double getReadiness() {
		return readiness;
	}

	public void setReadiness(double readiness) {
		this.readiness = readiness;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CItem getcItem() {
		return cItem;
	}

	public void setcItem(CItem cItem) {
		this.cItem = cItem;
	}

	public CItemRevision getPrevRevision() {
		return prevRevision;
	}

	public void setPrevRevision(CItemRevision prevRevision) {
		this.prevRevision = prevRevision;
	}

	public CItemState getState() {
		return state;
	}

	public void setState(CItemState state) {
		this.state = state;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<CItemsRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<CItemsRelationship> relationships) {
		this.relationships = relationships;
	}

}
