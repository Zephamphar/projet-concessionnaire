package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;

import java.util.List;

public interface AdministrateurService {
    AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException;
    List<AdministrateurResponseDTO> trouverTous();

    AdministrateurResponseDTO recupererMonCompte(String email, String password);
}
