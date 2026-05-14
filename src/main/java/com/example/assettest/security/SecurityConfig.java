package com.example.assettest.security;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security configuration that should be included as related config for
 * authentication and authorization inference.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/v1/orders/public/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .antMatchers("/api/v1/orders/**").authenticated()
            .antMatchers("/legacy/report/*").hasRole("AUDITOR")
            .and()
            .httpBasic();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        return new ShiroFilterFactoryBean();
    }
}
