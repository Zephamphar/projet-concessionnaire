package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;

import java.util.List;

public interface AdministrateurService {
    AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException;
    List<AdministrateurResponseDTO> trouverTous();
    AdministrateurResponseDTO recupererMonCompte(String email, String password);

    AdministrateurResponseDTO modifier(String email, String password, AdministrateurRequestDTO administrateurRequestDTO);

    void supprimer(String email, String password) throws AdministrateurException;
}
