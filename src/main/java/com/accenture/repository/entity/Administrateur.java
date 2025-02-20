package com.accenture.repository.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@DiscriminatorValue("Admin")
public class Administrateur extends UtilisateurConnecte {

    private String fonction;

}
