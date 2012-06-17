package org.scms.model.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
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

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.scms.enumerate.citem.CItemState;
import org.scms.util.JSFUtil;

@Entity
@Table(name = "configuration_item_revision")
public class CItemRevision extends AbstractTemporalModel {

	private static final long serialVersionUID = 7061569905854571059L;

	@Column(name = "revision_name", length = 1024)
	private String name;

	@Column(name = "revision_description", length = 2048)
	private String description;

	@ManyToOne
	@JoinColumn(name = "citem_id", nullable = false)
	private CItem cItem;

	@ManyToOne
	@JoinColumn(name = "citem_prev_revision_id")
	private CItemRevision prevRevision;

	@Column(name = "citem_state", nullable = false)
	private CItemState state = CItemState.CREATED;

	@Column(name = "citem_readiness")
	private Double readiness = 0.0;

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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cItemRevisionTo", fetch = FetchType.LAZY)
	private List<CItemsRelationship> relationships = new ArrayList<CItemsRelationship>();

	private transient List<CItemRevision> inputDocumentRevisions = new ArrayList<CItemRevision>();

	private transient List<CItemRevision> outputDocumentRevisions = new ArrayList<CItemRevision>();

	private transient StreamedContent file;

	public List<CItemRevision> getInputDocumentRevisions() {
		inputDocumentRevisions.clear();
		if (relationships == null || relationships.isEmpty()) {
			return Collections.emptyList();
		}
		for (CItemsRelationship r : relationships) {
			if (r.getType().isInputTo()) {
				inputDocumentRevisions.add(r.getcItemRevisionFrom());
			}
		}
		return inputDocumentRevisions;
	}

	public List<CItemRevision> getOutputDocumentRevisions() {
		outputDocumentRevisions.clear();
		if (relationships == null || relationships.isEmpty()) {
			return Collections.emptyList();
		}
		for (CItemsRelationship r : relationships) {
			if (r.getType().isOutputFor()) {
				outputDocumentRevisions.add(r.getcItemRevisionFrom());
			}
		}
		return outputDocumentRevisions;
	}

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

	public StreamedContent getFile() throws UnsupportedEncodingException {
		if (file == null) {
			InputStream stream = new ByteArrayInputStream(data);
			file = new DefaultStreamedContent(stream, contentType,
					JSFUtil.encodedFilename(fileName));
		}
		return file;
	}

}
