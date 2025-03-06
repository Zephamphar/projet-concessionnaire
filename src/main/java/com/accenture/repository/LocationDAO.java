package com.accenture.repository;

import com.accenture.repository.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDAO extends JpaRepository<Location, Integer> {
    List<Location> findByVehiculeId(int id);
}
