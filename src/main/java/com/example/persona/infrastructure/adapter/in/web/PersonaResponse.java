package com.example.persona.infrastructure.adapter.in.web;

import com.example.persona.domain.model.Persona;

public record PersonaResponse(String identificacion, String nombre, String email) {

    public static PersonaResponse from(Persona persona) {
        return new PersonaResponse(persona.identificacion(), persona.nombre(), persona.email());
    }
}
