package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.service.dto.AdresseDTO;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.mapper.ClientMapper;
import com.shared.Permis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    ClientMapper clientMapper;
    @Mock
    ClientDAO clientDAO;
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
        Mockito.when(clientDAO.existsByEmail("dylan@mail.com")).thenReturn(true);
        ClientRequestDTO dylan = creerClientDylan();
        assertThrows(ClientException.class, () -> clientService.ajouter(dylan));
    }



    private static ClientRequestDTO creerClientDylan() {
        HashSet<Permis> permis = new HashSet<>();
        permis.add(Permis.B);
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


}