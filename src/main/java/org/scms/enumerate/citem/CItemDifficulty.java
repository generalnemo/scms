package org.scms.enumerate.citem;

public enum CItemDifficulty {

	LOW(0.7), STANDARD(1), HARD(1.3);

	private double difficultyCoeffValue;

	CItemDifficulty(double difficultyCoeffValue) {
		this.difficultyCoeffValue = difficultyCoeffValue;
	}

	public double getDifficultyCoeffValue() {
		return difficultyCoeffValue;
	}

	public void setDifficultyCoeffValue(double difficultyCoeffValue) {
		this.difficultyCoeffValue = difficultyCoeffValue;
	}
}
