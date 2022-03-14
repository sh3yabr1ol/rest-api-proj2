package com.cwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
        //HTTP Basic authentication
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/create").hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/update/*").hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/delete/*").hasRole("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/updatePartial/*").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/all").permitAll()
        .and()
        .csrf().disable();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
        .withUser("user").password("{noop}user").roles("USER")
        .and()
        .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");

	}
}
