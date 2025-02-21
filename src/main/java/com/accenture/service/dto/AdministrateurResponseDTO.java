package com.accenture.service.dto;

public record AdministrateurResponseDTO(
    int id,
    String nom,
    String prenom,
    String email,
    String password,
    String fonction
) {
}
