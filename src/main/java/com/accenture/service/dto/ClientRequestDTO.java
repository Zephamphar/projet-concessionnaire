package com.accenture.service.dto;

import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Location;
import com.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

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
