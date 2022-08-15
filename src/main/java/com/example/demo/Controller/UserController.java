package com.example.demo.Controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;

import com.example.demo.Services.UserService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.SocketException;
import java.net.URI;
import java.util.*;

import com.example.demo.Entity.*;

//@RestController
//@CrossOrigin(origins = "http://localhost:4200/")


@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Transactional
public class UserController {
	@Autowired UserService userService;
	
	
	
	//@GetMapping("/users")
	//****************
	 @GetMapping("/users")
	    public ResponseEntity<List<User>>getUsers() {
	        return ResponseEntity.ok().body(userService.getAllUsers());
	    }
/*
	@GetMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
	   
	public List<User> getAll() {
		return userService.getAllUsers();
	}*/
	
	
	@GetMapping("/getUserName")
    public User getAll(String NameUser){
        return userService.getUserByName(NameUser);
    }
	
	@PostMapping("/addRole")
	public Role addRole(@RequestBody Role role){
		return userService.addRole(role);
	}
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user){
		return userService.addUser(user);
	}
	//*************************************
	@PostMapping( "/addUsera")

	public User register (@RequestBody User user) {
			

		return userService.register(user);
	}	
	
	
	
	@PostMapping("/addRoleToUser/{NameUser}/{NameRole}")
	public void addRoleToUser(@PathVariable("NameUser") String username,@PathVariable("NameRole") String rolename){
		userService.addRoleToUser(username,rolename);
	}
	 @GetMapping("/token/refresh")
	    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        String authorizationHeader = request.getHeader(AUTHORIZATION);
	        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            try {
	                String refresh_token = authorizationHeader.substring("Bearer ".length());
	                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	                JWTVerifier verifier = JWT.require(algorithm).build();
	                DecodedJWT decodedJWT = verifier.verify(refresh_token);
	                String username = decodedJWT.getSubject();
	                User user = userService.getUserByName(username);
	                String access_token = JWT.create()
	                        .withSubject(user.getUserName())
	                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
	                        .withIssuer(request.getRequestURL().toString())
	                        .withClaim("roles", user.getRoles().stream().map(Role::getRole).collect(Collectors.toList()))
	                        .sign(algorithm);
	                Map<String, String> tokens = new HashMap<>();
	                tokens.put("access_token", access_token);
	                tokens.put("refresh_token", refresh_token);
	                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
	            }catch (Exception exception) {
	                response.setHeader("error", exception.getMessage());
	                response.setStatus(FORBIDDEN.value());
	                //response.sendError(FORBIDDEN.value());
	                Map<String, String> error = new HashMap<>();
	                error.put("error_message", exception.getMessage());
	                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
	                new ObjectMapper().writeValue(response.getOutputStream(), error);
	            }
	        } else {
	            throw new RuntimeException("Refresh token is missing");
	        }
	    }
	 
	 
	 @GetMapping(value = "/isEmailExist")
		public Boolean isEmailExist(@RequestParam String email) {

			User user = userService.getUserByEmail(email);
			if (user != null)
				return true;

			return false;

		}
	 

		@PostMapping("/forgetpassword/{email}")
		public User forgetPass(@PathVariable("email") String email){
			return userService.ForgetPassword(email);
		}

		@PostMapping("/resetpassword/{email}/{newpass}/{cofirm}")
		public void RestPassword(@PathVariable("email") String email,@PathVariable("newpass") String newpass,@PathVariable("cofirm") String cofirm) {
			userService.RestPassword(email,newpass,cofirm);
		}
		@PutMapping("/activecompte/{username}")
		public String activecompte(@PathVariable("username") String username) {
			userService.activecompte(username);
			return "activated";
		}
	 
	 
	 
	 
	}

	@Data
	class RoleToUserForm {
	    private String username;
	    private String roleName;
	}
