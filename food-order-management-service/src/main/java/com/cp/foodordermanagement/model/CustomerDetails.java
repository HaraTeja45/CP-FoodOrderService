package com.cp.foodordermanagement.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CustomerDetails {

	@Id
	private Long customerId;

	private String customerName;

	private String mobileNumber;

	private String dob;

	private Long subscriptionKey;

	private Integer isactive;

	@CreationTimestamp
	private Timestamp createdDate;

	@UpdateTimestamp
	private Timestamp lstUpdatedime;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public Integer getIsactive() {
		return isactive;
	}

	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLstUpdatedime() {
		return lstUpdatedime;
	}

	public void setLstUpdatedime(Timestamp lstUpdatedime) {
		this.lstUpdatedime = lstUpdatedime;
	}

	public Long getSubscriptionKey() {
		return subscriptionKey;
	}

	public void setSubscriptionKey(Long subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}

}
