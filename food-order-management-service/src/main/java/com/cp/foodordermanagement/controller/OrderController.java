package com.cp.foodordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.bean.ErrorBean;
import com.cp.foodordermanagement.bean.OrderRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.customexception.FoodOrderManagementServiceException;
import com.cp.foodordermanagement.service.OrderService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/v1/placeorder")
	public ResponseEntity<ResponseBean> placeOrder(@RequestBody OrderRequestBean orderRequestBean) {

		ResponseBean responseBean = new ResponseBean();
		try {
			responseBean = orderService.placeOrder(orderRequestBean);
		} catch (FoodOrderManagementServiceException e) {
			ErrorBean errorBean = new ErrorBean();
			errorBean.setErrorCode("500");
			errorBean.setErrorMessage("Technical error occured");
			responseBean.setErrorBean(errorBean);
		}
		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

	@GetMapping("v1/getOrders")
	public ResponseEntity<ResponseBean> getMethodName(@RequestParam String customerId) {
		ResponseBean responseBean = new ResponseBean();

		try {
			responseBean = orderService.fetchOrderDetails(customerId);
		} catch (Exception e) {
			ErrorBean errorBean = new ErrorBean();
			errorBean.setErrorCode("500");
			errorBean.setErrorMessage("Technical error occured");
			responseBean.setErrorBean(errorBean);
		}

		return new ResponseEntity<ResponseBean>(responseBean, HttpStatus.OK);
	}

}
