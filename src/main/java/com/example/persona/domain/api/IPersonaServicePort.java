package com.example.persona.domain.api;

import com.example.persona.domain.model.Persona;

public interface IPersonaServicePort {

    Persona guardar(Persona persona);

    Persona consultar(String identificacion);
}
