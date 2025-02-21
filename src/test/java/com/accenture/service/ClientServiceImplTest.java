package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.service.mapper.ClientMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}