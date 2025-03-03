package com.accenture.service.dto;

import lombok.Builder;

@Builder
public record AdministrateurRequestDTO(
        String nom,
        String prenom,
        String password,
        String email,
        String fonction
) {
}
