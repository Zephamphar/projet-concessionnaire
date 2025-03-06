package com.accenture.controller;

import com.accenture.repository.MotoDAO;
import com.accenture.service.MotoService;
import com.accenture.service.dto.MotoRequestDTO;
import com.accenture.service.dto.MotoResponseDTO;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    MotoService motoService;

    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    @PostMapping
    ResponseEntity<Void> ajouter(MotoRequestDTO motoRequestDTO) {
        MotoResponseDTO motoEnregistree = motoService.ajouter(motoRequestDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(motoEnregistree.id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    List<MotoResponseDTO> motos() {
        return motoService.recupererToutes();
    }

    @GetMapping("/{id}")
    ResponseEntity<MotoResponseDTO> recuperParID(@PathVariable("id") int id) {
        return ResponseEntity.ok(motoService.recuperer(id));
    }

    @PatchMapping("/{id}")
    ResponseEntity<MotoResponseDTO> modifier(@PathVariable("id")int id, MotoRequestDTO motoRequestDTO) {
        MotoResponseDTO motoModifiee = motoService.modifier(id, motoRequestDTO);
        return ResponseEntity.ok(motoModifiee);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> supprimer(@PathVariable("id") int id) {
        motoService.supprimer(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
