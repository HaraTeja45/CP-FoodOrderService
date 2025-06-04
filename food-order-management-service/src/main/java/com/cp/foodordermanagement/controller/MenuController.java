package com.cp.foodordermanagement.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cp.foodordermanagement.service.MenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MenuController {

	@Autowired
	private MenuService menuService;

	@PostMapping("v1/upload/file")
	public ResponseEntity<String> uploadMenu(@RequestBody MultipartFile menu) {

		String uploadMenuFile = menuService.uploadMenuFile(menu);

		return new ResponseEntity<>(uploadMenuFile, HttpStatus.OK);
	}

	@GetMapping("v1/fetch/file")
	public ResponseEntity<String> fetchMenuFromS3(@RequestParam String s3ObjectKey) {

		return new ResponseEntity<>(menuService.fetchMenuFromS3(s3ObjectKey), HttpStatus.OK);
	}

}
