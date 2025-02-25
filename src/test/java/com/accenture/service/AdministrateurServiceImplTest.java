package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.AdministrateurMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdministrateurServiceImplTest {

    @Mock
    AdministrateurMapper mockMapper;
    @Mock
    AdministrateurDAO mockDao;
    @InjectMocks
    AdministrateurServiceImpl administrateurService;

    @DisplayName("Si ajouter(null), exception levée")
    @Test
    void testAjouterAdministrateurNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(null));
    }

    @DisplayName("Si l'email est déjà enregistré en base, exception levée")
    @Test
    void testAjouterAdministrateurExisteDeja() {
        Mockito.when(mockDao.existsByEmail("dylan@mail.com")).thenReturn(true);
        AdministrateurRequestDTO dylan = creerAdministrateurRequestDTODylan();
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dylan));
    }

    @DisplayName("Si ajouter(nom null), exception levée")
    @Test
    void testAjouterAdministrateurNomNull() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                null,
                "Dylan",
                "P@55w0rd",
                "dylan@mail.com",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(nom blank), exception levée")
    @Test
    void testAjouterAdministrateurNomBlank() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "   \t   ",
                "Dylan",
                "P@55w0rd",
                "dylan@mail.com",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(prenom null), exception levée")
    @Test
    void testAjouterAdministrateurPrenomNull() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                null,
                "dylan@mail.com",
                "P@55w0rd",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(prenom blank), exception levée")
    @Test
    void testAjouterAdministrateurPrenomBlank() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "   \t   ",
                "P@55w0rd",
                "dylan@mail.com",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(email null), exception levée")
    @Test
    void testAjouterAdministrateurEmailNull() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "P@55w0rd",
                null,
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(email blank), exception levée")
    @Test
    void testAjouterAdministrateurEmailBlank() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "P@55w0rd",
                "   \t   ",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(password non conforme), exception levée")
    @Test
    void testAjouterAdministrateurPasswordNonConforme() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "password",
                "dylan@mail.com",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(password null), exception levée")
    @Test
    void testAjouterAdministrateurPasswordNull() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                null,
                "dylan@mail.com",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(password blank), exception levée")
    @Test
    void testAjouterAdministrateurPasswordBlank() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "   \t   ",
                "dylan@mail.com",
                "CEO"
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(fonction null), exception levée")
    @Test
    void testAjouterAdministrateurFonctionNull() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "P@55w0rd",
                "dylan@mail.com",
                null
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(fonction blank), exception levée")
    @Test
    void testAjouterAdministrateurFonctionBlank() {
        AdministrateurRequestDTO dto = new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "P@55w0rd",
                "dylan@mail.com",
                "   \t   "
        );
        assertThrows(AdministrateurException.class, () -> administrateurService.ajouter(dto));
    }

    @DisplayName("Si ajouter(ok), on récupère la ClientResponseDTO")
    @Test
    void ajouterClientOK() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();
        AdministrateurResponseDTO responseDTO = creerAdministrateurResponseDTODylan();
        Administrateur administrateurAvant = creerAdministrateurDylan();
        Administrateur administrateurApres = creerAdministrateurDylan();

        Mockito.when(mockMapper.toAdministrateur(requestDTO)).thenReturn(administrateurAvant);
        Mockito.when(mockDao.save(administrateurAvant)).thenReturn(administrateurApres);
        Mockito.when(mockMapper.toAdministrateurResponseDTO(administrateurApres)).thenReturn(responseDTO);

        assertSame(responseDTO, administrateurService.ajouter(requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(administrateurAvant);

    }


    @DisplayName("Si recupererMonCompte(email null), exception levée")
    @Test
    void testRecupererMonCompteEmailNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.recupererMonCompte(null, "P@55w0rd"));
    }

    @DisplayName("Si recupererMonCompte(email blank), exception levée")
    @Test
    void testRecupererMonCompteEmailBlank() {
        assertThrows(AdministrateurException.class, () -> administrateurService.recupererMonCompte("   \t   ", "P@55w0rd"));
    }

    @DisplayName("Si recupererMonCompte(password null), exception levée")
    @Test
    void testRecupererMonComptePasswordNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.recupererMonCompte("dylan@mail.com", null));
    }

    @DisplayName("Si recupererMonCompte(password blank), exception levée")
    @Test
    void testRecupererMonComptePasswordBlank() {
        assertThrows(AdministrateurException.class, () -> administrateurService.recupererMonCompte("dylan@mail.com", "   \t   "));
    }

    @DisplayName("Si recupererMonCompte(email non trouvé), exception levée")
    @Test
    void testRecupererMonCompteEmailNonTrouve() {
        Mockito.when(mockDao.existsByEmail("test@teast.com")).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> administrateurService.recupererMonCompte("test@teast.com", "P@55w0rd"));
    }

    @DisplayName("Si ajouter(ok), on récupère la ClientResponseDTO")
    @Test
    void testRecupererMonCompteOK() {
        AdministrateurResponseDTO responseDTO = creerAdministrateurResponseDTODylan();
        Administrateur administrateur = creerAdministrateurDylan();

        Mockito.when(mockDao.existsByEmail("dylan@mail.com")).thenReturn(true);
        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateur));
        Mockito.when(mockMapper.toAdministrateurResponseDTO(administrateur)).thenReturn(responseDTO);

        assertSame(responseDTO, administrateurService.recupererMonCompte("dylan@mail.com", "P@55w0rd"));
        Mockito.verify(mockDao, Mockito.times(1)).findByEmailAndPassword("dylan@mail.com", "P@55w0rd");
    }

    @DisplayName("Si supprimer(null, ok), exception levée")
    @Test
    void testSupprimerEmailNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.supprimer(null, "P@55w0rd"));
    }

    @DisplayName("Si supprimer(blank, ok), exception levée")
    @Test
    void testSupprimerEmailBlank() {
        assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("   \t   ", "P@55w0rd"));
    }

    @DisplayName("Si supprimer(ok, null), exception levée")
    @Test
    void testSupprimerPasswordNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("dylan@mail.com", null));
    }

    @DisplayName("Si supprimer(ok, blank), exception levée")
    @Test
    void testSupprimerPasswordBlank() {
        assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("dylan@mail.com", "   \t   "));
    }

    @DisplayName("Si supprimer(email incorrect), exception levée")
    @Test
    void testSupprimerEmailIncorrect() {
        Mockito.when(mockDao.findByEmailAndPassword("dlan@mail.com", "P@55w0rd")).thenReturn(Optional.empty());
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("dlan@mail.com", "P@55w0rd"));
        assertEquals("Identifiants incorrects.", ex.getMessage());
    }

    @DisplayName("Si supprimer(password incorrect), exception levée")
    @Test
    void testSupprimerPasswordIncorrect() {
        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "p@55w0rd")).thenReturn(Optional.empty());
        AdministrateurException ex = assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("dylan@mail.com", "p@55w0rd"));
        assertEquals("Identifiants incorrects.", ex.getMessage());
    }

    @DisplayName("Si supprimer(ok, ok)")
    @Test
    void testSupprimerOK() {
        Administrateur administrateur = creerAdministrateurDylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateur));
        administrateurService.supprimer("dylan@mail.com", "P@55w0rd");
        Mockito.verify(mockDao, Mockito.times(1)).delete(administrateur);
    }

    private static Administrateur creerAdministrateurDylan() {
        return new Administrateur(
                "Demasse",
                "Dylan",
                "dylan@mail.com",
                "P@55w0rd",
                "CEO"
        );
    }

    private static AdministrateurRequestDTO creerAdministrateurRequestDTODylan() {
        return new AdministrateurRequestDTO(
                "Demasse",
                "Dylan",
                "P@55w0rd",
                "dylan@mail.com",
                "CEO"
        );
    }

    private static AdministrateurResponseDTO creerAdministrateurResponseDTODylan() {
        return new AdministrateurResponseDTO(
                "Demasse",
                "Dylan",
                "dylan@mail.com",
                "CEO"
        );
    }

}