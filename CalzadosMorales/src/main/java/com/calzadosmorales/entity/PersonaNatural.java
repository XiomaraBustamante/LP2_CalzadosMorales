package com.calzadosmorales.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "persona_natural")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class PersonaNatural extends Cliente {

    @Column(unique = true, length = 8)
    private String dni;
    
    private String nombre;
    private String apellido;
    private Integer genero;

    // GETTERS Y SETTERS
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }
}