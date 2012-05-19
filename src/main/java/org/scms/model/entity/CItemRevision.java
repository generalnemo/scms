package org.scms.model.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Double readiness;

	@Lob
	@Basic
	@Column(name = "revision_data")
	private byte[] data;

	@Column(name = "revision_data_content_type")
	private String contentType;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "is_baseline")
	private boolean baseLine = false;
	
	@Column(name = "is_current_revision")
	private boolean currentRevision = false;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contexCItemRevision", fetch = FetchType.LAZY)
	private List<CItemsRelationship> relationships;

	public Double getReadiness() {
		return readiness;
	}

	public void setReadiness(Double readiness) {
		this.readiness = readiness;
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

	public boolean isBaseLine() {
		return baseLine;
	}

	public void setBaseLine(boolean baseLine) {
		this.baseLine = baseLine;
	}

	public boolean isCurrentRevision() {
		return currentRevision;
	}

	public void setCurrentRevision(boolean currentRevision) {
		this.currentRevision = currentRevision;
	}

}
