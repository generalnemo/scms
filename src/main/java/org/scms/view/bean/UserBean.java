package org.scms.view.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.scms.model.entity.User;
import org.scms.service.UserService;

@Named("user")
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 3979771106094044331L;
	
	@Inject
	private ExternalContext external;
	@Inject
	private UserService userService;

	private User currentUser = null;

	@PostConstruct
	private void init() {
		if (getRequest().getRemoteUser() != null) {
			currentUser = userService.findById(getRequest().getRemoteUser());
		}
	}

	public String logout() throws ServletException {
		getRequest().logout();
		setCurrentUser(null);
		return "/index.xhtml?faces-redirect=true";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) external.getRequest();
	}

	public User getCurrentUser() {
		if (currentUser == null && getRequest().getRemoteUser() != null) {
			currentUser = userService.findById(getRequest().getRemoteUser());
		}
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
