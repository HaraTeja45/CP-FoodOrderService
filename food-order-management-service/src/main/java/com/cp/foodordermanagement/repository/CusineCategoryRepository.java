package com.cp.foodordermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.foodordermanagement.model.CusineCategory;

@Repository
public interface CusineCategoryRepository extends JpaRepository<CusineCategory, Long> {

	public CusineCategory findByCusineCatCodeAndIsActive(String cusineCatCode, Integer isActive);

}
