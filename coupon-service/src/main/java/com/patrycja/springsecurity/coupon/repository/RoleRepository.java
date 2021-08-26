package com.patrycja.springsecurity.coupon.repository;

import com.patrycja.springsecurity.coupon.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
