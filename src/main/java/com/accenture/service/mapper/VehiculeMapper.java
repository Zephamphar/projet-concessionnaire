package com.accenture.service.mapper;

import com.accenture.repository.entity.Moto;
import com.accenture.repository.entity.Vehicule;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.MotoResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.service.dto.VoitureResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class VehiculeMapper {

    private final VoitureMapper voitureMapper;
    private final MotoMapper motoMapper;

    public VehiculeMapper(VoitureMapper voitureMapper, MotoMapper motoMapper) {
        this.voitureMapper = voitureMapper;
        this.motoMapper = motoMapper;
    }

    public VehiculeDTO toDTO(List<Vehicule> listeVehicules) {
        List<VoitureResponseDTO> listeVoitures = listeVehicules
                .stream()
                .filter(Voiture.class::isInstance)
                .map(v -> (Voiture)v)
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();

        List<MotoResponseDTO> listeMotos = listeVehicules
                .stream()
                .filter(Moto.class::isInstance)
                .map(v -> (Moto)v)
                .map(motoMapper::toMotoResponseDTO)
                .toList();

        return new VehiculeDTO(listeVoitures, listeMotos);
    }

}
