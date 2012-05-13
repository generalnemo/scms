package org.scms.enumerate.citem;

public enum CItemState {

	CREATED("Создан"), ACCEPTED("Принят"), PROGRESS("В процессе"), REVIEW(
			"На проверке"), COMPLETED("Выполнен"), CLOSED("Закрыт"), DELAYED(
			"Отложен");

	private String label;

	CItemState(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
