package com.example.demo.ConfigSecurity;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

import org.springframework.beans.factory.annotation.Autowired;
@Configuration @EnableWebSecurity @RequiredArgsConstructor 
public class ConfigSecurity extends WebSecurityConfigurerAdapter {
	@Autowired 
	UserDetailsService userDetailsService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	AuthentificationFilterTOKEN authentificationFilterTOKEN = new AuthentificationFilterTOKEN(authenticationManagerBean());
	    	authentificationFilterTOKEN.setFilterProcessesUrl("/api/login");
	    	//http.headers().addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin","*"));
	        http.csrf().disable();
	        http.sessionManagement().sessionCreationPolicy(STATELESS);
	        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
	        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER");
	       http.authorizeRequests().antMatchers(GET, "/api/users/**").hasAnyAuthority("ROLE_USER");
	      //  http.authorizeRequests().antMatchers(POST, "/api/addUser/**").hasAnyAuthority("ROLE_ADMIN");
	        //http.authorizeRequests().antMatchers(POST, "/api/addUsera/**").hasAnyAuthority("ROLE_ADMIN");
	      //  http.authorizeRequests().antMatchers( "/activecompte/{username}");
	        //http.authorizeRequests().antMatchers("/resetpassword/{email}/{newpass}/{cofirm}");
	        //http.authorizeRequests().antMatchers("/forgetpassword/{email}");
	       //http.authorizeRequests().antMatchers( "/activecompte/{username}");
	       //http.authorizeRequests().antMatchers("/addUsera").permitAll();
	        
	        
	    //  http.authorizeRequests().antMatchers("/api/addRole","/api/addUser","/api/addRoleToUser/{NameUser}/{NameRole}","/api/forgetpassword/{email}","/api/resetpassword/{email}/{newpass}/{cofirm}","/api/activecompte/{username}","api/users"
			//		).permitAll();
	        
	        
	        
	        
	      http.authorizeRequests().anyRequest().authenticated();
	        http.addFilter(authentificationFilterTOKEN);
	        http.addFilterBefore(new AuthorizationFilterTOKEN(), UsernamePasswordAuthenticationFilter.class);
	      //  http.authorizeRequests().anyRequest().authenticated();
			
	    }

	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	}

	
	
