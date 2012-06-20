package org.scms.view.bean.document;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.UploadedFile;
import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemOperationType;
import org.scms.enumerate.citem.CItemRelationshipType;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItemRevision;
import org.scms.model.entity.LogEntry;
import org.scms.service.CItemRevisionService;
import org.scms.view.bean.AbstractCItemBean;

import com.ocpsoft.pretty.faces.application.PrettyRedirector;

@Named("userDocument")
@ViewScoped
public class UserDocumentBean extends AbstractCItemBean {

	private static final long serialVersionUID = -5533974832781202095L;

	@Inject
	private CItemRevisionService revisionService;

	@Override
	@PostConstruct
	public void init() {
		super.init();
		type = CItemType.DOCUMENT;
		categories = Arrays.asList(CItemControlCategory.CC4);
		addProperty(PRETTY_CATALOG, "pretty:documentsCatalog");
		addProperty(PRETTY_ADD, "pretty:addDocument");
		addProperty(PRETTY_EDIT, "pretty:editDocument");
		addProperty(PRETTY_VIEW, "pretty:viewDocument");
	}

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
			object.initObjectTreeNode();
			CItemRevision revision = new CItemRevision();
			revision.setcItem(object);
			revision.setCreatedBy(userBean.getCurrentUser());
			object.getRevisions().add(revision);
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

	public void documentUploadListener(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		if (file == null) {
			return;
		}
		if (object.getId() == 0) {
			super.documentUploadListener(event);
			return;
		}
		int revisionsCount = object.getRevisions().size();
		object.getRevisions().get(revisionsCount - 1)
				.setData(file.getContents());
		object.getRevisions().get(revisionsCount - 1)
				.setContentType(file.getContentType());
		object.getRevisions().get(revisionsCount - 1)
				.setFileName(file.getFileName());
	}

	public void deleteUploadedDocument() {
		int revisionsCount = object.getRevisions().size();
		CItemRevision revision = object.getRevisions().get(revisionsCount - 1);
		revision.setData(null);
		revision.setContentType(null);
		revision.setFileName(null);
	}

	public String addObject() {
		int revisionsCount = object.getRevisions().size();
		if (object.getRevisions().get(revisionsCount - 1).getData() == null) {
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Необходимо загрузить файл документа", null));
			return null;
		}
		return super.addObject();
	}

	public void saveObjectRevision() {
		int revisionsCount = object.getRevisions().size();
		if (object.getRevisions().get(revisionsCount - 1).getData() == null) {
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Необходимо загрузить файл документа", null));
			return;
		}
		if (object.getSelectedCItemRevision() == null) {
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Необходимо выбрать версию документа", null));
			return;
		}
		createLogEntriesForMainAttributes();
		object.getRevisions()
				.get(revisionsCount - 1)
				.setPrevRevision(
						object.getSelectedCItemRevision().getRevision());
		object.getSelectedCItemRevision().getRevision().setCurrentRevision(false);
		for (CItemRevision revision : object.getRevisions()) {
			revision.setCurrentRevision(false);
		}
		object.getRevisions().get(revisionsCount - 1).setCurrentRevision(true);
		try {
			if (object.getLogEntries() == null) {
				object.setLogEntries(new ArrayList<LogEntry>());
			}
			if (object.getLogEntries().isEmpty()) {
				LogEntry entry = new LogEntry();
				entry.setcItem(object);
				object.getLogEntries().add(entry);
			}
			int entriesSize = object.getLogEntries().size();
			object.getLogEntries().get(entriesSize - 1)
					.setType(CItemOperationType.VERSION_CREATION);
		} catch (Exception e) {
			logger.error(e);
		}
		super.saveObjectRevision();
		CItemRevision currentRevision = revisionService
				.getCurrentRevision(object);
		revisionService.updateRelationships(
				CItemRelationshipType.IS_OUTPUT_FOR, currentRevision);
		
	}

	public void onNodeSelectListener(NodeSelectEvent event) {
		logger.info("in listener");
	}

}
