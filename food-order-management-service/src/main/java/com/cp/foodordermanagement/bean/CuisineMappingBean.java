package com.cp.foodordermanagement.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CuisineMappingBean {

	@JsonProperty("cusinename")
	private String cuisineName;

	@JsonProperty("cusinerating")
	private Float cuisineRating;

	@JsonProperty("cusine_isactive")
	private Integer cuisineIsActive;

	public String getCuisineName() {
		return cuisineName;
	}

	public void setCuisineName(String cuisineName) {
		this.cuisineName = cuisineName;
	}

	public Float getCuisineRating() {
		return cuisineRating;
	}

	public void setCuisineRating(Float cuisineRating) {
		this.cuisineRating = cuisineRating;
	}

	public Integer getCuisineIsActive() {
		return cuisineIsActive;
	}

	public void setCuisineIsActive(Integer cuisineIsActive) {
		this.cuisineIsActive = cuisineIsActive;
	}

}
