package com.patrycja.springsecurity.product.repository;

import com.patrycja.springsecurity.product.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
