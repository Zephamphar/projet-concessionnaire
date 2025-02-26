package com.accenture.service.dto;

import com.accenture.shared.Permis;
import lombok.Builder;

import java.time.LocalDate;
import java.util.HashSet;

@Builder
public record ClientRequestDTO(
        String nom,
        String prenom,
        AdresseDTO adresse,
        String email,
        String password,
        LocalDate dateDeNaissance,
        LocalDate dateDInscription,
        HashSet<Permis> permis,
        Boolean desactive
) {
}
