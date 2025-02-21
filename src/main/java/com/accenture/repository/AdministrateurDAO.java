package com.accenture.repository;

import com.accenture.repository.entity.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrateurDAO extends JpaRepository<Administrateur, Integer> {
    Boolean existsByEmail(String email);
}
