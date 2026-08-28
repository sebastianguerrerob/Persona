package com.example.persona.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataPersonaRepository extends JpaRepository<PersonaEntity, String> {
}
