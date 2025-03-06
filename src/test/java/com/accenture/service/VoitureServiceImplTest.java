package com.accenture.service;

import com.accenture.exception.VoitureException;
import com.accenture.repository.VoitureDAO;
import com.accenture.repository.entity.Voiture;
import com.accenture.service.dto.VoitureRequestDTO;
import com.accenture.service.dto.VoitureResponseDTO;
import com.accenture.service.mapper.VoitureMapper;
import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVoiture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoitureServiceImplTest {

    @Mock
    VoitureDAO mockDAO;
    @Mock
    VoitureMapper mockMapper;
    @InjectMocks
    VoitureServiceImpl voitureService;


    @DisplayName("Si ajouter(null), exception levée")
    @Test
    void testAjouterVoitureNull() {
        assertThrows(VoitureException.class, () -> voitureService.ajouter(null));
    }

    @DisplayName("Si ajouter(marque null), exception levée")
    @Test
    void testAjouterVoitureMarqueNull() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                null,
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );
        Voiture voiture = new Voiture(
                null,
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );
        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(marque blank), exception levée")
    @Test
    void testAjouterVoitureMarqueBlank() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "   \t   ",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );
        Voiture voiture = new Voiture(
                null,
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(modele null), exception levée")
    @Test
    void testAjouterVoitureModeleNull() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                null,
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                null,
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(modele blank), exception levée")
    @Test
    void testAjouterVoitureModeleBlank() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "   \t   ",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "   \t   ",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(couleur null), exception levée")
    @Test
    void testAjouterVoitureCouleurNull() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                null,
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                null,
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(couleur blank), exception levée")
    @Test
    void testAjouterVoitureCouleurBlank() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "   \t   ",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "   \t   ",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(nombre de places 0), exception levée")
    @Test
    void testAjouterVoitureNombrePlacesZero() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                0,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                0,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(TypeCarburant null), exception levée")
    @Test
    void testAjouterVoitureTypeCarburantNull() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                null,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                null,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(nombre de portes 0), exception levée")
    @Test
    void testAjouterVoitureNombrePortesZero() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                0,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                0,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(Transmission null), exception levée")
    @Test
    void testAjouterVoitureTransmissionNull() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                null,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                null,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(nombre bagages 0), exception levée")
    @Test
    void testAjouterVoitureNombreBagaesZero() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                0,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                0,
                TypeVoiture.CITADINE,
                Permis.B,
                0,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(TypeVoiture null), exception levée")
    @Test
    void testAjouterVoitureTypeVoitureNull() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                null,
                Permis.B,
                50,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                null,
                Permis.B,
                50,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(tarif journalier 0), exception levée")
    @Test
    void testAjouterVoitureTarifJournalierZero() {
        VoitureRequestDTO voitureRequestDTO = new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                0,
                5000
        );

        Voiture voiture = new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                0,
                5000,
                true,
                false
        );

        when(mockMapper.toVoiture(voitureRequestDTO)).thenReturn(voiture);
        assertThrows(VoitureException.class, () -> voitureService.ajouter(voitureRequestDTO));
    }

    @DisplayName("Si ajouter(ok), on récupère la VoitureResponseDTO")
    @Test
    void ajouterVoitureOK() {
        VoitureRequestDTO requestDTO = creerRequestDTO();
        Voiture voitureAEnregistrer = creerVoiture();
        Voiture voitureEnregistree = creerVoiture();
        voitureEnregistree.setId(1);
        VoitureResponseDTO responseDTO = creerResponseDTO();

        when(mockMapper.toVoiture(requestDTO)).thenReturn(voitureAEnregistrer);
        when((mockDAO.save(voitureAEnregistrer))).thenReturn(voitureEnregistree);
        when(mockMapper.toVoitureResponseDTO(voitureEnregistree)).thenReturn(responseDTO);

        assertEquals(responseDTO, voitureService.ajouter(requestDTO));
        verify(mockDAO, times(1)).save(voitureAEnregistrer);

    }

    private Voiture creerVoiture() {
        return new Voiture(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );
    }

    private VoitureResponseDTO creerResponseDTO() {
        return new VoitureResponseDTO(
                1,
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000,
                true,
                false
        );
    }

    private VoitureRequestDTO creerRequestDTO() {
        return new VoitureRequestDTO(
                "Renault",
                "Clio",
                "Bleu",
                5,
                TypeCarburant.ESSENCE,
                5,
                Transmission.MANUELLE,
                true,
                2,
                TypeVoiture.CITADINE,
                Permis.B,
                50,
                5000
        );
    }


}