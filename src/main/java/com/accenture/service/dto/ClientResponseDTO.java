package com.accenture.service.dto;

import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Location;
import com.shared.Permis;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

public record ClientResponseDTO(
        int id,
        String nom,
        String prenom,
        String email,
        String password,
        Adresse adresse,
        LocalDate dateDeNaissance,
        LocalDate dateDInscription,
        HashSet<Permis> permis,
        Boolean desactive,
        List<Location> locations
) {
}
