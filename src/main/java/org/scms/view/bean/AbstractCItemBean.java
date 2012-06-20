package org.scms.view.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemDifficulty;
import org.scms.enumerate.citem.CItemEditableProperties;
import org.scms.enumerate.citem.CItemOperationType;
import org.scms.enumerate.citem.CItemRelationshipType;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.model.entity.CItemRevision;
import org.scms.model.entity.CItemsRelationship;
import org.scms.model.entity.LogEntry;
import org.scms.model.entity.LogEntryEditedProperty;
import org.scms.model.entity.User;
import org.scms.service.AbstractCRUDService;
import org.scms.service.CItemService;
import org.scms.util.JSFUtil;

import com.ocpsoft.pretty.faces.application.PrettyRedirector;

public abstract class AbstractCItemBean extends AbstractObjectBean<CItem>
		implements Serializable {

	private static final long serialVersionUID = 7601502105334694505L;

	protected CItemType type;

	protected List<CItemControlCategory> categories;

	protected List<CItemDifficulty> difficultyCoeffs = Arrays
			.asList(CItemDifficulty.values());

	@Inject
	protected CItemService cItemService;

	protected List<LogEntry> logEntries;

	@Override
	protected void pageLoad() {
		if (getObject() != null)
			return;
		if (objectId == null) {
			createNewObject();
			return;
		}
		try {
			setObject(cItemService.findById(Long.parseLong(objectId)));
		} catch (NumberFormatException e) {
			PrettyRedirector.getInstance().redirect(fContext,
					getProperty(PRETTY_404));
			return;
		}
		if (object == null) {
			PrettyRedirector.getInstance().redirect(fContext,
					getProperty(PRETTY_404));
			return;
		}

	}

	protected void createNewObject() {
		setObject(new CItem());
		object.setType(type);
		CItemRevision revision = new CItemRevision();
		revision.setcItem(object);
		revision.setCurrentRevision(true);
		revision.setCreatedBy(userBean.getCurrentUser());
		object.getRevisions().add(revision);
		LogEntry entry = new LogEntry();
		entry.setcItem(object);
		entry.setType(CItemOperationType.VERSION_CREATION);
		entry.setCreatedBy(userBean.getCurrentUser());
		object.getLogEntries().add(entry);
		object.setCreatedBy(userBean.getCurrentUser());
		object.setcCategory(categories.get(0));
	}

	public void documentUploadListener(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		if (file != null) {
			int revisionsCount = object.getRevisions().size();
			CItemRevision revision = object.getRevisions().get(
					revisionsCount - 1);
			revision.setData(file.getContents());
			revision.setContentType(file.getContentType());
			revision.setFileName(file.getFileName());
		}
	}

	public void createLogEntriesForMainAttributes() {
		if (object.getcCategory().isCc4() || object.getcCategory().isCc3()) {
			return;
		}
		CItem cloneItem = cItemService.lazyFindById(object.getId());
		LogEntry entry = new LogEntry();
		createLogEntryForStringAttribute(entry, object.getName(),
				cloneItem.getName(), CItemEditableProperties.NAME);
		createLogEntryForStringAttribute(entry, object.getDescription(),
				cloneItem.getDescription(), CItemEditableProperties.DESCRIPTION);
		createLogEntryForNumberAttribute(entry, object.getLaboriousness(),
				cloneItem.getLaboriousness(),
				CItemEditableProperties.LABORIOUSNESS);
		createLogEntryForNumberAttribute(entry, object.getDifficulty()
				.getDifficultyCoeffValue(), cloneItem.getDifficulty()
				.getDifficultyCoeffValue(), CItemEditableProperties.DIFFICULTY);
		createLogEntryForUserTaskRole(entry, object.getResourceManager(),
				cloneItem.getResourceManager(),
				CItemEditableProperties.RESOURCE_MANAGER);
		createLogEntryForUserTaskRole(entry, object.getCurator(),
				cloneItem.getCurator(), CItemEditableProperties.CURATOR);
		createLogEntryForUserTaskRole(entry, object.getController(),
				cloneItem.getController(), CItemEditableProperties.CONTROLLER);
		createLogEntryForUserTaskRole(entry, object.getPerformer(),
				cloneItem.getPerformer(), CItemEditableProperties.PERFORMER);
		createLogEntryForDateAttribute(entry, object.getStartProcessDate(),
				cloneItem.getStartProcessDate(),
				CItemEditableProperties.START_PROCESS_DATE);
		createLogEntryForDateAttribute(entry, object.getEndProcessDate(),
				cloneItem.getEndProcessDate(),
				CItemEditableProperties.END_PROCESS_DATE);
		if (!entry.getEditedProperties().isEmpty()) {
			entry.setcItem(object);
			entry.setType(CItemOperationType.SAVING);
			entry.setCreatedBy(userBean.getCurrentUser());
			object.getLogEntries().add(entry);
		}
	}

	// not for document
	protected void addRelationship(CItemRevision revisionFrom,
			CItemRelationshipType rType) {
		CItemsRelationship relationship = new CItemsRelationship();
		relationship.setType(rType);
		relationship.setCreatedBy(userBean.getCurrentUser());
		relationship.setcItemRevisionFrom(revisionFrom);
		relationship.setcItemRevisionTo(object.getRevisions().get(0));
		object.getRevisions().get(0).getRelationships().add(relationship);
	}

	protected void deleteRelationship(CItemRevision revisionFrom,
			CItemRelationshipType rType) {
		CItemsRelationship delRelationship = null;
		for (CItemsRelationship relationship : object.getRevisions().get(0)
				.getRelationships()) {
			if (revisionFrom.getId() == relationship.getcItemRevisionFrom()
					.getId() && relationship.getType() == rType) {
				delRelationship = relationship;
				break;
			}
		}
		if (delRelationship != null) {
			object.getRevisions().get(0).getRelationships()
					.remove(delRelationship);
		}
	}

	public void saveObject() {
		int revisionsCount = object.getRevisions().size();
		createLogEntriesForMainAttributes();
		if (object.getRevisions().get(revisionsCount - 1).getId() == 0) {
			object.getRevisions().remove(revisionsCount - 1);
		}
		super.saveObject();
		PrettyRedirector.getInstance().redirect(fContext,
				getProperty(PRETTY_CATALOG));
		return;
	}

	public void saveObjectRevision() {
		super.saveObject();
		PrettyRedirector.getInstance().redirect(fContext,
				getProperty(PRETTY_CATALOG));
		return;
	}

	public void createLogEntryForNewRevision() {
		int revisionsSize = object.getRevisions().size();
		CItemRevision revision = object.getRevisions().get(revisionsSize - 1);
		if (revision.getId() != 0)
			return;
		LogEntry entry = new LogEntry();
		entry.setcItem(object);
		entry.setType(CItemOperationType.VERSION_CREATION);
	}

	private void createLogEntryForUserTaskRole(LogEntry entry, User newValue,
			User oldValue, CItemEditableProperties property) {
		if (newValue == null && oldValue == null) {
			return;
		}
		if (newValue == null && oldValue != null) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, null, "");
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
		if (oldValue == null
				|| oldValue != null
				&& !newValue.getUserLoginName().equals(
						oldValue.getUserLoginName())) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, newValue.getUserLoginName(),
					newValue.getFullName());
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
	}

	private void createLogEntryForStringAttribute(LogEntry entry,
			String newValue, String oldValue, CItemEditableProperties property) {
		if (newValue == null && oldValue == null) {
			return;
		}
		if (newValue == null && oldValue != null) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, null, "");
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
		if (oldValue == null || oldValue != null && !newValue.equals(oldValue)) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, newValue, newValue);
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
	}

	private void createLogEntryForNumberAttribute(LogEntry entry,
			Number newValue, Number oldValue, CItemEditableProperties property) {
		if (newValue == null && oldValue == null) {
			return;
		}
		if (newValue == null && oldValue != null) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, null, "");
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
		if (oldValue == null || oldValue != null && !newValue.equals(oldValue)) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, newValue.toString(), newValue.toString());
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
	}

	private void createLogEntryForDateAttribute(LogEntry entry, Date newValue,
			Date oldValue, CItemEditableProperties property) {
		if (newValue == null && oldValue == null) {
			return;
		}
		if (newValue == null && oldValue != null) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, null, "");
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
		if (oldValue == null || oldValue != null && !newValue.equals(oldValue)) {
			LogEntryEditedProperty editedPropertyLog = new LogEntryEditedProperty(
					entry, property, JSFUtil.formatDate(newValue,
							"dd.MM.yyyy HH:mm:ss"), JSFUtil.formatDate(
							newValue, "dd.MM.yyyy HH:mm:ss"));
			entry.getEditedProperties().add(editedPropertyLog);
			return;
		}
	}

	public void renderAttributesHistory() {
		logEntries = cItemService.findLogEntriesByCItemId(object.getId());
	}

	@Override
	protected AbstractCRUDService<CItem> getService() {
		return cItemService;
	}

	public void setSelectedResourceManager(String userName) {
		if (userName == null || userName.equals("")) {
			object.setResourceManager(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			object.setResourceManager(user);
			break;
		}
	}

	public String getSelectedResourceManager() {
		try {
			return object.getResourceManager().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectedCurator(String userName) {
		if (userName == null || userName.equals("")) {
			object.setCurator(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			object.setCurator(user);
			break;
		}
	}

	public String getSelectedCurator() {
		try {
			return object.getCurator().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectedPerformer(String userName) {
		if (userName == null || userName.equals("")) {
			object.setPerformer(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			object.setPerformer(user);
			break;
		}
	}

	public String getSelectedPerformer() {
		try {
			return object.getPerformer().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectedController(String userName) {
		if (userName == null || userName.equals("")) {
			object.setController(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			object.setController(user);
			break;
		}
	}

	public String getSelectedController() {
		try {
			return object.getController().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public List<CItemControlCategory> getCategories() {
		return categories;
	}

	public List<CItemDifficulty> getDifficultyCoeffs() {
		return difficultyCoeffs;
	}

	public List<LogEntry> getLogEntries() {
		return logEntries;
	}

}
