package com.patrycja.springsecurity.coupon.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "couponservice";

//    @Value("${publicKey}")
//    private String publicKey;

    // added for symmetric key
    @Value("${signingKey}")
    private String signingKey;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId(RESOURCE_ID);
        // added for symmetric key
        resources.resourceId(RESOURCE_ID).tokenStore(tokenStore());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/coupons/{code:^[A-Z0-9]*$}").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/coupons").hasRole("ADMIN")
                .anyRequest().denyAll()
                .and().csrf().disable();
    }

    // Now this is useless, bd we have the public key endpoint in application.resources
    // and spring will now enable the default tokenStore and jwtAccessTokenConverter,
    // we don't need to write them ourselves
//    @Bean
//    public TokenStore tokenStore(){
//        return new JwtTokenStore(jwtAccessTokenConverter());
//    }
//
//    // Now the resourceServer no longer needs to communicate with authServer, is uses the public key to validate the token
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter(){
//        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setVerifierKey(publicKey);
//        return jwtAccessTokenConverter;
//    }

    // added for symmetric key
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }
}
