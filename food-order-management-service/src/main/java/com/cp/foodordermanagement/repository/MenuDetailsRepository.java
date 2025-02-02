package com.cp.foodordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.foodordermanagement.model.MenuDetails;

public interface MenuDetailsRepository  extends JpaRepository<MenuDetails, Long>{
	

}
