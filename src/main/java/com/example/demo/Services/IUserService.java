package com.example.demo.Services;

import java.util.List;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;

public interface IUserService {
	Role addRole(Role role);
	User  addRoleToUser(String username, String rolename);
	List<User> getAllUsers();
	User addUser(User user);
	User getUserByName(String NameUser);
	public User getUserByEmail(String email);
	User register(User user);

}
