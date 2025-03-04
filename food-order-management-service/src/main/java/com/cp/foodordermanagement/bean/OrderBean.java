package com.cp.foodordermanagement.bean;

import com.cp.foodordermanagement.customannotation.NotNull;

public class OrderBean {

	@NotNull
	private String restaurantName;

	@NotNull
	private String restaurantBranchName;

	@NotNull
	private String cusineName;

	private String paymentStatus;

	private String price;

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getCusineName() {
		return cusineName;
	}

	public void setCusineName(String cusineName) {
		this.cusineName = cusineName;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRestaurantBranchName() {
		return restaurantBranchName;
	}

	public void setRestaurantBranchName(String restaurantBranchName) {
		this.restaurantBranchName = restaurantBranchName;
	}

}
