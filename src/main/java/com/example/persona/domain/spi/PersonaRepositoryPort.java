package com.example.persona.domain.spi;

import com.example.persona.domain.model.Persona;

import java.util.Optional;

public interface PersonaRepositoryPort {

    Persona guardar(Persona persona);

    Optional<Persona> consultarPorIdentificacion(String identificacion);
}
