package com.cp.foodordermanagement.bean;

import java.time.LocalTime;
import java.util.List;

import org.opensearch.common.geo.GeoPoint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestaurantMappingBean {

	@JsonProperty("restaurantid")
	private String restaurantId;

	@JsonProperty("restaurantname")
	private String restaurantName;
	@JsonProperty("restaurantlocation")
	private GeoPoint restaurantLocation;

	@JsonProperty("rating")
	private Float rating;

	@JsonProperty("opentime")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime openTime;

	@JsonProperty("closetime")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime closeTime;

	@JsonProperty("isactive")
	private Integer isActive;

	@JsonProperty("cuisines")
	private List<CuisineMappingBean> cuisines;

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public GeoPoint getRestaurantLocation() {
		return restaurantLocation;
	}

	public void setRestaurantLocation(GeoPoint restaurantLocation) {
		this.restaurantLocation = restaurantLocation;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public LocalTime getOpenTime() {
		return openTime;
	}

	public void setOpenTime(LocalTime openTime) {
		this.openTime = openTime;
	}

	public LocalTime getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(LocalTime closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public List<CuisineMappingBean> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<CuisineMappingBean> cuisines) {
		this.cuisines = cuisines;
	}

}
