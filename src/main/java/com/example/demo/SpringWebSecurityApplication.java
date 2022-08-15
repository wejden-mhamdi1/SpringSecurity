package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Services.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class SpringWebSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebSecurityApplication.class, args);
	}
	 @Bean
	    BCryptPasswordEncoder CryptPassword() {
	        return new BCryptPasswordEncoder();
	    }

	 @Bean
		CommandLineRunner run(UserService userService) {
			return args -> {
				userService.addRole(new Role(null, "ROLE_USER"));
				//userService.addRole(new Role(null, "ROLE_MANAGER"));
				userService.addRole(new Role(null, "ROLE_ADMIN"));
				//userService.addRole(new Role(null, "ROLE_SUPER_ADMIN"));

				//userService.addUser(new User(null, "wejden", "mhamdi","email", "1234",true, new ArrayList<>()));
				

				//userService.addRoleToUser("wejden", "ROLE_USER");
				
			};
		}

	}
