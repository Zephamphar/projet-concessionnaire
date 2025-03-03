package com.accenture.service;

import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;

import java.util.List;

public interface VoitureService {
    VoitureResponseDTO ajouter(VoitureRequestDTO voitureRequestDTO);

    List<VoitureResponseDTO> trouverToutes();
}
