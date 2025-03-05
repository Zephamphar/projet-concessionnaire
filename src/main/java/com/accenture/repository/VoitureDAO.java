package com.accenture.repository;

import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoitureDAO extends JpaRepository<Voiture, Integer> {
    List<Voiture> findByActif(boolean actif);
    List<Voiture> findByRetireDuParc(boolean retire);
}
