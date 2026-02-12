package com.calzadosmorales.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "persona_juridica")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class PersonaJuridica extends Cliente {

    @Column(unique = true, length = 11)
    private String ruc;
    
    @Column(unique = true, length = 60)
    private String razonSocial;
    
    private String repreLegal;

    // GETTERS Y SETTERS
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRepreLegal() {
        return repreLegal;
    }

    public void setRepreLegal(String repreLegal) {
        this.repreLegal = repreLegal;
    }
}