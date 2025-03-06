package com.accenture.service.dto;

import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVoiture;

public record VoitureRequestDTO(
        String marque,
        String modele,
        String couleur,
        int nombrePlaces,
        TypeCarburant typeCarburant,
        int nombrePortes,
        Transmission transmission,
        boolean climatisation,
        int nombreBagages,
        TypeVoiture typeVoiture,
        Permis permis,
        int tarifJournalier,
        int kilometrage
) {
}
