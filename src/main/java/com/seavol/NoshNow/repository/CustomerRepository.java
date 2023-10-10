package com.seavol.NoshNow.repository;

import com.seavol.NoshNow.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByMobileNo(String mobileNo);
}
