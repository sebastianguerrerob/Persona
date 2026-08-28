package com.example.persona.domain.usecase;

import com.example.persona.domain.model.Persona;
import com.example.persona.domain.api.IPersonaServicePort;
import com.example.persona.domain.exception.PersonaNoEncontradaException;
import com.example.persona.domain.spi.PersonaRepositoryPort;

public class PersonaUseCase implements IPersonaServicePort {

    private final PersonaRepositoryPort repository;

    public PersonaUseCase(PersonaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Persona guardar(Persona persona) {
        return repository.guardar(persona);
    }

    @Override
    public Persona consultar(String identificacion) {
        return repository.consultarPorIdentificacion(identificacion)
                .orElseThrow(() -> new PersonaNoEncontradaException(identificacion));
    }
}
