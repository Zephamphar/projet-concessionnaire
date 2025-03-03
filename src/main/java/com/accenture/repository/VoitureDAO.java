package com.accenture.repository;

import com.accenture.repository.entity.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoitureDAO extends JpaRepository<Voiture, Integer> {
}
