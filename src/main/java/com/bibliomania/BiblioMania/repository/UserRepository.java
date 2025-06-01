package com.bibliomania.BiblioMania.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bibliomania.BiblioMania.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificacionToken(String verificacionToken);
    List<User> findByActivoTrue();
    List<User> findByActivoFalse();
    List<User> findByRol(String rol);
}