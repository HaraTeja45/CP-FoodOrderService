package com.cp.foodordermanagement.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.CreateIndexResponse;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

public class OpenSearchService {

	@Value("${restaurant.index.json.mapping}")
	private String mappingFileName;

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public boolean createIndexIfNotExists(String indexName) {

		try {
			GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);

			boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);

			if (!exists) {
				ClassPathResource resource = new ClassPathResource(mappingFileName);

				String indexJsonMapping = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

				CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName).source(indexJsonMapping,
						XContentType.JSON);

				CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest,
						RequestOptions.DEFAULT);

				return createIndexResponse.isAcknowledged();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}
}
