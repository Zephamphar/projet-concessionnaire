package com.accenture.repository;

import com.accenture.repository.entity.Moto;
import com.accenture.repository.entity.Voiture;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoDAO extends JpaRepository<Moto, Integer> {
    List<Moto> findByActif(boolean actif);
    List<Moto> findByRetireDuParc(boolean retire);
}
