package org.scms.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "difficulty_scale")
public class DifficultyScale extends AbstractTemporalModel {

	private static final long serialVersionUID = 8540754352993937837L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "scale_name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scale", fetch = FetchType.LAZY)
	private List<DifficultyScaleCoeff> coeffs;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DifficultyScaleCoeff> getCoeffs() {
		return coeffs;
	}

	public void setCoeffs(List<DifficultyScaleCoeff> coeffs) {
		this.coeffs = coeffs;
	}

}
