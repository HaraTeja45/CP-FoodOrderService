package com.cp.foodordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	public S3ClientService s3ClientService;

	@Override
	public String uploadMenuFile(MultipartFile menu) {

		String uploadFile = s3ClientService.uploadFile(menu);

		return uploadFile;
	}

	@Override
	public String fetchMenuFromS3(String objectKey) {

		return s3ClientService.getS3Object(objectKey);
	}

}
