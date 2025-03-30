package com.cp.foodordermanagement.helper;

import org.springframework.web.client.RestTemplate;

public class RestTemplateInstance {

	private static RestTemplate restTemplate = null;

	public static RestTemplate getRestTemplateInstance() {

		if (restTemplate == null) {
			restTemplate = new RestTemplate();
		}

		return restTemplate;

	}

}
