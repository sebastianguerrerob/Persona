package com.example.persona.infrastructure.adapter.out.persistence;

import com.example.persona.domain.model.Persona;
import com.example.persona.domain.spi.PersonaRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonaPersistenceAdapter implements PersonaRepositoryPort {

    private final SpringDataPersonaRepository repository;

    public PersonaPersistenceAdapter(SpringDataPersonaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entity = repository.save(new PersonaEntity(
                persona.identificacion(), persona.nombre(), persona.email()));
        return toDomain(entity);
    }

    @Override
    public Optional<Persona> consultarPorIdentificacion(String identificacion) {
        return repository.findById(identificacion).map(this::toDomain);
    }

    private Persona toDomain(PersonaEntity entity) {
        return new Persona(entity.getIdentificacion(), entity.getNombre(), entity.getEmail());
    }
}
