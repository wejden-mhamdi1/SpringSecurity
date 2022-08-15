package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.ModelSavePassword;
import com.example.demo.Entity.User;



public interface SavePasswordRepository extends JpaRepository <ModelSavePassword,Integer> {
	
	List<ModelSavePassword> findByUser(User user);

}
