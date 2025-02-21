package com.accenture.service;

import com.accenture.exception.AdministrateurException;
import com.accenture.repository.AdministrateurDAO;
import com.accenture.repository.entity.Administrateur;
import com.accenture.service.dto.AdministrateurRequestDTO;
import com.accenture.service.dto.AdministrateurResponseDTO;
import com.accenture.service.mapper.AdministrateurMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdministrateurServiceImpl implements AdministrateurService {

    private final AdministrateurDAO administrateurDAO;
    public final AdministrateurMapper administrateurMapper;
    private final String REGEX_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#@&-_§]).{8,16}$";

    public AdministrateurServiceImpl(AdministrateurDAO administrateurDAO, AdministrateurMapper administrateurMapper) {
        this.administrateurDAO = administrateurDAO;
        this.administrateurMapper = administrateurMapper;
    }

    /**
     * <p>Méthode permettant d'ajouter un Client en base</p>
     * @param administrateurRequestDTO L'objet métier administrateur, non null
     * @return AdministrateurResponseDTO
     * @throws AdministrateurException Si l'Administrateur ne répond pas aux règles métier
     */

    @Override
    public AdministrateurResponseDTO ajouter(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
        verifierAdministrateur(administrateurRequestDTO);
        Administrateur admin = administrateurMapper.toAdministrateur(administrateurRequestDTO);

        Administrateur adminRetour = administrateurDAO.save(admin);
        return administrateurMapper.toAdministrateurResponseDTO(adminRetour);
    }

    /**
     * <p>Méthode permettant de récupérer tous les administrateurs de la base</p>
     * @return Tous les administrateurs de la base dans une ArrayList
     */

    @Override
    public List<AdministrateurResponseDTO> trouverTous() {
        return administrateurDAO.findAll()
                .stream()
                .map(administrateurMapper::toAdministrateurResponseDTO)
                .toList();
    }

    private void verifierAdministrateur(AdministrateurRequestDTO administrateurRequestDTO) throws AdministrateurException {
        if(administrateurRequestDTO == null)
           throw new AdministrateurException("L'administrateur ne peut pas être null");
        if(administrateurDAO.existsByEmail(administrateurRequestDTO.email()))
            throw new AdministrateurException("Cet email est déjà utilisé.");
        if(administrateurRequestDTO.nom() == null || administrateurRequestDTO.nom().isBlank())
           throw new AdministrateurException("Le nom est obligatoire.");
        if(administrateurRequestDTO.prenom() == null || administrateurRequestDTO.prenom().isBlank())
           throw new AdministrateurException("Le prenom est obligatoire.");
        if(administrateurRequestDTO.email() == null || administrateurRequestDTO.email().isBlank())
           throw new AdministrateurException("L'e-mail est obligatoire.");
        if(administrateurRequestDTO.password() == null || administrateurRequestDTO.password().isBlank())
           throw new AdministrateurException("Le mot de passe est obligatoire.");
        if(!administrateurRequestDTO.password().matches(REGEX_PASSWORD))
            throw new AdministrateurException("Le mot de passe doit comporter au moins une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial parmi les suivants : & # @ - _ §");
        if(administrateurRequestDTO.fonction() == null || administrateurRequestDTO.fonction().isBlank())
            throw new AdministrateurException("La fonction est obligatoire");
    }


}
