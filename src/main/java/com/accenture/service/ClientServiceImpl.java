package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Location;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import com.shared.Permis;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    ClientDAO clientDAO;
    ClientMapper clientMapper;
    private final String REGEX_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public ClientServiceImpl(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) {
        verifierClient(clientRequestDTO);
        Client client = clientMapper.toClient(clientRequestDTO);

        client.setDateDInscription(LocalDate.now());
        client.setDesactive(false);

        Client clientRetour = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientRetour);
    }

    @Override
    public ClientResponseDTO trouver(int id) {
        return null;
    }

    @Override
    public List<ClientResponseDTO> trouverTous() {
        return List.of();
    }

    @Override
    public ClientResponseDTO modifier(int id, ClientRequestDTO clientRequestDTO) {
        return null;
    }

    @Override
    public ClientResponseDTO modifierPartiellement(int id, ClientRequestDTO clientRequestDTO) {
        return null;
    }

    @Override
    public void supprimer(int id) {
        //TODO
    }

    /* ************************************ *
     *          Méthodes privées            *
     * ************************************ */

    private void verifierClient(ClientRequestDTO clientRequestDTO) throws ClientException {
        if(clientRequestDTO == null)
            throw new ClientException("Le client ne peut pas être .");
        if(clientRequestDTO.nom() == null || clientRequestDTO.nom().isBlank())
            throw new ClientException("Le nom du client est obligatoire.");
        if(clientRequestDTO.prenom() == null || clientRequestDTO.prenom().isBlank())
            throw new ClientException("Le prenom du client est obligatoire.");
        if(clientRequestDTO.adresse() == null)
            throw new ClientException("L'adresse ne peut pas être null.");
        if(clientRequestDTO.adresse().getNumero() == 0)
            throw new ClientException("Le numéro d'adresse est obligatoire.");
        if(clientRequestDTO.adresse().getRue() == null || clientRequestDTO.adresse().getRue().isBlank())
            throw new ClientException("La rue est obligatoire.");
        if(clientRequestDTO.adresse().getCodePostal() == null || clientRequestDTO.adresse().getCodePostal().isBlank())
            throw new ClientException("Le code postal est obligatoire.");
        if(clientRequestDTO.adresse().getVille() == null || clientRequestDTO.adresse().getVille().isBlank())
            throw new ClientException("La ville est obligatoire.");
        if(clientRequestDTO.email() == null || clientRequestDTO.email().isBlank())
            throw new ClientException("L'e-mail est obligatoire.");
        if(clientRequestDTO.password() == null || clientRequestDTO.password().isBlank())
            throw new ClientException("Le mot de passe est obligatoire.");
        if(!clientRequestDTO.password().matches(REGEX_PASSWORD))
            throw new ClientException("Le mot de passe doit comporter au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial parmi les suivants : & # @ - _ §");
        if(clientRequestDTO.dateDeNaissance() == null)
            throw new ClientException("La date de naissance est obligatoire");
        if(LocalDate.now().minus(Period.between(clientRequestDTO.dateDeNaissance(), LocalDate.now())).getYear() < 18)
            throw new ClientException("Le client doit être majeur pour s'inscrire.");
    }

//    String nom,
//    String prenom,
//    Adresse adresse,
//    String email,
//    String password,
//    LocalDate dateDeNaissance,
//    LocalDate dateDInscription,
//    HashSet<Permis> permis,
//    Boolean desactive,
//    List<Location> locations
}
