package com.example.persona.infrastructure.adapter.in.web;

import com.example.persona.domain.model.Persona;
import com.example.persona.domain.api.IPersonaServicePort;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personas")
@Tag(name = "Personas", description = "Operaciones para guardar y consultar personas")
public class PersonaController {

    private final IPersonaServicePort personaUseCase;

    public PersonaController(IPersonaServicePort personaUseCase) {
        this.personaUseCase = personaUseCase;
    }

    @PostMapping("/guardarpersona")
        @Operation(summary = "Guardar persona", description = "Registra una persona con identificacion, nombre y email")
        @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Persona creada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos")
        })
    public ResponseEntity<PersonaResponse> guardar(@Valid @RequestBody PersonaRequest request) {
        Persona persona = new Persona(request.identificacion(), request.nombre(), request.email());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PersonaResponse.from(personaUseCase.guardar(persona)));
    }

    @GetMapping("/consultarpersona/{identificacion}")
        @Operation(summary = "Consultar persona", description = "Busca una persona por su numero de identificacion")
        @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Persona encontrada"),
            @ApiResponse(responseCode = "404", description = "Persona no encontrada")
        })
        public PersonaResponse consultar(@Parameter(description = "Numero de identificacion", example = "123456789")
                         @PathVariable String identificacion) {
        return PersonaResponse.from(personaUseCase.consultar(identificacion));
    }
}
