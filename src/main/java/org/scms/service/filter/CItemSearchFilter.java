package org.scms.service.filter;

import org.scms.enumerate.citem.CItemControlCategory;
import org.scms.enumerate.citem.CItemType;

public class CItemSearchFilter extends AbstractSearchFilter {

	protected CItemType type;

	protected String objectName;

	protected Long revisionNum;
	
	protected String curator;
	
	protected String controller;
	
	protected String resourceManager;
	
	protected String performer;
	
	protected CItemControlCategory category;

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

	public String getCurator() {
		return curator;
	}

	public void setCurator(String curator) {
		this.curator = curator;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(String resourceManager) {
		this.resourceManager = resourceManager;
	}

	public String getPerformer() {
		return performer;
	}

	public void setPerformer(String performer) {
		this.performer = performer;
	}

	public CItemControlCategory getCategory() {
		return category;
	}

	public void setCategory(CItemControlCategory category) {
		this.category = category;
	}

}
