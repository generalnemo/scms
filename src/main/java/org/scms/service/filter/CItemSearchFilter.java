package org.scms.service.filter;

import org.scms.enumerate.citem.CItemType;

public class CItemSearchFilter extends AbstractSearchFilter {

	protected CItemType type;

	protected String objectName;

	protected Long revisionNum;

	public CItemType getType() {
		return type;
	}

	public void setType(CItemType type) {
		this.type = type;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Long getRevisionNum() {
		return revisionNum;
	}

	public void setRevisionNum(Long revisionNum) {
		this.revisionNum = revisionNum;
	}

}
