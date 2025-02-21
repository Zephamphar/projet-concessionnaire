package com.accenture.service.dto;

import java.time.LocalDateTime;

public record ErreurReponse(LocalDateTime dateHeure, String type, String message) {
}
