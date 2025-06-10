package com.cp.foodordermanagement.service;

import com.cp.foodordermanagement.bean.RestaurantMappingBean;

public interface OpenSearchRestaurantService {
	
	public String indexRestaurant(RestaurantMappingBean restaurantMappingBean);
	
	
	public String getRestaurantByName(String queryParam);
	
	
	public String searchByFullText(String searchText,Double userLatitude,Double userLongitude);

}
