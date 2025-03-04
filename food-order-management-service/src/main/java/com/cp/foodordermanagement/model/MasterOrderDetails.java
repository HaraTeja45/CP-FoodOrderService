package com.cp.foodordermanagement.model;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "masterorderdetails",schema = "public")
public class MasterOrderDetails {

	@Id
	private Long masterOrderId;

	@OneToMany(mappedBy = "masterOrderDetails",cascade = CascadeType.ALL)
	private List<OrderDetails> orderDetails;

	private String orderStatus;

	private Integer isactive;

	private Timestamp lstUpdatedTime;

	private Timestamp createdTime;

	public Long getMasterOrderId() {
		return masterOrderId;
	}

	public void setMasterOrderId(Long masterOrderId) {
		this.masterOrderId = masterOrderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getIsactive() {
		return isactive;
	}

	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}

	public Timestamp getLstUpdatedTime() {
		return lstUpdatedTime;
	}

	public void setLstUpdatedTime(Timestamp lstUpdatedTime) {
		this.lstUpdatedTime = lstUpdatedTime;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

}
