package com.cp.foodordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.foodordermanagement.model.CusineCategory;

public interface CusineCategoryRepository extends JpaRepository<CusineCategory, Long> {

	public CusineCategory findByCusineCatCodeAndIsActive(String cusineCatCode, Integer isActive);

}
