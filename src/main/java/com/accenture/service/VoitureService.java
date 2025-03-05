package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.shared.FiltreRecherche;

import java.util.List;

public interface VoitureService {
    VoitureResponseDTO ajouter(VoitureRequestDTO voitureRequestDTO);

    List<VoitureResponseDTO> recupererToutes(FiltreRecherche filtreRecherche);

    VoitureResponseDTO recuperer(int id) throws VoitureException;

    void supprimer(int id) throws VoitureException;

    VoitureResponseDTO modifier(int id, VoitureRequestDTO voitureRequestDTO);
}
