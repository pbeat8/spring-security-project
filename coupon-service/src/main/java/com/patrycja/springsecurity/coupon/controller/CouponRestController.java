package com.patrycja.springsecurity.coupon.controller;

import com.patrycja.springsecurity.coupon.model.Coupon;
import com.patrycja.springsecurity.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
public class CouponRestController {

    @Autowired
    CouponRepository repository;

    @PostMapping
    public Coupon create(@RequestBody Coupon coupon){
        return repository.save(coupon);
    }

    @GetMapping("/{code}")
    public Coupon getCoupon(@PathVariable String code){
        return repository.findByCode(code);
    }
}
