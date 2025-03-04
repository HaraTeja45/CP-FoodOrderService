package com.cp.foodordermanagement.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orderdetails",schema = "public")
public class OrderDetails {

	@Id
	private Long orderId;

	private Long restaurantKey;

	private Long cusineKey;

	private Long orderedBy;

	@ManyToOne
	@JoinColumn(name = "masterOrderId", nullable = false)
	private MasterOrderDetails masterOrderDetails;

	private String status;

	private Integer isActive;

	private BigDecimal price;

	private Timestamp orderCreateddateTime;

	private Timestamp lstUpdatedDateTime;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getRestaurantKey() {
		return restaurantKey;
	}

	public void setRestaurantKey(Long restaurantKey) {
		this.restaurantKey = restaurantKey;
	}

	public Long getCusineKey() {
		return cusineKey;
	}

	public void setCusineKey(Long cusineKey) {
		this.cusineKey = cusineKey;
	}

	public Long getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(Long orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Timestamp getOrderCreateddateTime() {
		return orderCreateddateTime;
	}

	public void setOrderCreateddateTime(Timestamp orderCreateddateTime) {
		this.orderCreateddateTime = orderCreateddateTime;
	}

	public Timestamp getLstUpdatedDateTime() {
		return lstUpdatedDateTime;
	}

	public void setLstUpdatedDateTime(Timestamp lstUpdatedDateTime) {
		this.lstUpdatedDateTime = lstUpdatedDateTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public MasterOrderDetails getMasterOrderDetails() {
		return masterOrderDetails;
	}

	public void setMasterOrderDetails(MasterOrderDetails masterOrderDetails) {
		this.masterOrderDetails = masterOrderDetails;
	}
	
	

}
