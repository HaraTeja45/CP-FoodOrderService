package com.cp.foodordermanagement.helper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum MapperFactory {
	INSTANCE;

	private final ObjectMapper objectMapper;

	MapperFactory() {
		objectMapper = new ObjectMapper();

		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public ObjectMapper getInstance() {
		return objectMapper;
	}
}
