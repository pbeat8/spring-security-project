package com.patrycja.springsecurity.product.repository;

import com.patrycja.springsecurity.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
