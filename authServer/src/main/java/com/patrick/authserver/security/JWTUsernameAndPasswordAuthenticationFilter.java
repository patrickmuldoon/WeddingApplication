package com.patrick.authserver.security;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrick.authserver.auth.beans.CurrentUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authManager;
	
	private final JWTConfig jwtConfig;

	public JWTUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, JWTConfig jwtConfig) {
		this.authManager = authManager;
		this.jwtConfig = jwtConfig;
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			
			// 1. Get credentials from request
			UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
			// 2. Create auth object (contains credentials) which will be used by auth manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), Collections.emptyList());
			// 3. Authentication manager authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
			return authManager.authenticate(authToken);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Upon successful authentication, generate a token.
	// The 'auth' passed to successfulAuthentication() is the current authenticated user.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		CurrentUser user = (CurrentUser)auth.getPrincipal();
		Map<String, Object> claimsMap = constructClaims(user);
		Long now = System.currentTimeMillis();
		String token = Jwts.builder()
			.setSubject(auth.getName())	
			// Convert to list of strings. 
			// This is important because it affects the way we get them back in the Gateway.
			.addClaims(claimsMap)
			.setIssuedAt(new Date(now))
			.setExpiration(new Date(now + jwtConfig.getExpiration() * 1000))  // in milliseconds
			.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
			.compact();
		// Add token to header
		response.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
	}
	
	// A (temporary) class just to represent the user credentials
	private static class UserCredentials {
	    private String username, password;
	    
	    public String getUsername() {
			return username;
		}
	    
	    public void setUsername(String username) {
			this.username = username;
		}
	    public String getPassword() {
			return password;
		}
	    public void setPassword(String password) {
			this.password = password;
		}
	}
	
	private Map<String, Object> constructClaims(CurrentUser user){
		Map<String, Object> claimsMap = new HashMap<String, Object>();
		claimsMap.put("authorities", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		claimsMap.put("email", user.getEmail());
		claimsMap.put("userId", user.getId());
		claimsMap.put("userProfile", user.getUsersProfile());
		return claimsMap;
	}
	
}
