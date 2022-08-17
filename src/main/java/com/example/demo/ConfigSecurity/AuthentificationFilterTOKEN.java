package com.example.demo.ConfigSecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class AuthentificationFilterTOKEN  extends UsernamePasswordAuthenticationFilter{//ici login
	private AuthenticationManager AuthenticationManager;
	
	private AuthenticationManager authenticationManager;

	public AuthentificationFilterTOKEN(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}
	//comme AuthentificationFilterTOKEN authentificationFilterTOKEN = new AuthentificationFilterTOKEN(authenticationManager());
	
	
	
	
	
	 @Override
	    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        log.info("Username is: {}", username); log.info("Password is: {}", password);
	        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
	        return authenticationManager.authenticate(authenticationToken);
	    }

	    @Override
	    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
	        User user = (User)authentication.getPrincipal();
	        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	        List<String> roles = new ArrayList<>();
			
	        user.getAuthorities().forEach(au -> {
				roles.add(au.getAuthority());
			});
	        String refresh_token = JWT.create()
	                .withSubject(user.getUsername())
	                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
	                .withIssuer(request.getRequestURL().toString())
	                .sign(algorithm);
	       

			String userco=authentication.getName();
		
			response.addHeader("Authorization", refresh_token);
			response.addHeader("userconnected", userco);
	    }
	}
