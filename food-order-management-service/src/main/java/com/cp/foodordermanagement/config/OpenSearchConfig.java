package com.cp.foodordermanagement.config;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.ssl.SSLContextBuilder;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {

	@Value("${opensearch.admin.username:admin}")
	private String username;

	@Value("${opensearch.admin.password:HaraTeja%1965}")
	private String password;

	@Bean("customRestHighLevelClient")
	RestHighLevelClient restHighLevelClient() {

		BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
		basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
		return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "https"))
				.setHttpClientConfigCallback(
						httpClientBuilder -> 
				
				{
					try {
						httpClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
						return httpClientBuilder
								.setSSLContext(SSLContextBuilder.create()
										.loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
								.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
					} catch (Exception e) {
						throw new RuntimeException("Failed to configure SSL", e);
					}
				}
				
						));
	}
}
