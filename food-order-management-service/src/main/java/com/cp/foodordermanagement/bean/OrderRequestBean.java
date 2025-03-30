package com.cp.foodordermanagement.bean;

import java.time.chrono.IsoChronology;
import java.util.List;

public class OrderRequestBean {

	private Long customerId;

	private String paymentMode;

	private Integer rewardsPoints;

	private List<OrderBean> orderBeans;

	public List<OrderBean> getOrderBeans() {
		return orderBeans;
	}

	public void setOrderBeans(List<OrderBean> orderBeans) {
		this.orderBeans = orderBeans;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Integer getRewardsPoints() {
		return rewardsPoints;
	}

	public void setRewardsPoints(Integer rewardsPoints) {
		this.rewardsPoints = rewardsPoints;
	}
	
	

}
