package com.patrycja.springsecurity.product.controller;

import com.patrycja.springsecurity.product.model.Coupon;
import com.patrycja.springsecurity.product.model.Product;
import com.patrycja.springsecurity.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${coupon.service.url}")
    private String couponServiceUrl;

    @GetMapping("/showCreateProduct")
    public String showCreateProduct(){
        return "createProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product){
        if(product.getCouponCode() != null && !product.getCouponCode().isEmpty()){
            // todo
            Coupon coupon = restTemplate.getForObject(couponServiceUrl + product.getCouponCode(), Coupon.class);
            product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        }
         productRepository.save(product);
        return "createResponse";
    }

    @GetMapping("/showGetProduct")
    public String showGetProduct(){
        return "getProduct";
    }

    @PostMapping("/getProduct")
    public ModelAndView getProduct(Long id){
        ModelAndView mav = new ModelAndView("productDetails");
        mav.addObject(productRepository.findById(id).get());
        return mav;
    }

}
