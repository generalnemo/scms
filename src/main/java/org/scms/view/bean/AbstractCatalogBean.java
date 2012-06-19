package org.scms.view.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.model.entity.AbstractIdentityModel;
import org.scms.model.entity.User;
import org.scms.service.CItemService;
import org.scms.service.UserService;
import org.scms.service.filter.CItemSearchFilter;

public abstract class AbstractCatalogBean<T extends AbstractIdentityModel>
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

	protected List<User> userList;

	protected Date currentDate;
	
	protected List<CItemControlCategory> categories;

	@PostConstruct
	protected void init() {
		filter = new CItemSearchFilter();
		userList = userService.findAllUsers();
	}

	public void clearFilter() {
		filter = new CItemSearchFilter();
	}

	public abstract void findByFilter();

	public CItemSearchFilter getFilter() {
		return filter;
	}

	public List<T> getObjects() {
		return objects;
	}

	public List<User> getUserList() {
		return userList;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public List<CItemControlCategory> getCategories() {
		return categories;
	}
}
