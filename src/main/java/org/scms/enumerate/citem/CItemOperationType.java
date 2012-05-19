package org.scms.enumerate.citem;

public enum CItemOperationType {

	CREATION("Создание"), SAVING("Сохранение изменений"), READINESS_CALCULATION(
			"Обновление готовности"), STATE_CALCULATION("Обновление статуса"), VERSION_CREATION(
			"Создание версии");

	private String label;

	private CItemOperationType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
