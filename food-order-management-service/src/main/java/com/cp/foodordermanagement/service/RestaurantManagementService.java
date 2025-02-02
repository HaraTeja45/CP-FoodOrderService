package com.cp.foodordermanagement.service;

import com.cp.foodordermanagement.bean.PartnerRestaurantRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;

public interface RestaurantManagementService {

	public ResponseBean registerRestaurant(PartnerRestaurantRequestBean partnerRestaurantRequestBean);

	public ResponseBean updateRestaurant(PartnerRestaurantRequestBean partnerRestaurantRequestBean);

}
