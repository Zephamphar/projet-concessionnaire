package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import java.util.List;

public interface ClientService {

    ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException;
    ClientResponseDTO recupererMonCompte(String email, String password);
    ClientResponseDTO modifierPartiellement(String email, String password, ClientRequestDTO clientRequestDTO);
    void supprimer(String email, String password) throws ClientException;
    List<ClientResponseDTO> trouverTous();}
