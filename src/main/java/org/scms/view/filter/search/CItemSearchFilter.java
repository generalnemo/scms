package org.scms.view.filter.search;

import org.scms.enumerate.citem.CItemType;

public class CItemSearchFilter {
	
	private String userName;
	
	private CItemType type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public CItemType getType() {
		return type;
	}

	public void setType(CItemType type) {
		this.type = type;
	}

}
