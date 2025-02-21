package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;

public interface AdministrateurService {
    AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException;
}
