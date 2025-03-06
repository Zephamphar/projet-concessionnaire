package com.accenture.service.dto;

import java.util.ArrayList;
import java.util.List;

public record VehiculeDTO (
        List<VoitureResponseDTO> listeVoitures,
        List<MotoResponseDTO> listeMotos
) {
    public VehiculeDTO() {
        this(new ArrayList<>(), new ArrayList<>());
    }
}
