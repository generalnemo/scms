package org.scms.enumerate.citem;

public enum CItemRelationshipType {

	IS_INPUT_TO("Является входом для"), IS_OUTPUT_FOR("Является выходом для"), IS_PARENT_FOR(
			"Является родительским элементом для");

	private String label;

	private CItemRelationshipType(String label) {
		this.label = label;
	}

	public boolean isInputTo() {
		return this == IS_INPUT_TO;
	}

	public boolean isOutputFor() {
		return this == IS_OUTPUT_FOR;
	}

	public boolean isParentFor() {
		return this == IS_PARENT_FOR;
	}

	public String getLabel() {
		return label;
	}

}
