package org.scms.view.bean.task;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemType;
import org.scms.model.entity.CItem;
import org.scms.view.bean.AbstractCatalogBean;

@Named("userTaskCatalog")
@ViewScoped
public class UserTaskCatalogBean extends AbstractCatalogBean<CItem>{


	private static final long serialVersionUID = 7637635609593529609L;

	@PostConstruct
	protected void init() {
		super.init();
		filter.setType(CItemType.TASK);
		categories = Arrays.asList(CItemControlCategory.CC2,
				CItemControlCategory.CC3);
		objects = cItemService.execute(filter);
	}

	@Override
	public void findByFilter() {
		objects = cItemService.execute(filter);
	}
	
}
