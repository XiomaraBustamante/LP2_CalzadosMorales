package com.calzadosmorales.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "materiales")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_material;

    
    @Column(name = "tipo", nullable = false, unique = true)
    private String nombre; 

    @Column(nullable = false)
    private Boolean estado;

    public Material() {
    }

    public Material(Integer id_material, String nombre, Boolean estado) {
        this.id_material = id_material;
        this.nombre = nombre;
        this.estado = estado;
    }

    
    
    public Integer getId_material() {
        return id_material;
    }

    public void setId_material(Integer id_material) {
        this.id_material = id_material;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}