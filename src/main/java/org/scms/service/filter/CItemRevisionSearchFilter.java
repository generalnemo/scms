package org.scms.service.filter;

import org.scms.enumerate.citem.CItemType;

public class CItemRevisionSearchFilter extends AbstractSearchFilter {

	private Long cItemId;

	private String cItemName;

	private CItemType type;

	public Long getcItemId() {
		return cItemId;
	}

	public void setcItemId(Long cItemId) {
		this.cItemId = cItemId;
	}

	public String getcItemName() {
		return cItemName;
	}

	public void setcItemName(String cItemName) {
		this.cItemName = cItemName;
	}

	public CItemType getType() {
		return type;
	}

	public void setType(CItemType type) {
		this.type = type;
	}

}
