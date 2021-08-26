package com.patrycja.springsecurity.coupon.controller;

import com.patrycja.springsecurity.coupon.model.Coupon;
import com.patrycja.springsecurity.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @GetMapping("/showCreateCoupon")
    public String showCreateCoupon(){
        return "createCoupon";
    }

    @PostMapping("/saveCoupon")
    public String save(Coupon coupon){
        couponRepository.save(coupon);
        return "createResponse";
    }

    @GetMapping("/showGetCoupon")
    public String showGetCoupon(){
        return "getCoupon";
    }

    @PostMapping("/getCoupon")
    public ModelAndView getCoupon(String code){
        ModelAndView mav = new ModelAndView("couponDetails");
        mav.addObject(couponRepository.findByCode(code));
        return mav;
    }
}
