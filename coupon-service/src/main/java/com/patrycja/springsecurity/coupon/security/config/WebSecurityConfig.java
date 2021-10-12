package com.patrycja.springsecurity.coupon.security.config;

import com.patrycja.springsecurity.coupon.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

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
        // authorisation methods
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/coupons/{code:^[A-Z0-9]*$}", "/index",
                         "/showGetCoupon","/getCoupon", "/couponDetails").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/api/coupons", "/saveCoupon").hasRole("ADMIN")
                .mvcMatchers("/","/login", "/showReg", "/registerUser").permitAll()
                .anyRequest().denyAll() // will deny any request that are not matching exactly with those specified above
                .and().logout().logoutSuccessUrl("/");
        http.csrf(csrfCustomizer -> {
            csrfCustomizer.ignoringAntMatchers("/api/coupons/**");
            RequestMatcher requestMatcher = new RegexRequestMatcher("/api/coupons/{code:^[A-Z0-9]*$}", "POST");
            requestMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/getCoupon");
            csrfCustomizer.ignoringRequestMatchers(requestMatcher);
        });
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
