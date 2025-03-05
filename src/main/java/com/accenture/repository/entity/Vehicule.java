package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DISCR")
public abstract class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String marque;
    private String modele;
    private String couleur;

    private int tarifJournalier;
    private int kilometrage;
    private boolean actif;
    private boolean retireDuParc;

    public Vehicule(String marque, String modele, String couleur, int tarifJournalier, int kilometrage, boolean actif, boolean retireDuParc) {
        this.marque = marque;
        this.modele = modele;
        this.couleur = couleur;
        this.tarifJournalier = tarifJournalier;
        this.kilometrage = kilometrage;
        this.actif = actif;
        this.retireDuParc = retireDuParc;
    }
}
