package com.cp.foodordermanagement.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class RestaurantDetails {

	@Id
	private Long restaurantKey;

	private String restaurantName;

	private String restaurantDescription;

	private LocalTime openTime;

	private LocalTime closeTime;

	@JoinColumn(name = "menuKey", referencedColumnName = "menuKey", nullable = false)
	private MenuDetails menuKey;

	private String restaurantRating;

	private String branchName;

	private Integer isActive;

	public Long getRestaurantKey() {
		return restaurantKey;
	}

	public void setRestaurantKey(Long restaurantKey) {
		this.restaurantKey = restaurantKey;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getRestaurantDescription() {
		return restaurantDescription;
	}

	public void setRestaurantDescription(String restaurantDescription) {
		this.restaurantDescription = restaurantDescription;
	}

	public MenuDetails getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(MenuDetails menuKey) {
		this.menuKey = menuKey;
	}

	public String getRestaurantRating() {
		return restaurantRating;
	}

	public void setRestaurantRating(String restaurantRating) {
		this.restaurantRating = restaurantRating;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

}
