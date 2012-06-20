package org.scms.view.bean;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import org.jboss.solder.logging.Logger;
import org.scms.model.entity.User;
import org.scms.service.UserService;

@Named("auth")
@RequestScoped
public class AuthentificationBean implements Serializable {

	private static final long serialVersionUID = -1507636405956932787L;
	private static final Logger logger = Logger
			.getLogger(AuthentificationBean.class);

	public static final String AUTH_KEY = "scms.login";

	private String login;
	private String password;

	@Inject
	private UserService service;
	
	@Inject
	private UserBean userBean;

	public boolean isLoggedIn() {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(AUTH_KEY) != null;
	}

	public String loginAction() {
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md5 = null;
			byte messageDigest[] = null;
			try {
				md5 = MessageDigest.getInstance("md5");
				md5.reset();
				md5.update(password.getBytes());
				messageDigest = md5.digest();
				
				for (int i=0;i<messageDigest.length;i++) {
		    		String hex=Integer.toHexString(0xff & messageDigest[i]);
		   	     	if(hex.length()==1) hexString.append('0');
		   	     	hexString.append(hex);
		    	}
				logger.info(hexString.toString());
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage());
			}
			User user = service.findUserByLoginAndPassword(login,
					hexString.toString());
			if (user != null) {
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap().put(AUTH_KEY, login);
				userBean.setCurrentUser(user);
				return "/index.jsf?faces-redirect=true";
			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Указанной комбинации имени пользователя и пароля не существует",
										null));
			}
		} catch (Exception e) {
			logger.error("Can't find user '" + login + "'");
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									"Указанной комбинации имени пользователя и пароля не существует",
									null));
		}
		return null;
	}

	public void logout() throws ServletException {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove(AUTH_KEY);
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
