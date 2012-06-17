package org.scms.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.scms.enumerate.ControlCategory;
import org.scms.enumerate.citem.CItemType;

@Entity
@Table(name = "configuration_item")
public class CItem extends AbstractTemporalModel {

	private static final long serialVersionUID = 4709031887312265590L;

	@Column(name = "citem_name", nullable = false)
	private String name;

	@Column(name = "citem_description", length = 2048, nullable = false)
	private String description;

	@Column(name = "citem_type", nullable = false, updatable = false)
	private CItemType type;

	@ManyToOne
	@JoinColumn(name = "citem_controller")
	private User controller;

	@ManyToOne
	@JoinColumn(name = "citem_curator")
	private User curator;

	@ManyToOne
	@JoinColumn(name = "citem_performer")
	private User performer;

	@ManyToOne
	@JoinColumn(name = "citem_resource_manager")
	private User resourceManager;

	@Column(name = "citem_laboriousness")
	private Double laboriousness;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_process_date")
	private Date startProcessDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_process_date")
	private Date endProcessDate;
	
	@Column(name="control_category")
	private ControlCategory cCategory;
	
	@ManyToOne
	@JoinColumn(name = "citem_difficulty")
	private DifficultyScaleCoeff difficulty;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cItem", fetch = FetchType.LAZY)
	private List<CItemRevision> revisions = new ArrayList<CItemRevision>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cItem", fetch = FetchType.LAZY)
	private List<LogEntry> logEntries = new ArrayList<LogEntry>();

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

	public CItemType getType() {
		return type;
	}

	public void setType(CItemType type) {
		this.type = type;
	}

	public User getController() {
		return controller;
	}

	public void setController(User controller) {
		this.controller = controller;
	}

	public User getCurator() {
		return curator;
	}

	public void setCurator(User curator) {
		this.curator = curator;
	}

	public User getPerformer() {
		return performer;
	}

	public void setPerformer(User performer) {
		this.performer = performer;
	}

	public User getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(User resourceManager) {
		this.resourceManager = resourceManager;
	}

	public Double getLaboriousness() {
		return laboriousness;
	}

	public void setLaboriousness(Double laboriousness) {
		this.laboriousness = laboriousness;
	}

	public DifficultyScaleCoeff getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(DifficultyScaleCoeff difficulty) {
		this.difficulty = difficulty;
	}

	public Date getStartProcessDate() {
		return startProcessDate;
	}

	public void setStartProcessDate(Date startProcessDate) {
		this.startProcessDate = startProcessDate;
	}

	public Date getEndProcessDate() {
		return endProcessDate;
	}

	public void setEndProcessDate(Date endProcessDate) {
		this.endProcessDate = endProcessDate;
	}

	public List<CItemRevision> getRevisions() {
		return revisions;
	}

	public void setRevisions(List<CItemRevision> revisions) {
		this.revisions = revisions;
	}

	public List<LogEntry> getLogEntries() {
		return logEntries;
	}

	public void setLogEntries(List<LogEntry> logEntries) {
		this.logEntries = logEntries;
	}

	public ControlCategory getcCategory() {
		return cCategory;
	}

	public void setcCategory(ControlCategory cCategory) {
		this.cCategory = cCategory;
	}

}
