package com.cp.foodordermanagement.service;

import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class MyServiceImpl implements MyService{

	@Override
	@CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallBackMethod")
	public  String faultyMethod() {

		if (Math.random() > 0.7) {
			throw new RuntimeException(" Exception occured");

		}

		return "Success";
	}

	public String fallBackMethod(Throwable throwable) {
		
		System.out.println("ERROR:---"+throwable.getMessage());

		return "fall back! Exception occured";

	}

}
