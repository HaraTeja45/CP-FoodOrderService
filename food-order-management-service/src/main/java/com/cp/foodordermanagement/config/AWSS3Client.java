package com.cp.foodordermanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSS3Client {

	@Value("${aws.s3.bucketAccesskey}")
	private String s3BucketAccessKey;

	@Value("${aws.s3.bucketSecretKey}")
	private String s3BucketSecretKey;

	@Value("${aws.s3.bucketname}")
	private String s3BucketName;



	@Bean
	S3Client s3Client() {
		return S3Client.builder().region(Region.AP_SOUTH_1).credentialsProvider(
				StaticCredentialsProvider.create(AwsBasicCredentials.create(s3BucketAccessKey, s3BucketSecretKey)))
				.build();
	}
}
