package com.cp.foodordermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.*") 
public class FoodOrderManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderManagementServiceApplication.class, args);
	}

}
