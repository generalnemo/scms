package org.scms.view.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;
import org.scms.model.entity.AbstractTemporalModel;
import org.scms.model.entity.User;
import org.scms.model.exception.EntityAlreadyExistsException;
import org.scms.service.CRUDService;
import org.scms.service.UserService;

public abstract class AbstractObjectBean<T extends AbstractTemporalModel>
		implements Serializable {

	private static final long serialVersionUID = 6075393377754021363L;

	protected static final String PRETTY_CATALOG = "pretty:catalog";

	protected static final String PRETTY_ADD = "pretty:add";

	protected static final String PRETTY_VIEW = "pretty:view";

	protected static final String PRETTY_EDIT = "pretty:edit";

	protected Map<String, String> properties = new HashMap<String, String>();

	@Inject
	protected Logger logger;
	
	@Inject
	protected UserService userService;
	
	@Inject
	protected FacesContext fContext;

	protected List<User> userList;
	
	protected T object;

	protected abstract void pageLoad();

	protected abstract CRUDService<T> getService();

	protected void init() {
		userList = userService.findAllUsers();
	}

	public String addObject() {
		try {
			getService().add(object);
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO,
					"Информация добавлена в реестр", null));
			return getProperty(PRETTY_VIEW);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			for (Throwable a = e.getCause(); a != null; a = a.getCause()) {
				if (a.getClass().getName()
						.contains("ConstraintViolationException")) {
					fContext.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN,
							"Объект уже существует в системе", null));
					return null;
				}
			}
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Объект не удалось добавить",
					null));
		}
		return null;
	}

	public String removeObject() {
		try {
			getService().remove(object);
			return getProperty(PRETTY_CATALOG);
		} catch (Exception e) {
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Ошибка при удалении", null));
		}
		return null;
	}

	public void saveObject() {
		try {
			getService().update(object);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Информация обновлена", null);
			fContext.addMessage(null, message);
		} catch (EntityAlreadyExistsException e) {
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Объект уже существует", null));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			fContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Объект не удалось сохранить",
					null));
		}
	}

	protected String getProperty(String key) {
		return properties.get(key);
	}

	protected void addProperty(String key, String value) {
		properties.put(key, value);
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}