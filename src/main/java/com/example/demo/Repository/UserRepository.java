package com.example.demo.Repository;
import com.example.demo.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
User findByUserName(String userName);
public User findByEmail(String email);
}
