package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdministrateurServiceImplTest {

    @Mock
    AdministrateurMapper mockMapper;
    @Mock
    AdministrateurDAO mockDao;
    @Mock
    PasswordEncoder mockPasswordEncoder;
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

    @DisplayName("Si ajouter(ok), on récupère la AdministrateurResponseDTO")
    @Test
    void ajouterAdministrateurOK() {
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

    @DisplayName("Si ajouter(ok), on récupère la AdministrateurResponseDTO")
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

    @DisplayName("Si supprimer(ok, ok) mais qu'il n'existe qu'un seul Administrateur, exception levée")
    @Test
    void testSupprimerLeDernierAdministrateur() {
        Administrateur administrateur = creerAdministrateurDylan();

        Mockito.when(mockDao.count()).thenReturn(1L);
        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateur));
        assertThrows(AdministrateurException.class, () -> administrateurService.supprimer("dylan@mail.com", "P@55w0rd"));
    }


    @DisplayName("Si supprimer(ok, ok)")
    @Test
    void testSupprimerOK() {
        Administrateur administrateur1 = creerAdministrateurDylan();
        Administrateur administrateur2 = creerAdministrateurVictorien();

        Mockito.when(mockDao.count()).thenReturn(2L);
        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateur1));
        administrateurService.supprimer("dylan@mail.com", "P@55w0rd");
        Mockito.verify(mockDao, Mockito.times(1)).delete(administrateur1);
    }



    @DisplayName("Si modifier(null), exception levée")
    @Test
    void testModifierNull() {
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("dylan@mail.com", "P@55w0rd", null));
    }

    @DisplayName("Si modifier(email incorrect), exception levée")
    @Test
    void testModifierEmailIncorrect() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("dlan@mail.com", "P@55w0rd")).thenReturn(Optional.empty());
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("dlan@mail.com", "P@55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(password incorrect), exception levée")
    @Test
    void testModifierPasswordIncorrect() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P55w0rd")).thenReturn(Optional.empty());
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("dylan@mail.com", "P55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(email null), exception levée")
    @Test
    void testModifierEmailNull() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword(null, "P@55w0rd")).thenReturn(Optional.empty());
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier(null, "P@55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(password null), exception levée")
    @Test
    void testModifierPasswordNull() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", null)).thenReturn(Optional.empty());
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("dylan@mail.com", null, requestDTO));
    }

    @DisplayName("Si modifier(email blank), exception levée")
    @Test
    void testModifierEmailBlank() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("   \t   ", "P@55w0rd")).thenReturn(Optional.empty());
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("   \t   ", "P@55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(password blank), exception levée")
    @Test
    void testModifierPasswordBlank() {
        AdministrateurRequestDTO requestDTO = creerAdministrateurRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "   \t   ")).thenReturn(Optional.empty());
        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("dylan@mail.com", "   \t   ", requestDTO));
    }

    @DisplayName("Si modifier(email ok, password ok, modifier prenom)")
    @Test
    void testModifierPrenom() {
        Administrateur administrateurAModifier = creerAdministrateurDylan();

        AdministrateurRequestDTO requestDTO = AdministrateurRequestDTO.builder()
                .prenom("Maleck Jr")
                .build();

        Administrateur administrateurInfosAModifier = Administrateur.builder()
                .prenom("Maleck Jr")
                .build();

        Administrateur administrateurAEnregistrer = creerAdministrateurDylan();
        administrateurAEnregistrer.setPrenom(administrateurInfosAModifier.getPrenom());

        Administrateur administrateurEnregistre = creerAdministrateurDylan();
        administrateurEnregistre.setPrenom(administrateurInfosAModifier.getPrenom());

        AdministrateurResponseDTO responseDTO = new AdministrateurResponseDTO(
                "Demasse",
                "Maleck Jr",
                "dylan@mail.com",
                "CEO"
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateurAModifier));
        Mockito.when(mockMapper.toAdministrateur(requestDTO)).thenReturn(administrateurInfosAModifier);
        Mockito.when(mockDao.save(administrateurAEnregistrer)).thenReturn(administrateurEnregistre);
        Mockito.when(mockMapper.toAdministrateurResponseDTO(administrateurEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, administrateurService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(administrateurAEnregistrer);
    }


    @DisplayName("Si modifier(email ok, password ok, modifier nom)")
    @Test
    void testModifierNom() {
        Administrateur administrateurAModifier = creerAdministrateurDylan();

        AdministrateurRequestDTO requestDTO = AdministrateurRequestDTO.builder()
                .nom("Morgan")
                .build();

        Administrateur administrateurInfosAModifier = Administrateur.builder()
                .nom("Morgan")
                .build();

        Administrateur administrateurAEnregistrer = creerAdministrateurDylan();
        administrateurAEnregistrer.setNom(administrateurInfosAModifier.getNom());

        Administrateur administrateurEnregistre = creerAdministrateurDylan();
        administrateurEnregistre.setNom(administrateurInfosAModifier.getNom());

        AdministrateurResponseDTO responseDTO = new AdministrateurResponseDTO(
                "Morgan",
                "Dylan",
                "dylan@mail.com",
                "CEO"
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateurAModifier));
        Mockito.when(mockMapper.toAdministrateur(requestDTO)).thenReturn(administrateurInfosAModifier);
        Mockito.when(mockDao.save(administrateurAEnregistrer)).thenReturn(administrateurEnregistre);
        Mockito.when(mockMapper.toAdministrateurResponseDTO(administrateurEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, administrateurService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(administrateurAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier password non conforme), exception levée")
    @Test
    void testModifierPasswordNonConforme() {
        Administrateur administrateurAModifier = creerAdministrateurDylan();

        AdministrateurRequestDTO requestDTO = AdministrateurRequestDTO.builder()
                .password("password")
                .build();

        Administrateur administrateurInfosAModifier = Administrateur.builder()
                .password("password")
                .build();

        Administrateur administrateurAEnregistrer = creerAdministrateurDylan();
        administrateurAEnregistrer.setPassword(administrateurInfosAModifier.getPassword());

        Administrateur administrateurEnregistre = creerAdministrateurDylan();
        administrateurEnregistre.setPassword(administrateurInfosAModifier.getPassword());

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateurAModifier));
        Mockito.when(mockMapper.toAdministrateur(requestDTO)).thenReturn(administrateurInfosAModifier);

        assertThrows(AdministrateurException.class, () -> administrateurService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(email ok, password ok, modifier password)")
    @Test
    void testModifierPasswordOK() {
        Administrateur administrateurAModifier = creerAdministrateurDylan();

        AdministrateurRequestDTO requestDTO = AdministrateurRequestDTO.builder()
                .password("P@55w0rd1")
                .build();

        Administrateur administrateurInfosAModifier = Administrateur.builder()
                .password("P@55w0rd1")
                .build();

        Administrateur administrateurAEnregistrer = creerAdministrateurDylan();
        administrateurAEnregistrer.setPassword(administrateurInfosAModifier.getPassword());

        Administrateur administrateurEnregistre = creerAdministrateurDylan();
        administrateurEnregistre.setPassword(administrateurInfosAModifier.getPassword());

        AdministrateurResponseDTO responseDTO = new AdministrateurResponseDTO(
                "Morgan",
                "Dylan",
                "dylan@mail.com",
                "CEO"
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateurAModifier));
        Mockito.when(mockMapper.toAdministrateur(requestDTO)).thenReturn(administrateurInfosAModifier);
        Mockito.when(mockDao.save(administrateurAEnregistrer)).thenReturn(administrateurEnregistre);
        Mockito.when(mockMapper.toAdministrateurResponseDTO(administrateurEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, administrateurService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(administrateurAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier fonction)")
    @Test
    void testModifierFonction() {
        Administrateur administrateurAModifier = creerAdministrateurDylan();

        AdministrateurRequestDTO requestDTO = AdministrateurRequestDTO.builder()
                .fonction("Sultan")
                .build();

        Administrateur administrateurInfosAModifier = Administrateur.builder()
                .fonction("Sultan")
                .build();

        Administrateur administrateurAEnregistrer = creerAdministrateurDylan();
        administrateurAEnregistrer.setFonction(administrateurInfosAModifier.getFonction());

        Administrateur administrateurEnregistre = creerAdministrateurDylan();
        administrateurEnregistre.setFonction(administrateurInfosAModifier.getFonction());

        AdministrateurResponseDTO responseDTO = new AdministrateurResponseDTO(
                "Morgan",
                "Dylan",
                "dylan@mail.com",
                "Sultan"
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(administrateurAModifier));
        Mockito.when(mockMapper.toAdministrateur(requestDTO)).thenReturn(administrateurInfosAModifier);
        Mockito.when(mockDao.save(administrateurAEnregistrer)).thenReturn(administrateurEnregistre);
        Mockito.when(mockMapper.toAdministrateurResponseDTO(administrateurEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, administrateurService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(administrateurAEnregistrer);
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

    private static Administrateur creerAdministrateurVictorien() {
        return new Administrateur(
                "Temple",
                "Victorien",
                "victorien@mail.com",
                "P@55w0rd",
                "CTO"
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