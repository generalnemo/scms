package org.scms.enumerate.citem;

public enum CItemOperationType {

	VERSION_CREATION("Создание версии"), SAVING("Сохранение изменений"), READINESS_CALCULATION(
			"Обновление готовности"), STATE_CALCULATION("Обновление статуса");

	private String label;

	private CItemOperationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
