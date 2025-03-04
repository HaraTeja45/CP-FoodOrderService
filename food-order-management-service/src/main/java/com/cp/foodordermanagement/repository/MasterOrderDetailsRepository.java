package com.cp.foodordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.foodordermanagement.model.MasterOrderDetails;

@Repository
public interface MasterOrderDetailsRepository extends JpaRepository<MasterOrderDetails, Long> {

	public MasterOrderDetails findByMasterOrderIdAndIsactive(Long masterOrderId, Integer isActive);

	public List<MasterOrderDetails> findByMasterOrderIdInAndIsactive(List<Long> masterOrderId, Integer isactive);

}
