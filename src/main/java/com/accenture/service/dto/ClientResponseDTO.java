package com.accenture.service.dto;

import com.accenture.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;

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
