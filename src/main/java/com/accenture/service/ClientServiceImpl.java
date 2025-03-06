package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDAO;
import com.accenture.repository.entity.Adresse;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDTO;
import com.accenture.service.dto.ClientResponseDTO;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * Classe de Service gérant l'entité Client
 *
 * @author paulin.novotny
 * @since 1.0
 */

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientDAO clientDAO;
    private final ClientMapper clientMapper;
    private final String REGEX_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public ClientServiceImpl(ClientDAO clientDAO, ClientMapper clientMapper) {
        this.clientDAO = clientDAO;
        this.clientMapper = clientMapper;
    }

    /**
     * <p>Méthode permettant d'ajouter un Client en base</p>
     *
     * @param clientRequestDTO L'objet métier client, non null
     * @return ClientResponseDTO
     * @throws ClientException Si le client ne répond pas aux règles métier
     */
    @Override
    public ClientResponseDTO ajouter(ClientRequestDTO clientRequestDTO) throws ClientException {
        verifierClient(clientRequestDTO);
        Client client = clientMapper.toClient(clientRequestDTO);

        client.setDateDInscription(LocalDate.now());
        client.setDesactive(false);

        Client clientRetour = clientDAO.save(client);
        return clientMapper.toClientResponseDTO(clientRetour);
    }

    /**
     * <p>Méthode permettant à un Client de récupérer les informations de son compte</p>
     * @param email Email du Client
     * @param password Mot de passe du Client
     * @return ClientResponseDTO
     * @throws EntityNotFoundException Si l'email demandé n'existe pas en base
     * @throws ClientException Si l'email ou le mot de passe est null ou blank
     */

    @Override
        public ClientResponseDTO recupererMonCompte(String email, String password) throws EntityNotFoundException, ClientException {
        if(email == null || email.isBlank())
            throw new ClientException("L'email est obligatoire.");
        if(password == null || password.isBlank())
            throw new ClientException("Le mot de passe est obligatoire.");
        return clientMapper
                .toClientResponseDTO(
                        clientDAO.findByEmailAndPassword(email, password)
                                .orElseThrow(() -> new ClientException("Identifiants incorrects."))
                );
    }

    /**
     * <p>Méthode permettant de récupérer tous les clients de la base</p>
     *
     * @return Tous les clients de la base dans une ArrayList
     */

    @Override
    public List<ClientResponseDTO> trouverTous() {
        return clientDAO.findAll()
                .stream()
                .map(clientMapper::toClientResponseDTO)
                .toList();
    }

    /**
     * <p>Méthode permettant de modifier les informations d'un Client</p>
     * @param email E-mail du Client à modifier
     * @param password Mot de passe du Client à modifier
     * @param clientRequestDTO ClientRequestDTO contenant les nouvelles valeurs à modifier
     * @return La ClientResponseDTO mappée à partir du Client modifié
     */

    @Override
    public ClientResponseDTO modifier(String email, String password, ClientRequestDTO clientRequestDTO) {
        if(clientRequestDTO == null)
            throw new ClientException("Aucune information reçue.");
        Optional<Client> optionalClient = clientDAO.findByEmailAndPassword(email, password);
        Client clientAModifier = optionalClient.orElseThrow(() -> new ClientException("Identifiants invalides"));

        Client nouveauClient = clientMapper.toClient(clientRequestDTO);

        remplacer(nouveauClient, clientAModifier);

        Client clientEnregistre = clientDAO.save(clientAModifier);
        return clientMapper.toClientResponseDTO(clientEnregistre);
    }

    /**
     * <p>Méthode permettant de supprimer un Client de la base. Si le client a des locations passées, il est simplement désactivé.</p>
     * @param email E-mail du Client à supprimer
     * @param password Mot de passe du Client à supprimer
     * @throws ClientException Si le Client ne fournit pas d'identifiants ou si ils sont incorrects
     */

    @Override
    public void supprimer(String email, String password) throws ClientException {
        if(email == null || email.isBlank())
            throw new ClientException("L'email est obligatoire.");
        if(password == null || password.isBlank())
            throw new ClientException("Le mot de passe est obligatoire.");
        Client client = clientDAO.findByEmailAndPassword(email, password)
                                .orElseThrow(() -> new ClientException("Identifiants incorrects."));
        clientDAO.delete(client);

        /*TODO Vérifier si le Client a des locations passées. Si oui, désactiver son compte. Si non, supprimer son compte.
        À implémenter après que la gestion des locations a été mise en place*/
    }

    /* ************************************ *
     *          Méthodes privées            *
     * ************************************ */

    private void verifierClient(ClientRequestDTO clientRequestDTO) throws ClientException {
        if (clientRequestDTO == null)
            throw new ClientException("Le client ne peut pas être null.");
        if (clientDAO.existsByEmail(clientRequestDTO.email()))
            throw new ClientException("Cet email est déjà utilisé.");
        if (clientRequestDTO.nom() == null || clientRequestDTO.nom().isBlank())
            throw new ClientException("Le nom du client est obligatoire.");
        if (clientRequestDTO.prenom() == null || clientRequestDTO.prenom().isBlank())
            throw new ClientException("Le prenom du client est obligatoire.");
        if (clientRequestDTO.adresse() == null)
            throw new ClientException("L'adresse ne peut pas être null.");
        if (clientRequestDTO.adresse().numero() == 0)
            throw new ClientException("Le numéro d'adresse est obligatoire.");
        if (clientRequestDTO.adresse().rue() == null || clientRequestDTO.adresse().rue().isBlank())
            throw new ClientException("La rue est obligatoire.");
        if (clientRequestDTO.adresse().codePostal() == null || clientRequestDTO.adresse().codePostal().isBlank())
            throw new ClientException("Le code postal est obligatoire.");
        if (clientRequestDTO.adresse().ville() == null || clientRequestDTO.adresse().ville().isBlank())
            throw new ClientException("La ville est obligatoire.");
        if (clientRequestDTO.email() == null || clientRequestDTO.email().isBlank())
            throw new ClientException("L'e-mail est obligatoire.");
        if (clientRequestDTO.password() == null || clientRequestDTO.password().isBlank())
            throw new ClientException("Le mot de passe est obligatoire.");
        if (!clientRequestDTO.password().matches(REGEX_PASSWORD))
            throw new ClientException("Le mot de passe doit comporter au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial parmi les suivants : & # @ - _ §");
        if (clientRequestDTO.dateDeNaissance() == null)
            throw new ClientException("La date de naissance est obligatoire");
        if (Period.between(clientRequestDTO.dateDeNaissance(), LocalDate.now()).getYears() < 18)
            throw new ClientException("Le client doit être majeur pour s'inscrire.");
    }

    private void remplacer(Client nouveauClient, Client clientAModifier) {
        if(nouveauClient.getPassword() != null && !nouveauClient.getPassword().isBlank()) {
            if (!nouveauClient.getPassword().matches(REGEX_PASSWORD))
                throw new ClientException("Le mot de passe doit comporter au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial parmi les suivants : & # @ - _ §");
            clientAModifier.setPassword(nouveauClient.getPassword());
        }
        if(nouveauClient.getPrenom() != null && !nouveauClient.getPrenom().isBlank())
            clientAModifier.setPrenom(nouveauClient.getPrenom());
        if(nouveauClient.getNom() != null && !nouveauClient.getNom().isBlank())
            clientAModifier.setNom(nouveauClient.getNom());
        if(nouveauClient.getAdresse() != null) {
            if(nouveauClient.getAdresse().getNumero() != 0)
                clientAModifier.getAdresse().setNumero(nouveauClient.getAdresse().getNumero());
            if(nouveauClient.getAdresse().getRue() != null && !nouveauClient.getAdresse().getRue().isBlank())
                clientAModifier.getAdresse().setRue(nouveauClient.getAdresse().getRue());
            if(nouveauClient.getAdresse().getCodePostal() != null && !nouveauClient.getAdresse().getCodePostal().isBlank())
                clientAModifier.getAdresse().setCodePostal(nouveauClient.getAdresse().getCodePostal());
            if(nouveauClient.getAdresse().getVille() != null && !nouveauClient.getAdresse().getVille().isBlank())
                clientAModifier.getAdresse().setVille(nouveauClient.getAdresse().getVille());
        }
        if(nouveauClient.getDateDeNaissance() != null)
            clientAModifier.setDateDeNaissance(nouveauClient.getDateDeNaissance());
        if(nouveauClient.getPermis() != null && !nouveauClient.getPermis().isEmpty()) {
            clientAModifier.getPermis().clear();
            clientAModifier.getPermis().addAll(nouveauClient.getPermis());
        }
    }

}
