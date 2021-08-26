package com.patrycja.springsecurity.coupon.security.config;

import com.patrycja.springsecurity.coupon.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authentication method
        http.httpBasic();
        // authorisation methods
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/coupons/{code:^[A-Z0-9]*$}").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/coupons").hasRole("ADMIN")
                .anyRequest().denyAll() // will deny any request that are not matching exactly with those specified above
                .and().csrf().disable(); // to allow post requests from product service
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
