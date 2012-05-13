package org.scms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "difficulty_scale_coeff")
public class DifficultyScaleCoeff extends AbstractTemporalModel {

	private static final long serialVersionUID = -3295789803255491514L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "scale_id")
	private DifficultyScale scale;

	@Column(name = "coeff_value", nullable = false)
	private double value;

	@Override
	public Object getPrimaryKey() {
		return id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
