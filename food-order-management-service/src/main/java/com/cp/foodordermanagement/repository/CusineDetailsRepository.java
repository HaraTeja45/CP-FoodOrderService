package com.cp.foodordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cp.foodordermanagement.model.CusineDetails;

public interface CusineDetailsRepository extends JpaRepository<CusineDetails, Long> {

	public List<CusineDetails> findByCusineNameAndIsActive(String cusineName, Integer isactive);

	public List<CusineDetails> findByCusineKeyInAndIsActive(List<Long> cusineKeys, Integer isactive);

	public CusineDetails findByCusineKeyAndIsActive(Long cusineKey, Integer isactive);
}
