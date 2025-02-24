package com.accenture.service.dto;

import com.accenture.repository.entity.Location;
import com.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public record ClientResponseDTO(
        String nom,
        String prenom,
        AdresseDTO adresse,
        String email,
        LocalDate dateDeNaissance,
        LocalDate dateDInscription,
        HashSet<Permis> permis,
        Boolean desactive
) {
}
