package com.example.persona.domain.exception;

public class PersonaNoEncontradaException extends RuntimeException {

    public PersonaNoEncontradaException(String identificacion) {
        super("No existe una persona con identificacion: " + identificacion);
    }
}
