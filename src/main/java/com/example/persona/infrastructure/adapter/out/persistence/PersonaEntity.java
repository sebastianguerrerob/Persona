package com.example.persona.infrastructure.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas")
public class PersonaEntity {

    @Id
    private String identificacion;
    private String nombre;
    private String email;

    protected PersonaEntity() {
    }

    public PersonaEntity(String identificacion, String nombre, String email) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.email = email;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
