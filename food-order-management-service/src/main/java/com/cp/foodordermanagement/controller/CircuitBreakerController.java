package com.cp.foodordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.service.MyService;

@RestController
public class CircuitBreakerController {

	@Autowired
	private MyService myService;

	@GetMapping("test")
	public ResponseEntity<String> getPaymentDets() {

		return new ResponseEntity<>(myService. faultyMethod(), HttpStatus.OK);
	}

}
