package com.patrycja.springsecurity.auth.repository;

import com.patrycja.springsecurity.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
