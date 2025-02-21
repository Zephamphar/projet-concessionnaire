package com.accenture.controller;

import com.accenture.service.AdministrateurService;
import com.accenture.service.AdministrateurServiceImpl;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {

    AdministrateurService administrateurService;
    AdministrateurMapper administrateurMapper;

    public AdministrateurController(AdministrateurService administrateurService, AdministrateurMapper administrateurMapper) {
        this.administrateurService = administrateurService;
        this.administrateurMapper = administrateurMapper;
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
}
