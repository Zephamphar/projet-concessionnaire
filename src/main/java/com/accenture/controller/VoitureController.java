package com.accenture.controller;

import com.accenture.exception.VoitureException;
import com.accenture.service.VoitureService;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.shared.FiltreRecherche;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/voitures")
public class VoitureController {

    VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @PostMapping
    ResponseEntity<Void> ajouter(VoitureRequestDTO voitureRequestDTO) {
        VoitureResponseDTO voitureEnregistree = voitureService.ajouter(voitureRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(voitureEnregistree.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    List<VoitureResponseDTO> voitures(FiltreRecherche filtreRecherche) {
        return voitureService.recupererToutes(filtreRecherche);
    }

    @GetMapping("/{id}")
    ResponseEntity<VoitureResponseDTO> recupererParID(@PathVariable("id") int id) throws VoitureException {
        VoitureResponseDTO voiture = voitureService.recuperer(id);
        return ResponseEntity.ok(voiture);
    }

    @PatchMapping("/{id}")
    ResponseEntity<VoitureResponseDTO> modifier(@PathVariable("id")int id, VoitureRequestDTO voitureRequestDTO) {
        VoitureResponseDTO voitureModifiee = voitureService.modifier(id, voitureRequestDTO);
        return ResponseEntity.ok(voitureModifiee);
    }

}
