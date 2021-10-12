package com.patrycja.springsecurity.auth.repository;

import com.patrycja.springsecurity.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
