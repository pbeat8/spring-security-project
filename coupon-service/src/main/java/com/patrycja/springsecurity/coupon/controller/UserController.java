package com.patrycja.springsecurity.coupon.controller;

import com.patrycja.springsecurity.coupon.model.Role;
import com.patrycja.springsecurity.coupon.model.User;
import com.patrycja.springsecurity.coupon.repository.RoleRepository;
import com.patrycja.springsecurity.coupon.repository.UserRepository;
import com.patrycja.springsecurity.coupon.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/showReg")
    public String showRegistrationPage(){
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role_user = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(role_user));
        userRepository.save(user);
        return "login";
    }

    // custom login page
    @GetMapping("/")
    public String showLoginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String email, String password){
        boolean loginResponse = securityService.login(email, password);
        if(loginResponse){
            return "index";
        }
        return "login";
    }
}
