package com.cp.foodordermanagement.helper;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

public class FOMHelper {

	private RestTemplate restTemplate;

	@PostConstruct
	public void intit() {

		restTemplate = RestTemplateInstance.getRestTemplateInstance();
	}

	public ResponseEntity<?> executeRestCall(String url, HttpMethod httpMethod, Class<?> responseType,
			Map<String, String> params, String requestJSON, HttpHeaders httpHeaders) {

		httpHeaders.add("Content-Type", "application/json");

		HttpEntity<Object> httpEntity = null;

		String responseJSON = null;

		ResponseEntity<?> responseEntity = null;

		try {
			httpEntity = new HttpEntity<>(requestJSON, httpHeaders);

			if (params == null) {
				responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, responseType);
			} else {
				responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, responseType, params);
			}

			if (responseEntity != null && responseEntity.getBody() != null)
				responseJSON = responseEntity.getBody().toString();

			return new ResponseEntity<>(responseJSON, responseEntity.getHeaders(), responseEntity.getStatusCode());
		} catch (RestClientException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

}
