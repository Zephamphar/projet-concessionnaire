package com.accenture.repository.entity;

import com.accenture.shared.Permis;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
@DiscriminatorValue("Client")
public class Client extends UtilisateurConnecte {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Adresse adresse;
    private LocalDate dateDeNaissance;
    private LocalDate dateDInscription;
    private HashSet<Permis> permis;
    private Boolean desactive;

    public Client(String nom, String prenom, String email, String password, Adresse adresse, LocalDate dateDeNaissance, LocalDate dateDInscription, HashSet<Permis> permis, Boolean desactive) {
        super(nom, prenom, email, password);
        this.adresse = adresse;
        this.dateDeNaissance = dateDeNaissance;
        this.dateDInscription = dateDInscription;
        this.permis = new HashSet<>();
        this.desactive = desactive;
    }



}
