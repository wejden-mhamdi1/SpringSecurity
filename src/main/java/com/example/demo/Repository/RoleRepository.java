package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
Role findByRole(String role);
}