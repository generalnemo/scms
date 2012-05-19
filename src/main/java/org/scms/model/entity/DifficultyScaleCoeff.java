package org.scms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "difficulty_scale_coeff")
public class DifficultyScaleCoeff extends AbstractIdentityModel {

	private static final long serialVersionUID = -3295789803255491514L;

	@ManyToOne
	@JoinColumn(name = "scale_id")
	private DifficultyScale scale;

	@Column(name = "coeff_value", nullable = false)
	private double value;

	public DifficultyScale getScale() {
		return scale;
	}

	public void setScale(DifficultyScale scale) {
		this.scale = scale;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
