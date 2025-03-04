package com.cp.foodordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.foodordermanagement.model.MenuDetails;

@Repository
public interface MenuDetailsRepository extends JpaRepository<MenuDetails, Long> {

	public MenuDetails findByMenuKeyAndIsActive(Long menuKey, Integer isactive);
}
