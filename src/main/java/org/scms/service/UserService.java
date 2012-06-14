package org.scms.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.scms.enumerate.UserGroup;
import org.scms.model.entity.User;

@Stateless
public class UserService implements Serializable {

	private static final long serialVersionUID = -7092614744590025030L;

	private List<User> users;

	@Inject
	private EntityManager em;

	public User findById(String userLoginName) {
		return em.find(User.class, userLoginName);
	}

	public List<User> findByGroup(UserGroup group) {
		return em.createNamedQuery(User.FIND_BY_GROUP, User.class)
				.setParameter("group", group).getResultList();
	}

	public List<User> findByPartFIO(String name, UserGroup group) {
		return findUsersByPartNameAndGroup(name, name, name, group);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		if (users == null)
			users = em.createNamedQuery(User.FIND_ALL).getResultList();
		return users;
	}

	public List<User> findUsersByPartNameAndGroup(String lastName,
			String firstName, String middleName, UserGroup group) {
		TypedQuery<User> query = em.createNamedQuery(
				User.FIND_BY_PART_NAME_AND_GROUP, User.class);
		query.setParameter("firstName", (firstName.isEmpty()) ? "%%" : ("%"
				+ firstName + "%"));
		query.setParameter("lastName", (firstName.isEmpty()) ? "%%" : ("%"
				+ lastName + "%"));
		query.setParameter("middleName", (firstName.isEmpty()) ? "%%" : ("%"
				+ middleName + "%"));
		query.setParameter("group", group);
		return query.getResultList();
	}

	public User findUserByLoginAndPassword(String login, String password) {
		try {
			TypedQuery<User> query = em
					.createQuery(
							"SELECT u FROM User u WHERE u.userLoginName = :login AND u.password = :passwd",
							User.class);
			query.setParameter("login", login);
			query.setParameter("passwd", password);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<User> getUsers() {
		return users;
	}

}
