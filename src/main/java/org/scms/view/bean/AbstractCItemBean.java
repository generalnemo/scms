package org.scms.view.bean;

import java.io.Serializable;

import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.scms.enumerate.citem.CItemOperationType;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.model.entity.CItemRevision;
import org.scms.model.entity.LogEntry;
import org.scms.model.entity.User;
import org.scms.service.AbstractCRUDService;
import org.scms.service.CItemService;

import com.ocpsoft.pretty.faces.application.PrettyRedirector;

public abstract class AbstractCItemBean extends AbstractObjectBean<CItem>
		implements Serializable {

	private static final long serialVersionUID = 7601502105334694505L;

	protected CItemType type;

	@Inject
	protected CItemService cItemService;

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
					getProperty(PRETTY_CATALOG));
			return;
		}
		if (object == null) {
			PrettyRedirector.getInstance().redirect(fContext,
					getProperty(PRETTY_CATALOG));
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
		entry.setType(CItemOperationType.CREATION);
		entry.setCreatedBy(userBean.getCurrentUser());
		object.getLogEntries().add(entry);
		object.setCreatedBy(userBean.getCurrentUser());
	}

	public void documentUploadListener(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		if (file != null) {
			int revisionsCount = object.getRevisions().size();
			CItemRevision revision = object.getRevisions().get(revisionsCount - 1);
			revision.setData(file.getContents());
			revision.setContentType(file.getContentType());
			revision.setFileName(file.getFileName());
		}
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

}
