package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.AdministrateurMapper;
import com.shared.Permis;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdministrateurServiceImplTest {

    @Mock
    AdministrateurMapper mapperMock;
    @Mock
    AdministrateurDAO DAOMock;
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
        Mockito.when(DAOMock.existsByEmail("dylan@mail.com")).thenReturn(true);
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

        Mockito.when(mapperMock.toAdministrateur(requestDTO)).thenReturn(administrateurAvant);
        Mockito.when(DAOMock.save(administrateurAvant)).thenReturn(administrateurApres);
        Mockito.when(mapperMock.toAdministrateurResponseDTO(administrateurApres)).thenReturn(responseDTO);

        assertSame(responseDTO, administrateurService.ajouter(requestDTO));
        Mockito.verify(DAOMock, Mockito.times(1)).save(administrateurAvant);

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
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        return new AdministrateurResponseDTO(
                "Demasse",
                "Dylan",
                "dylan@mail.com",
                "CEO"
        );
    }

}