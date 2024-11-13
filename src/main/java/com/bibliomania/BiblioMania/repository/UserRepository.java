package com.bibliomania.BiblioMania.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliomania.BiblioMania.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificacionToken(String verificacionToken);
}