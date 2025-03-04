package com.cp.foodordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.foodordermanagement.model.RestaurantDetails;

public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Long> {

	public RestaurantDetails findByRestaurantNameAndBranchNameAndIsActive(String restaurantName, String branch,
			Integer isActive);

	public List<RestaurantDetails> findByRestaurantKeyInAndIsActive(List<Long> restaurantKeys, Integer isactive);

	public RestaurantDetails findByRestaurantKeyAndIsActive(Long restaurantKey, Integer isactive);
}
