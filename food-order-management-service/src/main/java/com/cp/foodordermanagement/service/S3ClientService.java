package com.cp.foodordermanagement.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cp.foodordermanagement.config.CircuitBreakerFactory;

import jakarta.validation.Valid;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.InvalidObjectStateException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3ClientService {

	@Autowired
	S3Client awss3Client;

	@Value("${aws.s3.bucketname}")
	private String s3BucketName;

	@Value("${file.destination}")
	private String fileDestination;

	public String uploadFile(MultipartFile multipartFile) {
		try {
			String key = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(s3BucketName).key(key)
					.contentType(multipartFile.getContentType()).build();

			PutObjectResponse putObjectResponse = awss3Client.putObject(putObjectRequest,
					RequestBody.fromBytes(multipartFile.getBytes()));

			if (putObjectResponse.sdkHttpResponse().isSuccessful()) {
				return "File Uploaded successfully";

			} else {

				throw new RuntimeException("Failed to upload document");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;
	}

	public String getS3Object(String s3ObjectKey) {

		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(s3BucketName).key(s3ObjectKey).build();

		try {
			ResponseInputStream<GetObjectResponse> responseInputStream = awss3Client.getObject(getObjectRequest);

			try (InputStream inputStream = responseInputStream;
					OutputStream outputStream = new FileOutputStream(fileDestination)) {

				byte[] buffer = new byte[8192]; // 8 KB buffer size
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			return "File downloaded successfully to: " + fileDestination;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
