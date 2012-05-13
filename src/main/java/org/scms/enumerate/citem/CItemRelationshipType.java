package org.scms.enumerate.citem;

public enum CItemRelationshipType {

	IS_INPUT_TO("Является входом для"), IS_OUTPUT_FOR("Является выходом для"), IS_PARENT_FOR(
			"Является родительским элементом для");

	private String label;

	private CItemRelationshipType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
