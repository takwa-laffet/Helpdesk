package com.helpdesk.helpdesk_backend.repository;


import com.helpdesk.helpdesk_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.helpdesk.helpdesk_backend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    List<User> findByIsActiveTrue();
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}