package com.cp.foodordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.bean.ErrorBean;
import com.cp.foodordermanagement.bean.OrderRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.customexception.FoodOrderManagementServiceException;
import com.cp.foodordermanagement.service.OrderService;
import com.logging.CommonLoggingUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class OrderController {

	private static final String CLASS_NAME = OrderController.class.getCanonicalName();

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonLoggingUtil logger;

	@Operation(description = "Food Order Management Service", responses = {
			@ApiResponse(responseCode = "200", description = "Order placed  Successfully", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :200," + "\"Status\" :\"OK\","
							+ "\"Message\" :\"Successfully placed\"}") })),
			@ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :400," + "\"Status\" :\"BAD REQUEST\","
							+ "\"Message\" :\"Data Insufficient\"}") })) })
	@CrossOrigin
	@PostMapping("/v1/placeorder")
	public ResponseEntity<ResponseBean> placeOrder(@RequestBody OrderRequestBean orderRequestBean) {

		logger.debug(CLASS_NAME, "Controller", "inside placeOrder");

		ResponseBean responseBean = new ResponseBean();
		try {
			responseBean = orderService.placeOrder(orderRequestBean);
		} catch (FoodOrderManagementServiceException e) {

			logger.error(CLASS_NAME, "Controller", "error occured in placeOrder");
			ErrorBean errorBean = new ErrorBean();
			errorBean.setErrorCode(e.getErrorCode());
			errorBean.setErrorMessage(e.getMessage());
			responseBean.setErrorBean(errorBean);

			return new ResponseEntity<ResponseBean>(responseBean,
					HttpStatusCode.valueOf(Integer.valueOf(e.getErrorCode())));
		}
		logger.debug(CLASS_NAME, "Controller", "end placeOrder");
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@Operation(description = "Food Order Management Service", responses = {
			@ApiResponse(responseCode = "200", description = "Order fetched Successfully", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :200," + "\"Status\" :\"OK\","
							+ "\"Message\" :\"Successfully fetched order details\"}") })),
			@ApiResponse(responseCode = "400", description = "Bad Request!", content = @Content(mediaType = "application/json", examples = {
					@ExampleObject(value = "{\"Code\" :400," + "\"Status\" :\"BAD REQUEST\","
							+ "\"Message\" :\"Data Insufficient\"}") })) })
	@CrossOrigin
	@GetMapping("v1/getOrders")
	public ResponseEntity<ResponseBean> getOrderDetails(@RequestParam String customerId) {
		ResponseBean responseBean = new ResponseBean();

		logger.debug(CLASS_NAME, "Controller", "inside getOrderDetails for customer id:" + customerId);

		try {
			responseBean = orderService.fetchOrderDetails(customerId);
		} catch (FoodOrderManagementServiceException e) {

			logger.error(CLASS_NAME, "Controller", "error occured in getOrderDetails");
			ErrorBean errorBean = new ErrorBean();
			errorBean.setErrorCode(e.getErrorCode());
			errorBean.setErrorMessage(e.getMessage());
			responseBean.setErrorBean(errorBean);

			return new ResponseEntity<ResponseBean>(responseBean,
					HttpStatusCode.valueOf(Integer.valueOf(e.getErrorCode())));
		}

		logger.debug(CLASS_NAME, "Controller", "end getOrderDetails for customer id:" + customerId);

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

}
