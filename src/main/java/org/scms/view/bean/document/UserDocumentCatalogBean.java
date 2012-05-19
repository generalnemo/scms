package org.scms.view.bean.document;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.service.filter.CItemSearchFilter;
import org.scms.view.bean.AbstractCatalogBean;

@Named("userDocumentCatalog")
@ViewScoped
public class UserDocumentCatalogBean extends AbstractCatalogBean<CItem> {

	private static final long serialVersionUID = 8247899590922071759L;

	@PostConstruct
	protected void init() {
		filter = new CItemSearchFilter();
		filter.setType(CItemType.DOCUMENT);
		objects = cItemService.execute(filter);
	}

}