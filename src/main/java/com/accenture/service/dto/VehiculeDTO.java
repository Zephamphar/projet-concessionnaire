package com.accenture.service.dto;

import java.util.List;

public record VehiculeDTO (
        List<VoitureResponseDTO> listeVoitures,
        List<MotoResponseDTO> listeMotos
) {

}
