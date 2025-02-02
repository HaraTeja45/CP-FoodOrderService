package com.cp.foodordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.bean.PartnerRestaurantRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.service.RestaurantManagementService;

@RestController
public class PartnerRestaurantController {

	@Autowired
	private RestaurantManagementService restaurantManagementService;

	@PostMapping("v1/food/register/restaurant")
	public ResponseEntity<?> registerRestaurant(
			@RequestBody PartnerRestaurantRequestBean partnerRestaurantRequestBean) {

		ResponseBean responseBean = restaurantManagementService.registerRestaurant(partnerRestaurantRequestBean);

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

	@PutMapping("v1/food/update/restaurant")
	public ResponseEntity<?> updateRestaurant(
			@RequestBody  PartnerRestaurantRequestBean partnerRestaurantRequestBean) {

		ResponseBean responseBean = restaurantManagementService.updateRestaurant(partnerRestaurantRequestBean);

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

}
