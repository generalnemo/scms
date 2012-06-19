package org.scms.enumerate.citem;

public enum CItemType {

	TASK("Работа"), DOCUMENT("Документ"), CHANGE_REQUEST("Запрос на изменение"), PROJECT(
			"Проект");

	private String label;

	private CItemType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public boolean isTask() {
		return this == TASK;
	}

	public boolean isDocument() {
		return this == DOCUMENT;
	}

}
