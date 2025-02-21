package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
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

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientEnregistre = clientService.ajouter(clientRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(clientEnregistre.email())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
