package com.accenture.service.dto;

import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeMoto;

public record MotoRequestDTO(
        String marque,
        String modele,
        String couleur,
        int nombreCylindres,
        int cylindree,
        int poids,
        int puissance,
        int hauteurSelle,
        Transmission transmission,
        TypeMoto typeMoto,
        int tarifJournalier,
        int kilometrage
) {
}
