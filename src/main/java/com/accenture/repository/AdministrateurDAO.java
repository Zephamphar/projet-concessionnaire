package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministrateurDAO extends JpaRepository<Administrateur, Integer> {
    boolean existsByEmail(String email);
    Optional<Administrateur> findByEmailAndPassword(String email, String password);
}
