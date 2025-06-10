package com.cp.foodordermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cp.foodordermanagement.service.OpenSearchService;

@RestController
public class OpenSearchController {

	@Autowired
	private OpenSearchService openSearchService;

	@GetMapping("checkIndex/{indexName}")
	public ResponseEntity<?> checkIndexPresentOrNot(@PathVariable String indexName) {

		boolean indexIfNotExists = openSearchService.createIndexIfNotExists(indexName);

		return new ResponseEntity<>(indexIfNotExists, HttpStatus.OK);
	}

}
