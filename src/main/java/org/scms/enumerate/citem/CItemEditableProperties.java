package org.scms.enumerate.citem;

public enum CItemEditableProperties {

	NAME("Название объекта"), DESCRIPTION("Описание объекта"), CONTROLLER(
			"Контролер"), CURATOR("Куратор"), PERFORMER("Исполнитель"), RESOURCE_MANAGER(
			"Владелец ресурсов"), LABORIOUSNESS("Трудоемкость"), START_PROCESS_DATE(
			"Дата начала работ"), END_PROCESS_DATE(
			"Дата окончания работ"), DIFFICULTY(
			"Коэффициент сложности");

	private String label;

	private CItemEditableProperties(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
