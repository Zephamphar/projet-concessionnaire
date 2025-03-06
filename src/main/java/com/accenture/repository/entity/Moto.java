package com.accenture.repository.entity;

import com.accenture.shared.Permis;
import com.accenture.shared.Transmission;
import com.accenture.shared.TypeMoto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@DiscriminatorValue("MOTO")
public class Moto extends Vehicule {

    private int nombreCylindres;
    private int cylindree;
    private int poids;
    private int puissance;
    private int hauteurSelle;
    private Transmission transmission;
    private TypeMoto typeMoto;
    private Permis permis;

    public Moto(String marque, String modele, String couleur, int tarifJournalier, int kilometrage, boolean actif, boolean retireDuParc, int nombreCylindres, int cylindree, int poids, int puissance, int hauteurSelle, Transmission transmission, TypeMoto typeMoto, Permis permis) {
        super(marque, modele, couleur, tarifJournalier, kilometrage, actif, retireDuParc);
        this.nombreCylindres = nombreCylindres;
        this.cylindree = cylindree;
        this.poids = poids;
        this.puissance = puissance;
        this.hauteurSelle = hauteurSelle;
        this.transmission = transmission;
        this.typeMoto = typeMoto;
        this.permis = permis;
    }
}
