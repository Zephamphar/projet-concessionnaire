package com.accenture.repository.entity;

import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeCarburant;
import com.accenture.shared.TypeVoiture;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("VOITURE")
public class Voiture extends Vehicule {

    private int nombrePlaces;
    private TypeCarburant typeCarburant;
    private int nombrePortes;
    private Transmission transmission;
    private boolean climatisation;
    private int nombreBagages;
    private TypeVoiture typeVoiture;
    private Permis permis;

    public Voiture(String marque, String modele, String couleur, int nombrePlaces, TypeCarburant typeCarburant, int nombrePortes, Transmission transmission, boolean climatisation, int nombreBagages, TypeVoiture typeVoiture, Permis permis,  int tarifJournalier, int kilometrage, boolean actif, boolean retireDuParc) {
        super(marque, modele, couleur, tarifJournalier, kilometrage, actif, retireDuParc);
        this.nombrePlaces = nombrePlaces;
        this.typeCarburant = typeCarburant;
        this.nombrePortes = nombrePortes;
        this.transmission = transmission;
        this.climatisation = climatisation;
        this.nombreBagages = nombreBagages;
        this.typeVoiture = typeVoiture;
        this.permis = permis;
    }
}
