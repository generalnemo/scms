package org.scms.view.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.scms.model.entity.AbstractTemporalModel;
import org.scms.service.CItemService;
import org.scms.service.UserService;
import org.scms.service.filter.CItemSearchFilter;

public abstract class AbstractCatalogBean<T extends AbstractTemporalModel>
		implements Serializable {

	private static final long serialVersionUID = 442757784131722328L;

	@Inject
	protected CItemService cItemService;

	@Inject
	protected UserService userService;
	
	@Inject
	protected UserBean userBean;

	protected CItemSearchFilter filter;

	protected List<T> objects;

	@PostConstruct
	protected void init() {
		filter = new CItemSearchFilter();
	}

	public void clearFilter() {
		filter = new CItemSearchFilter();
	}

	public CItemSearchFilter getFilter() {
		return filter;
	}

	public List<T> getObjects() {
		return objects;
	}
}
