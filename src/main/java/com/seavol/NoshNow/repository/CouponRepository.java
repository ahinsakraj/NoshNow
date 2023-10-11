package com.seavol.NoshNow.repository;

import com.seavol.NoshNow.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    Coupon findByName(String name);
}
