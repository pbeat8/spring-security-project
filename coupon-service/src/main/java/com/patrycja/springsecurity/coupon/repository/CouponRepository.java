package com.patrycja.springsecurity.coupon.repository;

import com.patrycja.springsecurity.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCode(String code);
}
