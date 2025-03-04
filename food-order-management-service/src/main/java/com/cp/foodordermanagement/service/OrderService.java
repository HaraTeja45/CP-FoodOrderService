package com.cp.foodordermanagement.service;

import com.cp.foodordermanagement.bean.OrderRequestBean;
import com.cp.foodordermanagement.bean.ResponseBean;

public interface OrderService {
	
	public ResponseBean fetchOrderDetails(String customerId);
	
	
	public ResponseBean placeOrder(OrderRequestBean orderRequestBean);
	

}
