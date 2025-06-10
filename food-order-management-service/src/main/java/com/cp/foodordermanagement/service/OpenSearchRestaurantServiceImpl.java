package com.cp.foodordermanagement.service;

import java.io.IOException;

import org.json.JSONObject;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.action.search.SearchRequest;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.opensearch.index.query.BoolQueryBuilder;
import org.opensearch.index.query.MatchQueryBuilder;
import org.opensearch.index.query.QueryBuilders;
import org.opensearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cp.foodordermanagement.bean.RestaurantMappingBean;
import com.cp.foodordermanagement.helper.MapperFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OpenSearchRestaurantServiceImpl implements OpenSearchRestaurantService {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	private ObjectMapper objectMapper = MapperFactory.INSTANCE.getInstance();

	@Value("${restaurat.index.name}")
	private String restaurantIndexName;

	@Override
	public String indexRestaurant(RestaurantMappingBean restaurantMappingBean) {

		try {
			String stringMappingBean = objectMapper.writeValueAsString(restaurantMappingBean);

			IndexRequest indexRequest = new IndexRequest().index(restaurantIndexName)
					.source(stringMappingBean,XContentType.JSON);

			IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

			if (indexResponse != null) {

				return "Success";
			} else {

				return "Failed";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Success";
	}

	@Override
	public String getRestaurantByName(String queryParam) {

		try {

			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
					.query(QueryBuilders.matchQuery("restaurantname", queryParam));

			SearchRequest searchRequest = new SearchRequest(restaurantIndexName).source(searchSourceBuilder);

			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

			if (searchResponse == null) {

				return "Failure";
			} else {

				return searchResponse.toString();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String searchByFullText(String searchText, Double userLatitude, Double userLongitude) {

		try {
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
					.must(QueryBuilders.multiMatchQuery(searchText, "restaurantname", "cusinename"))
					.filter(QueryBuilders.geoDistanceQuery("restaurantlocation").point(userLatitude, userLongitude)
							.distance("2km"));

			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(boolQueryBuilder);

			SearchRequest searchRequest = new SearchRequest().source(searchSourceBuilder);

			SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

			if (search != null) {

				return search.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
