package com.accenture.repository.entity;

import com.accenture.shared.EtatLocation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Vehicule vehicule;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int kmParcourus;
    private int montantTotal;
    private LocalDate dateValidation;
    private EtatLocation etatLocation;

    public Location(Client client, Vehicule vehicule, LocalDate dateDebut, LocalDate dateFin, int kmParcourus, int montantTotal, LocalDate dateValidation, EtatLocation etatLocation) {
        this.client = client;
        this.vehicule = vehicule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.kmParcourus = kmParcourus;
        this.montantTotal = montantTotal;
        this.dateValidation = dateValidation;
        this.etatLocation = etatLocation;
    }
}
