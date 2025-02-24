package com.accenture.repository;

import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientDAO extends JpaRepository<Client, String> {
    boolean existsByEmail(String email);
    Optional<Client> findByEmailAndPassword(String email, String password);
}
