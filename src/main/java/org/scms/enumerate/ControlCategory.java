package org.scms.enumerate;

public enum ControlCategory {

	CC1("КК1","КК1: Идентификация, хранение, просмотр и хранение изменений (все), версионность"), 
	CC2("КК2","КК2: Идентификация, хранение, просмотр и хранение изменений (только атрибуты)"), 
	CC3("КК3","КК3: Идентификация, хранение (задание/изменение основных атрибутов)"), 
	CC4("КК4","КК4: Идентификация, хранение (без задания/изменения основных атрибутов)");

	private String label;

	private String description;

	private ControlCategory(String label, String description) {
		this.label = label;
		this.description = description;
	}

	public boolean isCc1() {
		return this == CC1;
	}

	public boolean isCc2() {
		return this == CC2;
	}

	public boolean isCc3() {
		return this == CC3;
	}

	public boolean isCc4() {
		return this == CC4;
	}

	public String getLabel() {
		return label;
	}

	public String getDescription() {
		return description;
	}

}
