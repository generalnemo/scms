package org.scms.view.bean.task;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.scms.enumerate.citem.CItemType;
import org.scms.view.bean.AbstractCItemBean;

@Named("userTask")
@ViewScoped
public class UserTaskBean extends AbstractCItemBean {

	private static final long serialVersionUID = 8132779611590184248L;

	@Override
	@PostConstruct
	public void init() {
		super.init();
		type = CItemType.TASK;
		addProperty(PRETTY_CATALOG, "pretty:tasksCatalog");
		addProperty(PRETTY_ADD, "pretty:addTask");
		addProperty(PRETTY_EDIT, "pretty:editTask");
		addProperty(PRETTY_VIEW, "pretty:viewTask");
	}
}
