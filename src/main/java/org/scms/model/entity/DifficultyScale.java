package org.scms.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "difficulty_scale")
public class DifficultyScale extends AbstractIdentityModel {

	private static final long serialVersionUID = 8540754352993937837L;

	@Column(name = "scale_name")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "scale", fetch = FetchType.LAZY)
	private List<DifficultyScaleCoeff> coeffs;

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
