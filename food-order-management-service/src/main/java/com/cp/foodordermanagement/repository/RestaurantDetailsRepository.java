package com.cp.foodordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.foodordermanagement.model.RestaurantDetails;

public interface RestaurantDetailsRepository extends JpaRepository<RestaurantDetails, Long> {

	RestaurantDetails findByRestaurantNameAndBranchNameAndIsActive(String restaurantName,String branch,Integer isActive);

}
