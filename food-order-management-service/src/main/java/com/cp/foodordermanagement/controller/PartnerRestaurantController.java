package com.cp.foodordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.bean.ErrorBean;
import com.cp.foodordermanagement.bean.PartnerRestaurantRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.customannotation.NotNull;
import com.cp.foodordermanagement.customexception.FoodOrderManagementServiceException;
import com.cp.foodordermanagement.service.RestaurantManagementService;
import com.logging.CommonLoggingUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class PartnerRestaurantController {

	private static final String CLASS_NAME = PartnerRestaurantController.class.getCanonicalName();

	@Autowired
	private RestaurantManagementService restaurantManagementService;

	@Autowired
	private CommonLoggingUtil logger;

	@Operation(description = "Food Order Management Service", responses = {
			@ApiResponse(responseCode = "200", description = "Restaurant registered Successfully", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :200," + "\"Status\" :\"OK\","
							+ "\"Message\" :\"Successfully registered\"}") })),
			@ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :400," + "\"Status\" :\"BAD REQUEST\","
							+ "\"Message\" :\"Data Insufficient\"}") })) })
	@CrossOrigin
	@PostMapping("v1/food/register/restaurant")
	public ResponseEntity<?> registerRestaurant(
			@RequestBody PartnerRestaurantRequestBean partnerRestaurantRequestBean) {

		logger.debug(CLASS_NAME, "Controller", "inside registerRestaurant");

		ResponseBean responseBean = new ResponseBean();
		try {
			responseBean = restaurantManagementService.registerRestaurant(partnerRestaurantRequestBean);
		} catch (FoodOrderManagementServiceException e) {

			logger.error(CLASS_NAME, "Controller", "error occured in registerRestaurant");

			ErrorBean errorBean = new ErrorBean();
			errorBean.setErrorCode(e.getErrorCode());
			errorBean.setErrorMessage(e.getMessage());
			responseBean.setErrorBean(errorBean);
			return new ResponseEntity<ResponseBean>(responseBean,
					HttpStatusCode.valueOf(Integer.valueOf(e.getErrorCode())));
		}
		logger.debug(CLASS_NAME, "Controller", "end registerRestaurant");
		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

	@Operation(description = "Food Order Management Service", responses = {
			@ApiResponse(responseCode = "200", description = "Restaurants updated Successfully", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :200," + "\"Status\" :\"OK\","
							+ "\"Message\" :\"Successfully updated\"}") })),
			@ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :400," + "\"Status\" :\"BAD REQUEST\","
							+ "\"Message\" :\"Data Insufficient\"}") })) })
	@CrossOrigin
	@PutMapping("v1/food/update/restaurant")
	public ResponseEntity<?> updateRestaurant(
			@RequestBody @NotNull PartnerRestaurantRequestBean partnerRestaurantRequestBean) {

		logger.debug(CLASS_NAME, "Controller", "inside updateRestaurant");

		ResponseBean responseBean = new ResponseBean();
		try {
			responseBean = restaurantManagementService.updateRestaurant(partnerRestaurantRequestBean);
		} catch (FoodOrderManagementServiceException e) {

			logger.error(CLASS_NAME, "Controller", "error occured in updateRestaurant");

			ErrorBean errorBean = new ErrorBean();
			errorBean.setErrorCode(e.getErrorCode());
			errorBean.setErrorMessage(e.getMessage());
			responseBean.setErrorBean(errorBean);

			return new ResponseEntity<ResponseBean>(responseBean,
					HttpStatusCode.valueOf(Integer.valueOf(e.getErrorCode())));
		}

		logger.debug(CLASS_NAME, "Controller", "end updateRestaurant");

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

}
