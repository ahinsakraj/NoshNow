package com.seavol.NoshNow.repository;

import com.seavol.NoshNow.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

}
