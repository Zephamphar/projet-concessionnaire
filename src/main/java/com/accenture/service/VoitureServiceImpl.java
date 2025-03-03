package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.VoitureDAO;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.Permis;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureServiceImpl implements VoitureService {

    VoitureDAO voitureDAO;
    VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDAO voitureDAO, VoitureMapper voitureMapper) {
        this.voitureDAO = voitureDAO;
        this.voitureMapper = voitureMapper;
    }

    @Override
    public VoitureResponseDTO ajouter(VoitureRequestDTO voitureRequestDTO) {
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDTO);
        verifierEtCalculerVoiture(voiture);

        return voitureMapper.toVoitureResponseDTO(voitureDAO.save(voiture));
    }

    @Override
    public List<VoitureResponseDTO> trouverToutes() {
        return voitureDAO.findAll()
                .stream()
                .map(voitureMapper::toVoitureResponseDTO)
                .toList();
    }

    private void verifierEtCalculerVoiture(Voiture voiture) throws VoitureException {
        if (voiture == null)
            throw new VoitureException("La Voiture ne peut pas être null.");
        if(voiture.getMarque() == null || voiture.getMarque().isBlank())
            throw new VoitureException("La marque est obligatoire");
        if (voiture.getModele() == null || voiture.getModele().isBlank())
            throw new VoitureException("Le modele est obligatoire.");
        if (voiture.getCouleur() == null || voiture.getCouleur().isBlank())
            throw new VoitureException("La couleur est obligatoire.");
        if (voiture.getNombrePlaces() == 0)
            throw new VoitureException("Le nombre de places est obligatoire.");
        if (voiture.getTypeCarburant() == null)
            throw new VoitureException("Le type de carburant est obligatoire.");
        if (voiture.getNombrePortes() == 0)
            throw new VoitureException("Le nombre de portes est obligatoire.");
        if (voiture.getTransmission() == null)
            throw new VoitureException("Le type de transmission est obligatoire.");
        if (voiture.getNombreBagages() == 0)
            throw new VoitureException("Le nombre de bagages est obligatoire.");
        if (voiture.getTypeVoiture() == null)
            throw new VoitureException("Le type de voiture est obligatoire.");
        if (voiture.getTarifJournalier() == 0)
            throw new VoitureException("Le tarif journalier est obligatoire.");
        if (voiture.getKilometrage() == 0)
            throw new VoitureException("Le kilométrage est obligatoire.");

        voiture.setPermis(voiture.getNombrePlaces() < 10 ? Permis.B : Permis.D1);
        voiture.setRetireDuParc(false);
    }
}

/*
String marque,
        String modele,
        String couleur,
        int nbPlaces,
        TypeCarburant typeCarburant,
        int nombrePortes,
        Transmission transmission,
        boolean climatisation,
        int nombreBagages,
        TypeVoiture typeVoiture,
        Permis permis,
        int tarifJournalier,
        int kilometrage,
        boolean actif,
        boolean retireDuParc
 */