package com.accenture.repository.entity;

import com.shared.Permis;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

}
