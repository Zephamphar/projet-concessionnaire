package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AdresseDTO;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import com.accenture.shared.Permis;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.mock.MockType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientMapper mockMapper;
    @Mock
    ClientDAO mockDao;
    @InjectMocks
    ClientServiceImpl clientService;

    @DisplayName("Si ajouter(null), exception levée")
    @Test
    void testAjouterClientNull() {
        assertThrows(ClientException.class, () -> clientService.ajouter(null));
    }

    @DisplayName("Si l'email est déjà enregistré en base, exception levée")
    @Test
    void testAjouterClientExisteDeja() {
        Mockito.when(mockDao.existsByEmail("dylan@mail.com")).thenReturn(true);
        ClientRequestDTO dylan = creerClientRequestDTODylan();
        assertThrows(ClientException.class, () -> clientService.ajouter(dylan));
    }

    @DisplayName("Si ajouter(nom null), exception levée")
    @Test
    void testAjouterClientNomNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                null,
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(nom blank), exception levée")
    @Test
    void testAjouterClientNomBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "\t   ",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(prenom null), exception levée")
    @Test
    void testAjouterClientPrenomNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                null,
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(prenom blank), exception levée")
    @Test
    void testAjouterClientPrenomBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "\t   ",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(adresse null), exception levée")
    @Test
    void testAjouterClientAdresseNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                null,
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(adresse numero 0), exception levée")
    @Test
    void testAjouterClientAdresseNumeroZero() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(0, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(Adresse rue null), exception levée")
    @Test
    void testAjouterClientAdresseRueNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, null, "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(Adresse rue blank), exception levée")
    @Test
    void testAjouterClientAdresseRueBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "   \t   ", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(adresse code postal null), exception levée")
    @Test
    void testAjouterClientAdresseCodePostalNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", null, "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(adresse code postal null), exception levée")
    @Test
    void testAjouterClientAdresseCodePostalBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "   \t   ", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }


    @DisplayName("Si ajouter(Adresse ville null), exception levée")
    @Test
    void testAjouterClientAdresseVilleNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", null),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(Adresse ville blank), exception levée")
    @Test
    void testAjouterClientAdresseVilleBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "   \t   "),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(email null), exception levée")
    @Test
    void testAjouterClientEmailNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                null,
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(email blank), exception levée")
    @Test
    void testAjouterClientEmailBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "   \t   ",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(date de naissance null), exception levée")
    @Test
    void testAjouterClientDateNaissanceNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                null,
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(date de naissance non majeur), exception levée")
    @Test
    void testAjouterClientClientNonMajeur() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(2012, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(password null), exception levée")
    @Test
    void testAjouterClientPasswordNull() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                null,
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(password blank), exception levée")
    @Test
    void testAjouterClientPasswordBlank() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "   \t   ",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(password non conforme), exception levée")
    @Test
    void testAjouterClientPasswordNonConforme() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientRequestDTO dto = new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "password",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
        assertThrows(ClientException.class, () -> clientService.ajouter(dto));
    }

    @DisplayName("Si ajouter(ok), on récupère la ClientResponseDTO")
    @Test
    void testAjouterClientOK() {
        ClientRequestDTO requestDTO = creerClientRequestDTODylan();
        ClientResponseDTO responseDTO = creerClientResponseDTODylan();
        Client clientAvant = creerClientDylan();
        Client clientApres = creerClientDylan();

        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientAvant);
        Mockito.when(mockDao.save(clientAvant)).thenReturn(clientApres);
        Mockito.when(mockMapper.toClientResponseDTO(clientApres)).thenReturn(responseDTO);

        assertSame(responseDTO, clientService.ajouter(requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAvant);

    }

    @DisplayName("Si recupererMonCompte(email null), exception levée")
    @Test
    void testRecupererMonCompteEmailNull() {
        assertThrows(ClientException.class, () -> clientService.recupererMonCompte(null, "P@55w0rd"));
    }

    @DisplayName("Si recupererMonCompte(email blank), exception levée")
    @Test
    void testRecupererMonCompteEmailBlank() {
        assertThrows(ClientException.class, () -> clientService.recupererMonCompte("   \t   ", "P@55w0rd"));
    }

    @DisplayName("Si recupererMonCompte(password null), exception levée")
    @Test
    void testRecupererMonComptePasswordNull() {
        assertThrows(ClientException.class, () -> clientService.recupererMonCompte("dylan@mail.com", null));
    }

    @DisplayName("Si recupererMonCompte(password blank), exception levée")
    @Test
    void testRecupererMonComptePasswordBlank() {
        assertThrows(ClientException.class, () -> clientService.recupererMonCompte("dylan@mail.com", "   \t   "));
    }

    @DisplayName("Si recupererMonCompte(email non trouvé), exception levée")
    @Test
    void testRecupererMonCompteEmailNonTrouve() {
        assertThrows(ClientException.class, () -> clientService.recupererMonCompte("test@teast.com", "P@55w0rd"));
    }

    @DisplayName("Si recupererMonCompte(ok), on récupère la ClientResponseDTO")
    @Test
    void testRecupererMonCompteOK() {
        ClientResponseDTO responseDTO = creerClientResponseDTODylan();
        Client client = creerClientDylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(client));
        Mockito.when(mockMapper.toClientResponseDTO(client)).thenReturn(responseDTO);

        assertSame(responseDTO, clientService.recupererMonCompte("dylan@mail.com", "P@55w0rd"));
    }

    @DisplayName("Si supprimer(null, ok), exception levée")
    @Test
    void testSupprimerEmailNull() {
        assertThrows(ClientException.class, () -> clientService.supprimer(null, "P@55w0rd"));
    }

    @DisplayName("Si supprimer(blank, ok), exception levée")
    @Test
    void testSupprimerEmailBlank() {
        assertThrows(ClientException.class, () -> clientService.supprimer("   \t   ", "P@55w0rd"));
    }

    @DisplayName("Si supprimer(ok, null), exception levée")
    @Test
    void testSupprimerPasswordNull() {
        assertThrows(ClientException.class, () -> clientService.supprimer("dylan@mail.com", null));
    }

    @DisplayName("Si supprimer(ok, blank), exception levée")
    @Test
    void testSupprimerPasswordBlank() {
        assertThrows(ClientException.class, () -> clientService.supprimer("dylan@mail.com", "   \t   "));
    }

    @DisplayName("Si supprimer(email incorrect), exception levée")
    @Test
    void testSupprimerEmailIncorrect() {
        Mockito.when(mockDao.findByEmailAndPassword("dlan@mail.com", "P@55w0rd")).thenReturn(Optional.empty());
        ClientException ex = assertThrows(ClientException.class, () -> clientService.supprimer("dlan@mail.com", "P@55w0rd"));
        assertEquals("Identifiants incorrects.", ex.getMessage());
    }

    @DisplayName("Si supprimer(password incorrect), exception levée")
    @Test
    void testSupprimerPasswordIncorrect() {
        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "p@55w0rd")).thenReturn(Optional.empty());
        ClientException ex = assertThrows(ClientException.class, () -> clientService.supprimer("dylan@mail.com", "p@55w0rd"));
        assertEquals("Identifiants incorrects.", ex.getMessage());
    }

    @DisplayName("Si supprimer(ok, ok)")
    @Test
    void testSupprimerOK() {
        Client client = creerClientDylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(client));
        clientService.supprimer("dylan@mail.com", "P@55w0rd");
        Mockito.verify(mockDao, Mockito.times(1)).delete(client);
    }

    @DisplayName("Si modifier(null), exception levée")
    @Test
    void testModifierNull() {
        assertThrows(ClientException.class, () -> clientService.modifier("dylan@mail.com", "P@55w0rd", null));
    }

    @DisplayName("Si modifier(email incorrect), exception levée")
    @Test
    void testModifierEmailIncorrect() {
        ClientRequestDTO requestDTO = creerClientRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("dlan@mail.com", "P@55w0rd")).thenReturn(Optional.empty());
        assertThrows(ClientException.class, () -> clientService.modifier("dlan@mail.com", "P@55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(password incorrect), exception levée")
    @Test
    void testModifierPasswordIncorrect() {
        ClientRequestDTO requestDTO = creerClientRequestDTODylan();

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P55w0rd")).thenReturn(Optional.empty());
        assertThrows(ClientException.class, () -> clientService.modifier("dylan@mail.com", "P55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(email ok, password ok, modifier prenom)")
    @Test
    void testModifierPrenom() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .prenom("Maleck Jr")
                .build();

        Client clientInfosAModifier = Client.builder()
                .prenom("Maleck Jr")
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.setPrenom(clientInfosAModifier.getPrenom());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.setPrenom(clientInfosAModifier.getPrenom());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Demasse",
                "Maleck Jr",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }


    @DisplayName("Si modifier(email ok, password ok, modifier nom)")
    @Test
    void testModifierNom() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .nom("Morgan")
                .build();

        Client clientInfosAModifier = Client.builder()
                .nom("Morgan")
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.setNom(clientInfosAModifier.getNom());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.setNom(clientInfosAModifier.getNom());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier password non conforme), exception levée")
    @Test
    void testModifierPasswordNonConforme() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .password("password")
                .build();

        Client clientInfosAModifier = Client.builder()
                .password("password")
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.setPassword(clientInfosAModifier.getPassword());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.setPassword(clientInfosAModifier.getPassword());

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);

        assertThrows(ClientException.class, () -> clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
    }

    @DisplayName("Si modifier(email ok, password ok, modifier password)")
    @Test
    void testModifierPasswordOK() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .password("P@55w0rd1")
                .build();

        Client clientInfosAModifier = Client.builder()
                .password("P@55w0rd1")
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.setPassword(clientInfosAModifier.getPassword());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.setPassword(clientInfosAModifier.getPassword());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier numero adresse)")
    @Test
    void testModifierAdresseNumero() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .adresse(new AdresseDTO(5, null, null, null))
                .build();

        Client clientInfosAModifier = Client.builder()
                .adresse(new Adresse(5, null, null, null))
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.getAdresse().setNumero(clientInfosAModifier.getAdresse().getNumero());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.getAdresse().setNumero(clientInfosAModifier.getAdresse().getNumero());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(5, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier rue adresse)")
    @Test
    void testModifierAdresseRue() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .adresse(new AdresseDTO(0, "avenue du Soleil", null, null))
                .build();

        Client clientInfosAModifier = Client.builder()
                .adresse(new Adresse(0, "avenue du Soleil", null, null))
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.getAdresse().setRue(clientInfosAModifier.getAdresse().getRue());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.getAdresse().setRue(clientInfosAModifier.getAdresse().getRue());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(5, "avenue du Soleil", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier code postal adresse)")
    @Test
    void testModifierAdresseCodePostal() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .adresse(new AdresseDTO(0, null, "06960", null))
                .build();

        Client clientInfosAModifier = Client.builder()
                .adresse(new Adresse(0, null, "06960", null))
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.getAdresse().setCodePostal(clientInfosAModifier.getAdresse().getCodePostal());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.getAdresse().setCodePostal(clientInfosAModifier.getAdresse().getCodePostal());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(5, "rue Hector Berlioz", "06960", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier ville adresse)")
    @Test
    void testModifierAdresseVille() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .adresse(new AdresseDTO(0, null, null, "Montpellier"))
                .build();

        Client clientInfosAModifier = Client.builder()
                .adresse(new Adresse(0, null, null, "Montpellier"))
                .build();

        Client clientAEnregistrer = creerClientDylan();
            clientAEnregistrer.getAdresse().setVille(clientInfosAModifier.getAdresse().getVille());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.getAdresse().setVille(clientInfosAModifier.getAdresse().getVille());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(5, "rue Hector Berlioz", "44300", "Montpellier"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier date de naissance)")
    @Test
    void testModifierDateNaissance() {
        Client clientAModifier = creerClientDylan();

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .dateDeNaissance(LocalDate.of(2000, 7, 17))
                .build();

        Client clientInfosAModifier = Client.builder()
                .dateDeNaissance(LocalDate.of(2000, 7, 17))
                .build();

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.setDateDeNaissance(clientInfosAModifier.getDateDeNaissance());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.setDateDeNaissance(clientInfosAModifier.getDateDeNaissance());

        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(2000, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }

    @DisplayName("Si modifier(email ok, password ok, modifier permis)")
    @Test
    void testModifierPermis() {
        Client clientAModifier = creerClientDylan();
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B, Permis.A));

        ClientRequestDTO requestDTO = ClientRequestDTO.builder()
                .permis(permis)
                .build();

        Client clientInfosAModifier = new Client();
        clientInfosAModifier.setPermis(permis);

        Client clientAEnregistrer = creerClientDylan();
        clientAEnregistrer.setPermis(clientInfosAModifier.getPermis());

        Client clientEnregistre = creerClientDylan();
        clientEnregistre.setPermis(clientInfosAModifier.getPermis());

        ClientResponseDTO responseDTO = new ClientResponseDTO(
                "Morgan",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(2000, 7, 17),
                LocalDate.now(),
                permis,
                false
        );

        Mockito.when(mockDao.findByEmailAndPassword("dylan@mail.com", "P@55w0rd")).thenReturn(Optional.of(clientAModifier));
        Mockito.when(mockMapper.toClient(requestDTO)).thenReturn(clientInfosAModifier);
        Mockito.when(mockDao.save(clientAEnregistrer)).thenReturn(clientEnregistre);
        Mockito.when(mockMapper.toClientResponseDTO(clientEnregistre)).thenReturn(responseDTO);

        assertEquals(responseDTO, clientService.modifier("dylan@mail.com", "P@55w0rd", requestDTO));
        Mockito.verify(mockDao, Mockito.times(1)).save(clientAEnregistrer);
    }


    private static Client creerClientDylan() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));

        return new Client(
                "Demasse",
                "Dylan",
                "dylan@mail.com",
                "P@55w0rd",
                new Adresse(48, "rue Hector Berlioz", "44300", "Nantes"),
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
    }

    private static ClientRequestDTO creerClientRequestDTODylan() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        return new ClientRequestDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                "P@55w0rd",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
    }

    private static ClientResponseDTO creerClientResponseDTODylan() {
        HashSet<Permis> permis = new HashSet<>(List.of(Permis.B));
        return new ClientResponseDTO(
                "Demasse",
                "Dylan",
                new AdresseDTO(48, "rue Hector Berlioz", "44300", "Nantes"),
                "dylan@mail.com",
                LocalDate.of(1999, 7, 17),
                LocalDate.now(),
                permis,
                false
        );
    }


}