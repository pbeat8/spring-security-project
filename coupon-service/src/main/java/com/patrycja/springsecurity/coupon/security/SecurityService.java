package com.patrycja.springsecurity.coupon.security;

public interface SecurityService {

    boolean login(String username, String password);

}
