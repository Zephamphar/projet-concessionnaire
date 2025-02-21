package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurDAO extends JpaRepository<Administrateur, Integer> {
    boolean existsByEmail(String email);
}
