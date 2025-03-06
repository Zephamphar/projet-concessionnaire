package com.accenture.service;

import com.accenture.exception.MotoException;
import com.accenture.repository.MotoDAO;
import com.accenture.repository.entity.Moto;
import com.accenture.service.dto.MotoRequestDTO;
import com.accenture.service.dto.MotoResponseDTO;
import com.accenture.service.dto.VehiculeDTO;
import com.accenture.service.mapper.MotoMapper;
import com.accenture.shared.Permis;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoServiceImpl implements MotoService {

    MotoDAO motoDAO;
    MotoMapper motoMapper;

    public MotoServiceImpl(MotoDAO motoDAO, MotoMapper motoMapper) {
        this.motoDAO = motoDAO;
        this.motoMapper = motoMapper;
    }

    @Override
    public MotoResponseDTO ajouter(MotoRequestDTO motoRequestDTO) throws MotoException {
        Moto moto = motoMapper.toMoto(motoRequestDTO);

        verifierEtCalculerMoto(moto);

        return motoMapper.toMotoResponseDTO(motoDAO.save(moto));
    }

    @Override
    public MotoResponseDTO modifier(int idMoto, MotoRequestDTO motoRequestDTO) throws EntityNotFoundException, MotoException {
        return null;
    }

    @Override
    public List<MotoResponseDTO> recupererToutes() {
        return motoDAO.findAll().stream()
                .map(motoMapper::toMotoResponseDTO)
                .toList();
    }

    @Override
    public MotoResponseDTO recuperer(int id) throws EntityNotFoundException {

        Moto moto = motoDAO
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucune moto à cet ID."));

        return motoMapper.toMotoResponseDTO(moto);
    }

    @Override
    public void supprimer(int id) throws EntityNotFoundException {
        Moto moto = motoDAO
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aucune moto à cet ID."));

        motoDAO.delete(moto);
    }

    /* ******************************** *
     *         Méthodes privées         *
     * ******************************** */

    private void verifierEtCalculerMoto(Moto moto) {
        if (moto == null)
            throw new MotoException("La Moto ne peut pas être null.");
        if (moto.getMarque() == null || moto.getMarque().isBlank())
            throw new MotoException("La marque est obligatoire");
        if (moto.getModele() == null || moto.getModele().isBlank())
            throw new MotoException("Le modele est obligatoire.");
        if (moto.getCouleur() == null || moto.getCouleur().isBlank())
            throw new MotoException("La couleur est obligatoire.");
        if (moto.getNombreCylindres() <= 0)
            throw new MotoException("Le nombre de cylindres est obligatoire");
        if(moto.getCylindree() <= 0)
            throw new MotoException("La cylindrée est obligatoire.");
        if(moto.getPoids() <= 0)
            throw new MotoException("Le poids est obligatoire.");
        if(moto.getPuissance() <= 0)
            throw new MotoException("La puissance est obligatoire.");
        if(moto.getHauteurSelle() <= 0)
            throw new MotoException("La hauteur de selle est obligatoire.");
        if(moto.getTransmission() == null)
            throw new MotoException("Le type de transmission est obligatoire.");
        if(moto.getTypeMoto() == null)
            throw new MotoException("Le type de moto est obligatoire.");
        if(moto.getTarifJournalier() <= 0)
            throw new MotoException("Le tarif journalier est obligatoire.");
        if(moto.getKilometrage() <= 0)
            throw new MotoException("Le kilometrage est obligatoire.");

        moto.setActif(true);
        moto.setRetireDuParc(false);

        if(moto.getCylindree() <= 125 && moto.getPuissance() <= 11)
            moto.setPermis(Permis.A1);
        else if(moto.getPuissance() <= 35)
            moto.setPermis(Permis.A2);
        else moto.setPermis(Permis.A);
    }
}
