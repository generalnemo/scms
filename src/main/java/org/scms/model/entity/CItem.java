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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.primefaces.model.TreeNode;
import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemDifficulty;
import org.scms.enumerate.citem.CItemEditableProperties;
import org.scms.enumerate.citem.CItemOperationType;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.CItemRevisionTreeNode;
import org.scms.util.JSFUtil;

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

	@Column(name = "control_category")
	private CItemControlCategory cCategory;

	@Column(name = "citem_difficulty")
	private CItemDifficulty difficulty = CItemDifficulty.STANDARD;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cItem", fetch = FetchType.LAZY)
	private List<CItemRevision> revisions = new ArrayList<CItemRevision>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cItem", fetch = FetchType.LAZY)
	private List<LogEntry> logEntries = new ArrayList<LogEntry>();

	private transient CItemRevisionTreeNode objectRevisionStructure;

	private transient CItemRevisionTreeNode selectedCItemRevision;

	public void initObjectTreeNode() {
		if (revisions == null || revisions.isEmpty())
			return;
		CItemRevision rootRevision = null;
		for (CItemRevision revision : revisions) {
			if (revision.getPrevRevision() == null) {
				rootRevision = revision;
				break;
			}
		}
		objectRevisionStructure = new CItemRevisionTreeNode();
		constructTree(new CItemRevisionTreeNode(rootRevision,
				objectRevisionStructure));
	}

	private void constructTree(CItemRevisionTreeNode root) {
		for (CItemRevision revision : revisions) {
			if (revision.getPrevRevision() != null
					&& revision.getPrevRevision().getId() == root.getRevision()
							.getId()) {
				new CItemRevisionTreeNode(revision, root);
			}
		}
		if (root.getChildCount() <= 0) {
			return;
		}
		for (TreeNode node : root.getChildren()) {
			constructTree((CItemRevisionTreeNode) node);
		}
	}

	@PrePersist
	protected void onCreate() {
		super.onCreate();
		if (logEntries.isEmpty()) {
			LogEntry logEntry = new LogEntry();
			logEntry.setcItem(this);
			logEntry.setCreatedBy(getCreatedBy());
			logEntry.setType(CItemOperationType.VERSION_CREATION);
			logEntries.add(logEntry);
		}
		LogEntry entry = logEntries.get(0);
		if (name != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.NAME, name, name));
		}
		if (description != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.DESCRIPTION, description,
							description));
		}
		if (startProcessDate != null) {
			String dateValue = JSFUtil.formatDate(startProcessDate,
					"dd.MM.yyyy HH:mm:ss");
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.START_PROCESS_DATE,
							dateValue, dateValue));
		}
		if (endProcessDate != null) {
			String dateValue = JSFUtil.formatDate(endProcessDate,
					"dd.MM.yyyy HH:mm:ss");
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.END_PROCESS_DATE,
							dateValue, dateValue));
		}
		if (curator != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.CURATOR,
							curator.getUserLoginName(), curator.getFullName()));
		}
		if (performer != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.PERFORMER,
							performer.getUserLoginName(), performer.getFullName()));
		}
		if (controller != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.CONTROLLER,
							controller.getUserLoginName(), controller.getFullName()));
		}
		if (resourceManager != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.RESOURCE_MANAGER,
							resourceManager.getUserLoginName(), resourceManager.getFullName()));
		}
		if (difficulty != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.DIFFICULTY,
							String.valueOf(difficulty.getDifficultyCoeffValue()), String.valueOf(difficulty.getDifficultyCoeffValue())));
		}
		if (laboriousness != null) {
			entry.getEditedProperties().add(
					new LogEntryEditedProperty(entry,
							CItemEditableProperties.LABORIOUSNESS,
							String.valueOf(laboriousness), String.valueOf(laboriousness)));
		}
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

	public CItemControlCategory getcCategory() {
		return cCategory;
	}

	public void setcCategory(CItemControlCategory cCategory) {
		this.cCategory = cCategory;
	}

	public TreeNode getObjectRevisionStructure() {
		return objectRevisionStructure;
	}

	public CItemRevisionTreeNode getSelectedCItemRevision() {
		return selectedCItemRevision;
	}

	public void setSelectedCItemRevision(
			CItemRevisionTreeNode selectedCItemRevision) {
		this.selectedCItemRevision = selectedCItemRevision;
	}

	public CItemDifficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(CItemDifficulty difficulty) {
		this.difficulty = difficulty;
	}

	public void setObjectRevisionStructure(
			CItemRevisionTreeNode objectRevisionStructure) {
		this.objectRevisionStructure = objectRevisionStructure;
	}

}
