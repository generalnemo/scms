package org.scms.view.bean.document;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.view.bean.AbstractCatalogBean;

@Named("userDocumentCatalog")
@ViewScoped
public class UserDocumentCatalogBean extends AbstractCatalogBean<CItem> {

	private static final long serialVersionUID = 8247899590922071759L;

	@PostConstruct
	protected void init() {
		super.init();
		filter.setType(CItemType.DOCUMENT);
		categories = Arrays.asList(CItemControlCategory.CC1,
				CItemControlCategory.CC4);
		objects = cItemService.execute(filter);
	}

	@Override
	public void findByFilter() {
		objects = cItemService.execute(filter);
	}

}
