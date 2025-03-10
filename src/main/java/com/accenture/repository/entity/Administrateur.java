package com.accenture.repository.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

@Entity
@DiscriminatorValue("Admin")
public class Administrateur extends UtilisateurConnecte {

    private String fonction;

    @Builder
    public Administrateur(String nom, String prenom, String email, String password, String fonction) {
        super(nom, prenom, email, password);
        this.fonction = fonction;
    }
}
