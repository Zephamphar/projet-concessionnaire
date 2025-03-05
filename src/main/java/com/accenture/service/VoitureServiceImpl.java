package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.LocationDAO;
import com.accenture.repository.VoitureDAO;
import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoitureServiceImpl implements VoitureService {

    VoitureDAO voitureDAO;
    LocationDAO locationDAO;
    VoitureMapper voitureMapper;

    public VoitureServiceImpl(VoitureDAO voitureDAO, LocationDAO locationDAO, VoitureMapper voitureMapper) {
        this.voitureDAO = voitureDAO;
        this.locationDAO = locationDAO;
        this.voitureMapper = voitureMapper;
    }

    /**
     * <p>Méthode permettant d'ajouter une Voiture au parc.</p>
     *
     * @param voitureRequestDTO L'objet métier voiture, non null
     * @return VoitureResponseDTO
     */

    @Override
    public VoitureResponseDTO ajouter(VoitureRequestDTO voitureRequestDTO) {
        Voiture voiture = voitureMapper.toVoiture(voitureRequestDTO);
        verifierEtCalculerVoiture(voiture);

        return voitureMapper.toVoitureResponseDTO(voitureDAO.save(voiture));
    }

    /**
     * <p>Méthode permettant de récupérer une liste de véhicule, selon un filtre choisi</p>
     * @param filtreRecherche Enum permettant de choisir le critère de recherche des voitures (Toutes, actives, inactives, retirées)
     * @return Une liste de VoitureResponseDTO correspondant au critère de recherche
     */
    @Override
    public List<VoitureResponseDTO> recupererToutes(FiltreRecherche filtreRecherche) {
        return switch (filtreRecherche) {
            case TOUS -> voitureDAO.findAll()
                    .stream()
                    .map(voitureMapper::toVoitureResponseDTO)
                    .toList();

            case ACTIF -> voitureDAO.findByActif(true)
                    .stream()
                    .map(voitureMapper::toVoitureResponseDTO)
                    .toList();

            case INACTIF -> voitureDAO.findByActif(false)
                    .stream()
                    .map(voitureMapper::toVoitureResponseDTO)
                    .toList();

            case RETIRE -> voitureDAO.findByRetireDuParc(true)
                    .stream()
                    .map(voitureMapper::toVoitureResponseDTO)
                    .toList();
        };
    }

    /**
     * <p>Méthode permettant de récupérer une voiture par son ID</p>
     * @param id L'ID de la voiture à récupérer
     * @return La VoitureResponseDTO correspondant à l'ID choisi
     * @throws EntityNotFoundException Si aucune Voiture n'existe à cet ID
     */

    @Override
    public VoitureResponseDTO recuperer(int id) throws EntityNotFoundException {
        Voiture voiture = voitureDAO
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucune voiture à cet ID."));

        return voitureMapper.toVoitureResponseDTO(voiture);
    }

    /**
     * <p>Méthode permettant de supprimer une voiture par son ID</p>
     * Si la voiture a déjà une ou plusieurs locations, elle est retirée du parc plutôt que supprimée
     * @param id L'ID de la voiture à supprimer
     * @throws EntityNotFoundException Si aucune Voiture n'existe à cet ID
     */

    @Override
    public void supprimer(int id) throws EntityNotFoundException {
        Voiture voiture = voitureDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Aucune voiture à cet ID."));
        List<Location> listeLocations = locationDAO.findByVehiculeId(voiture.getId());
        if (listeLocations.isEmpty()) {
            voitureDAO.delete(voiture);
            return;
        }
        voiture.setRetireDuParc(true);
        voitureDAO.save(voiture);
    }

    /**
     * <p>Méthode permettant de modifier une ou plusieurs propriétés d'une Voiture</p>
     * @param id L'ID de la voiture à modifier
     * @param voitureRequestDTO L'objet VoitureRequestDTO contenant les nouvelles valeurs à modifier
     * @return La VoitureResponseDTO mappée à partir de la Voiture modifiée
     * @throws VoitureException Si la VoitureRequestDTO vaut null
     * @throws EntityNotFoundException Si aucune Voiture n'existe à cet ID
     */

    @Override
    public VoitureResponseDTO modifier(int id, VoitureRequestDTO voitureRequestDTO) throws VoitureException, EntityNotFoundException {
        if (voitureRequestDTO == null)
            throw new VoitureException("Aucune information reçue.");
        Voiture voitureAModifier = voitureDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Aucune voiture à cet ID."));

        modifierVoiture(voitureRequestDTO, voitureAModifier);

        Voiture voitureModifiee = voitureDAO.save(voitureAModifier);
        return voitureMapper.toVoitureResponseDTO(voitureModifiee);
    }

    /* ******************************** *
     *         Méthodes privées         *
     * ******************************** */

    private void modifierVoiture(VoitureRequestDTO voitureRequestDTO, Voiture voitureAModifier) {
        if (voitureRequestDTO.marque() != null && !voitureRequestDTO.marque().isBlank())
            voitureAModifier.setMarque(voitureRequestDTO.marque());
        if (voitureRequestDTO.modele() != null && !voitureRequestDTO.modele().isBlank())
            voitureAModifier.setModele(voitureRequestDTO.modele());
        if (voitureRequestDTO.couleur() != null && !voitureRequestDTO.couleur().isBlank())
            voitureAModifier.setCouleur(voitureRequestDTO.couleur());
        if (voitureRequestDTO.nombrePlaces() != 0)
            voitureAModifier.setNombrePlaces(voitureRequestDTO.nombrePlaces());
        if (voitureRequestDTO.typeCarburant() != null)
            voitureAModifier.setTypeCarburant(voitureRequestDTO.typeCarburant());
        if (voitureRequestDTO.nombrePortes() != 0)
            voitureAModifier.setNombrePortes(voitureRequestDTO.nombrePortes());
        if (voitureRequestDTO.transmission() != null)
            voitureAModifier.setTransmission(voitureRequestDTO.transmission());
        if (voitureRequestDTO.climatisation() != voitureAModifier.isClimatisation())
            voitureAModifier.setClimatisation(voitureRequestDTO.climatisation());
        if (voitureRequestDTO.nombreBagages() != 0)
            voitureAModifier.setNombreBagages(voitureRequestDTO.nombreBagages());
        if (voitureRequestDTO.typeVoiture() != null)
            voitureAModifier.setTypeVoiture(voitureRequestDTO.typeVoiture());
        if (voitureRequestDTO.tarifJournalier() != 0)
            voitureAModifier.setTarifJournalier(voitureRequestDTO.tarifJournalier());
        if (voitureRequestDTO.kilometrage() != 0)
            voitureAModifier.setKilometrage(voitureAModifier.getKilometrage());
    }

    private void verifierEtCalculerVoiture(Voiture voiture) throws VoitureException {
        if (voiture == null)
            throw new VoitureException("La Voiture ne peut pas être null.");
        if (voiture.getMarque() == null || voiture.getMarque().isBlank())
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

        voiture.setPermis(voiture.getNombrePlaces() < 10 ? Permis.B : Permis.D1);
        voiture.setRetireDuParc(false);
    }
}

