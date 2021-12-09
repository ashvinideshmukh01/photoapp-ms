package com.photoapp.userms.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoapp.userms.service.UserService;
import com.photoapp.userms.shared.UserDTO;
import com.photoapp.userms.ui.model.LoginUserReqModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private Environment env;
	private UserService userService;
	
	
	@Autowired
	public AuthenticationFilter(UserService userService,Environment env,AuthenticationManager authmanager) {
		super();
		this.userService = userService;
		this.env=env;
		super.setAuthenticationManager(authmanager);
	}

	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginUserReqModel readValue;
		try {
			 readValue = new ObjectMapper().readValue(request.getInputStream(), LoginUserReqModel.class);
			 return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(readValue.getEmail(),readValue.getPassword(),new ArrayList<>()));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  RuntimeException();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new  RuntimeException();
		}
		
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username = ((User)authResult.getPrincipal()).getUsername();
		UserDTO userByEmail = userService.getUserByEmail(username);
		String token = Jwts.builder()
		    .setSubject(userByEmail.getUserId())
		    .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token-expiration-time"))))
		    .signWith(SignatureAlgorithm.HS512, env.getProperty("token-secret"))
		    .compact();
		response.addHeader("token", token);
		response.addHeader("userId", userByEmail.getUserId());
	}
	
}
