package org.scms.view.bean.document;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.scms.model.entity.CItem;
import org.scms.service.CItemService;
import org.scms.service.CRUDService;
import org.scms.view.bean.AbstractObjectBean;

public class UserDocumentBean extends AbstractObjectBean<CItem> {

	private static final long serialVersionUID = -5533974832781202095L;

	@Inject
	protected CItemService cItemService; 
	
	@Override
	@PostConstruct
	public void init() {
		super.init();
		addProperty(PRETTY_CATALOG, "pretty:documentsCatalog");
		addProperty(PRETTY_ADD, "pretty:addDocument");
		addProperty(PRETTY_EDIT, "pretty:editDocument");
		addProperty(PRETTY_VIEW, "pretty:viewDocument");
	}

	@Override
	protected void pageLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	protected CRUDService<CItem> getService() {
		return cItemService;
	}

}
