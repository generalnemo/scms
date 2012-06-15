package org.scms.view.bean.task;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.service.filter.CItemSearchFilter;
import org.scms.view.bean.AbstractCatalogBean;

@Named("userTaskCatalog")
@ViewScoped
public class UserTaskCatalogBean extends AbstractCatalogBean<CItem>{


	private static final long serialVersionUID = 7637635609593529609L;

	@PostConstruct
	protected void init() {
		filter = new CItemSearchFilter();
		filter.setType(CItemType.TASK);
		objects = cItemService.execute(filter);
	}
	
}
