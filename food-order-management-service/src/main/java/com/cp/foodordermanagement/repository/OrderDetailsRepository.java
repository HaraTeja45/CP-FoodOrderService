package com.cp.foodordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cp.foodordermanagement.model.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

	public List<OrderDetails> findByOrderIdAndIsActive(Long orderIdLong, Integer isactive);

	public List<OrderDetails> findByOrderedByAndIsActive(Long orderedBy, Integer isactive);

}
