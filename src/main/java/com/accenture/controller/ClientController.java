package com.accenture.controller;

import com.accenture.exception.ClientException;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDTO> clients(){
        return clientService.trouverTous();
    }

    @GetMapping("/moncompte")
    ResponseEntity<ClientResponseDTO> recupererMonCompte(String email, String password) throws ClientException, EntityNotFoundException {
        ClientResponseDTO client = clientService.recupererMonCompte(email, password);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDTO clientRequestDTO) throws ClientException, EntityNotFoundException {
        ClientResponseDTO clientEnregistre = clientService.ajouter(clientRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(clientEnregistre.email())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
