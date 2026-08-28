package com.example.persona.infrastructure.adapter.in.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PersonaRequest(
        @NotBlank(message = "La identificacion es obligatoria") String identificacion,
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El email es obligatorio") @Email(message = "El email no es valido") String email) {
}
