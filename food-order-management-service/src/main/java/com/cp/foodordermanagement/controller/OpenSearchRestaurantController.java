package com.cp.foodordermanagement.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.bean.ResponseBean;
import com.cp.foodordermanagement.bean.RestaurantMappingBean;
import com.cp.foodordermanagement.service.OpenSearchRestaurantService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class OpenSearchRestaurantController {

	@Autowired
	private OpenSearchRestaurantService openSearchRestaurantService;

	@PostMapping("/index/restaurant")
	public ResponseEntity<?> indexRestaurant(@RequestBody RestaurantMappingBean restaurantMappingBean) {

		String response = openSearchRestaurantService.indexRestaurant(restaurantMappingBean);

		ResponseBean responseBean = new ResponseBean();

		responseBean.setPayload(response);
		responseBean.setStatus("Success");

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchByRestaurantName(@RequestParam String restaurantName) {

		String searchResult = openSearchRestaurantService.getRestaurantByName(restaurantName);

		ResponseBean responseBean = new ResponseBean();

		responseBean.setPayload(searchResult);
		responseBean.setStatus("Success");

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

	@GetMapping("/search/FullText")
	public ResponseEntity<?> searchFullText(@RequestParam String searchText, @RequestParam Double userLatitude,
			@RequestParam Double userLongitutde) {

		String searchByFullText = openSearchRestaurantService.searchByFullText(searchText, userLatitude,
				userLongitutde);

		ResponseBean responseBean = new ResponseBean();
		responseBean.setPayload(searchByFullText);
		responseBean.setStatus("Success");

		return new ResponseEntity<>(responseBean, HttpStatus.OK);
	}

}
