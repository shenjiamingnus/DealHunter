package com.nus.dealhunter.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@TestConfiguration
@EnableAutoConfiguration
@Import({SecurityConfig.class})
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("**/**")
        .permitAll()
        .anyRequest()
        .authenticated();
  }
}
