package org.scms.view.bean.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemOperationType;
import org.scms.enumerate.citem.CItemRelationshipType;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.model.entity.CItemRevision;
import org.scms.model.entity.CItemsRelationship;
import org.scms.model.entity.LogEntry;
import org.scms.model.entity.User;
import org.scms.service.CItemRevisionService;
import org.scms.service.CItemService;
import org.scms.service.filter.CItemRevisionSearchFilter;
import org.scms.view.bean.AbstractCItemBean;

@Named("userTask")
@ViewScoped
public class UserTaskBean extends AbstractCItemBean {

	private static final long serialVersionUID = 8132779611590184248L;

	private CItemRevisionSearchFilter filter;

	private List<CItemRevision> foundCItemsRevisions;

	@Inject
	private CItemRevisionService revisionService;

	private CItem document;

	@Override
	@PostConstruct
	public void init() {
		super.init();
		type = CItemType.TASK;
		filter = new CItemRevisionSearchFilter();
		filter.setType(CItemType.DOCUMENT);
		categories = Arrays.asList(CItemControlCategory.CC2,
				CItemControlCategory.CC3);
		addProperty(PRETTY_CATALOG, "pretty:tasksCatalog");
		addProperty(PRETTY_ADD, "pretty:addTask");
		addProperty(PRETTY_EDIT, "pretty:editTask");
		addProperty(PRETTY_VIEW, "pretty:viewTask");
	}

	public void findDocuments() {
		filter.setType(CItemType.DOCUMENT);
		foundCItemsRevisions = revisionService.execute(filter);
	}

	public void findTasks() {
		filter.setType(CItemType.TASK);
		foundCItemsRevisions = revisionService.execute(filter);
	}

	public void clearSearchResults() {
		filter = new CItemRevisionSearchFilter();
		if (foundCItemsRevisions != null)
			foundCItemsRevisions.clear();
	}

	public void addToAdded(CItemRevision documentRevision) {
		addRelationship(documentRevision, CItemRelationshipType.IS_INPUT_TO);
	}

	public void addParentTaskRevision(CItemRevision taskRevision) {
		object.getRevisions().get(0).setParentRevision(null);
		CItemsRelationship prevParentRelationship = null;
		for (CItemsRelationship relationship : object.getRevisions().get(0)
				.getRelationships()) {
			if (relationship.getType().isParentFor()) {
				prevParentRelationship = relationship;
				break;
			}
		}
		if (prevParentRelationship != null) {
			object.getRevisions().get(0).getRelationships()
					.remove(prevParentRelationship);
		}
		addRelationship(taskRevision, CItemRelationshipType.IS_PARENT_FOR);
		filter = new CItemRevisionSearchFilter();
		foundCItemsRevisions = new ArrayList<CItemRevision>();
	}

	public void documentUploadListener(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		if (file != null) {
			CItemRevision revision = document.getRevisions().get(0);
			revision.setContentType(file.getContentType());
			revision.setData(file.getContents());
			revision.setFileName(file.getFileName());
		}
	}

	public void deleteUploadedDocument() {
		int revisionsCount = document.getRevisions().size();
		CItemRevision revision = document.getRevisions()
				.get(revisionsCount - 1);
		revision.setData(null);
		revision.setContentType(null);
		revision.setFileName(null);
	}

	protected void createNewObject() {
		super.createNewObject();
		createNewDocument();
	}

	protected void createNewDocument() {
		document = new CItem();
		document.setType(CItemType.DOCUMENT);
		document.setcCategory(CItemControlCategory.CC1);
		CItemRevision revision = new CItemRevision();
		revision.setcItem(document);
		revision.setCurrentRevision(true);
		revision.setCreatedBy(userBean.getCurrentUser());
		document.getRevisions().add(revision);
		LogEntry entry = new LogEntry();
		entry.setcItem(document);
		entry.setType(CItemOperationType.VERSION_CREATION);
		entry.setCreatedBy(userBean.getCurrentUser());
		document.getLogEntries().add(entry);
		document.setCreatedBy(userBean.getCurrentUser());
	}

	public void addNewDocument() {
		RequestContext context = RequestContext.getCurrentInstance();
		try {
			getService().add(document);
			document = ((CItemService) getService()).findById(document.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		addRelationship(document.getRevisions().get(0), CItemRelationshipType.IS_OUTPUT_FOR);
		context.addCallbackParam("valid", true);
		createNewDocument();
	}
	
	public void deleteParentTaskRevision(CItemRevision revision) {
		deleteRelationship(revision, CItemRelationshipType.IS_PARENT_FOR);
		object.getRevisions().get(0).setParentRevision(null);
	}

	public void deleteInputDocument(CItemRevision revision) {
		deleteRelationship(revision, CItemRelationshipType.IS_INPUT_TO);
	}

	public void deleteOutputDocument(CItemRevision revision) {
		deleteRelationship(revision, CItemRelationshipType.IS_OUTPUT_FOR);
		try {
			cItemService.remove(revision.getcItem());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void setSelectedDocumentResourceManager(String userName) {
		if (userName == null || userName.equals("")) {
			document.setResourceManager(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			document.setResourceManager(user);
			break;
		}
	}

	public String getSelectedDocumentResourceManager() {
		try {
			return document.getResourceManager().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectedDocumentCurator(String userName) {
		if (userName == null || userName.equals("")) {
			document.setCurator(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			document.setCurator(user);
			break;
		}
	}

	public String getSelectedDocumentCurator() {
		try {
			return document.getCurator().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectedDocumentPerformer(String userName) {
		if (userName == null || userName.equals("")) {
			document.setPerformer(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			document.setPerformer(user);
			break;
		}
	}

	public String getSelectedDocumentPerformer() {
		try {
			return document.getPerformer().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public void setSelectedDocumentController(String userName) {
		if (userName == null || userName.equals("")) {
			document.setController(null);
			return;
		}
		for (User user : userList) {
			if (!user.getUserLoginName().equals(userName))
				continue;
			document.setController(user);
			break;
		}
	}

	public String getSelectedDocumentController() {
		try {
			return document.getController().getUserLoginName();
		} catch (Exception e) {
			return null;
		}
	}

	public CItemRevisionSearchFilter getFilter() {
		return filter;
	}

	public CItem getDocument() {
		return document;
	}

	public List<CItemRevision> getFoundCItemsRevisions() {
		return foundCItemsRevisions;
	}

}
