package com.example.config;

import com.example.hanlder.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration

@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Autowired

   CustomSuccessHandler customSuccessHandler;

   @Autowired
   public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication().withUser("bill").password("{noop}abc123").roles("USER");
      auth.inMemoryAuthentication().withUser("admin").password("{noop}root123").roles("ADMIN");
      auth.inMemoryAuthentication().withUser("dba").password("{noop}root123").roles("ADMIN","DBA");
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
     http.authorizeRequests()
      .antMatchers("/", "/home").access("hasRole('USER')")
      .antMatchers("/admin/**").access("hasRole('ADMIN')")
      .antMatchers("/dba/**").access("hasRole('ADMIN') and hasRole('DBA')")
      .and().formLogin().successHandler(customSuccessHandler)
      .usernameParameter("ssoId").passwordParameter("password")
      .and().csrf()
      .and().exceptionHandling().accessDeniedPage("/Access_Denied");
   }
}