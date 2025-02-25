package com.accenture.controller;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.service.AdministrateurService;
import com.accenture.service.AdministrateurServiceImpl;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {

    AdministrateurService administrateurService;
    AdministrateurMapper administrateurMapper;

    public AdministrateurController(AdministrateurService administrateurService, AdministrateurMapper administrateurMapper) {
        this.administrateurService = administrateurService;
        this.administrateurMapper = administrateurMapper;
    }

    @GetMapping
    List<AdministrateurResponseDTO> trouverTous() {
        return administrateurService.trouverTous();
    }

    @GetMapping("/moncompte")
    ResponseEntity<AdministrateurResponseDTO> recupererMonCompte(String email, String password) throws AdministrateurException, EntityNotFoundException {
        AdministrateurResponseDTO administrateur = administrateurService.recupererMonCompte(email, password);
        return ResponseEntity.ok(administrateur);
    }

    @PostMapping
    ResponseEntity<Void> ajouter(@RequestBody AdministrateurRequestDTO administrateurRequestDTO) {
        AdministrateurResponseDTO administrateurResponseDTO = administrateurService.ajouter(administrateurRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(administrateurResponseDTO.email())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping
    ResponseEntity<Void> supprimer(String email, String password) {
        administrateurService.supprimer(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
