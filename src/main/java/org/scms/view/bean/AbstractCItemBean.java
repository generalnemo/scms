package org.scms.view.bean;

import java.io.Serializable;

import javax.inject.Inject;

import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
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
	}

	@Override
	protected AbstractCRUDService<CItem> getService() {
		return cItemService;
	}

}
