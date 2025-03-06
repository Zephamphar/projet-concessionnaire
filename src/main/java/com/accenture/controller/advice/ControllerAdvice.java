package com.accenture.controller.advice;

import com.accenture.exception.AdministrateurException;
import com.accenture.exception.ClientException;
import com.accenture.exception.MotoException;
import com.accenture.exception.VoitureException;
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
        ErreurReponse erreurReponse = new ErreurReponse(LocalDateTime.now(), "Erreur de gestion Clien", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurReponse);
    }

    @ExceptionHandler(AdministrateurException.class)
    public ResponseEntity<ErreurReponse> gestionClientException(AdministrateurException ex) {
        ErreurReponse erreurReponse = new ErreurReponse(LocalDateTime.now(), "Erreur de gestion Administrateur", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurReponse);
    }

    @ExceptionHandler(VoitureException.class)
    public ResponseEntity<ErreurReponse> gestionVoitureException(VoitureException ex) {
        ErreurReponse erreurReponse = new ErreurReponse(LocalDateTime.now(), "Erreur de gestion Voiture", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurReponse);
    }

    @ExceptionHandler(MotoException.class)
    public ResponseEntity<ErreurReponse> gestionVoitureException(MotoException ex) {
        ErreurReponse erreurReponse = new ErreurReponse(LocalDateTime.now(), "Erreur de gestion Moto", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erreurReponse);
    }

}
