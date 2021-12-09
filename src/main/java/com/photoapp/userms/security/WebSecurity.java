package com.photoapp.userms.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.photoapp.userms.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private Environment env;
	private UserService userservice;
	private BCryptPasswordEncoder bcBCryptPasswordEncoder;
	
	@Autowired
	public WebSecurity(Environment env,UserService userservice, BCryptPasswordEncoder bcBCryptPasswordEncoder) {
		super();
		this.userservice = userservice;
		this.bcBCryptPasswordEncoder = bcBCryptPasswordEncoder;
		this.env=env;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/users**").permitAll()
				.and()
		.addFilter(getAuthenticationFilter());
		http.authorizeRequests().antMatchers("/users").permitAll();

		http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
	}

	private Filter getAuthenticationFilter() throws Exception {
		// TODO Auto-generated method stub
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userservice,env,authenticationManager());
		// authenticationFilter.setAuthenticationManager(authenticationManager());;
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		 return authenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService( userservice).passwordEncoder(bcBCryptPasswordEncoder); 	
	}
	
	
	

}
