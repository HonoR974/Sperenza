package com.example.APISperenza.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.APISperenza.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
