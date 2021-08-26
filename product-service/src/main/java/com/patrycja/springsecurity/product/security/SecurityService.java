package com.patrycja.springsecurity.product.security;

public interface SecurityService {

    boolean login(String username, String password);

}
