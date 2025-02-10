package com.cp.foodordermanagement.bean;

import java.util.List;

import com.cp.foodordermanagement.customannotation.NotNull;

public class PartnerRestaurantRequestBean {

	@NotNull
	private String restaurantName;

	private String restaurantDescription;

	private String restaurantOpenTime;

	private String restaurantCloseTime;
	@NotNull
	private String branch;

	private String openDays;

	private String status;

	@NotNull
	private PartnerMenuRequestBean partnerMenuRequestBean;
	@NotNull
	private List<PartnerCusineRequestBean> partnerCusineRequestBeans;

	public PartnerMenuRequestBean getPartnerMenuRequestBean() {
		return partnerMenuRequestBean;
	}

	public void setPartnerMenuRequestBean(PartnerMenuRequestBean partnerMenuRequestBean) {
		this.partnerMenuRequestBean = partnerMenuRequestBean;
	}

	public List<PartnerCusineRequestBean> getPartnerCusineRequestBeans() {
		return partnerCusineRequestBeans;
	}

	public void setPartnerCusineRequestBeans(List<PartnerCusineRequestBean> partnerCusineRequestBeans) {
		this.partnerCusineRequestBeans = partnerCusineRequestBeans;
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

	public String getRestaurantOpenTime() {
		return restaurantOpenTime;
	}

	public void setRestaurantOpenTime(String restaurantOpenTime) {
		this.restaurantOpenTime = restaurantOpenTime;
	}

	public String getRestaurantCloseTime() {
		return restaurantCloseTime;
	}

	public void setRestaurantCloseTime(String restaurantCloseTime) {
		this.restaurantCloseTime = restaurantCloseTime;
	}

	public String getOpenDays() {
		return openDays;
	}

	public void setOpenDays(String openDays) {
		this.openDays = openDays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

}
