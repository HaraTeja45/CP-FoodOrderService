package com.cp.foodordermanagement.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;


@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI createOpenApi() {
		return new OpenAPI().info(new Info().title("Food Order Mangement Service Swagger").version("1.0.0")
				.description("Endpoints related to Food Order Mangement Service")
				.contact(new Contact().name("Yadam Venkata Hara Teja").email("harateja2002@gmail.com")));

	}

	@Bean
	public GroupedOpenApi createGroupedOpenApi() {
		return GroupedOpenApi.builder().group("Food Order Mangement Service Swagger")
				.packagesToScan("com.cp.foodordermanagement.controller").build();

	}

}
