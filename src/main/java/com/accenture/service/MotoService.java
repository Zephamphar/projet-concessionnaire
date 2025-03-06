package com.accenture.service;

import com.accenture.exception.MotoException;
import com.accenture.service.dto.MotoRequestDTO;
import com.accenture.service.dto.MotoResponseDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MotoService {

    MotoResponseDTO ajouter(MotoRequestDTO motoRequestDTO) throws MotoException;
    MotoResponseDTO modifier(int idMoto, MotoRequestDTO motoRequestDTO) throws EntityNotFoundException, MotoException;
    List<MotoResponseDTO> recupererToutes();
    MotoResponseDTO recuperer(int id) throws EntityNotFoundException;
    void supprimer(int id) throws EntityNotFoundException;

}
