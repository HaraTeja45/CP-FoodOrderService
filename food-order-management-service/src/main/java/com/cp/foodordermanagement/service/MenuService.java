package com.cp.foodordermanagement.service;

import org.springframework.web.multipart.MultipartFile;

public interface MenuService {

	
	public String uploadMenuFile(MultipartFile menu);
	
	public String fetchMenuFromS3(String objectKey);
}
