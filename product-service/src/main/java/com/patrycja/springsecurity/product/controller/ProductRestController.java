package com.patrycja.springsecurity.product.controller;

import com.patrycja.springsecurity.product.model.Coupon;
import com.patrycja.springsecurity.product.model.Product;
import com.patrycja.springsecurity.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${coupon.service.url}")
    private String couponServiceUrl;

    @PostMapping
    public Product create(@RequestBody Product product){
        if(product.getCouponCode() != null && !product.getCouponCode().isEmpty()){
            Coupon coupon = restTemplate.getForObject(couponServiceUrl + product.getCouponCode(), Coupon.class);
            product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        }
        return repository.save(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        return repository.findById(id).orElse(null);
    }

}
