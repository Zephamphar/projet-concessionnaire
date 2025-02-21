package com.accenture.controller.advice;

import com.accenture.exception.ClientException;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ErreurReponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(ClientException ex) {
        ErreurReponse erreurReponse = new ErreurReponse(LocalDateTime.now(), "Erreur fonctionnelle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurReponse);
    }

}
