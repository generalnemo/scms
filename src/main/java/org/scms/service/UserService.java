package org.scms.service;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.scms.model.entity.User;

@Stateless
public class UserService implements Serializable {

	private static final long serialVersionUID = -7092614744590025030L;

	@Inject
	private EntityManager em;

	public User findById(String userLoginName) {
		return em.find(User.class, userLoginName);
	}

}
