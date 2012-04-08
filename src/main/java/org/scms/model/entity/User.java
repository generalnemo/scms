package org.scms.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.scms.enumerate.UserGroup;

@Entity
@Table(name = "t_user")
@SecondaryTable(name = "user_group", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_name"))
@NamedQueries({
		@NamedQuery(name = User.FIND_ALL, query = "SELECT u from User u"),
		@NamedQuery(name = User.FIND_BY_GROUP, query = "SELECT u from User u WHERE group = :group") })
public class User implements Serializable {

	private static final long serialVersionUID = -8059489547682362958L;

	public static final String FIND_ALL = "User.findAllUsers";
	public static final String FIND_BY_GROUP = "User.findByGroup";

	@Id
	@Column(name = "user_name")
	private String userLoginName;

	@Column(name = "passwd", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "group_name", table = "user_group")
	private UserGroup group;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "middle_name", nullable = false)
	private String middleName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	public String getFullName() {
		StringBuilder full = new StringBuilder();
		if (!lastName.isEmpty()) {
			full.append(lastName).append(' ');
		}
		if (!firstName.isEmpty()) {
			full.append(firstName).append(' ');
		}
		if (!firstName.isEmpty()) {
			full.append(middleName);
		}
		return full.toString().trim();
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

}
