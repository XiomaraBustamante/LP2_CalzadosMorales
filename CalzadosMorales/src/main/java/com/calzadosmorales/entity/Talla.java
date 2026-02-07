package com.calzadosmorales.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tallas")
public class Talla {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_talla;

	@Column(nullable = false, unique = true)
	private String nombre;

	@Column(nullable = false)
	private Boolean estado;

	public Talla() {
	}

	public Talla(Integer id_talla, String nombre, Boolean estado) {
		this.id_talla = id_talla;
		this.nombre = nombre;
		this.estado = estado;
	}

	public Integer getId_talla() {
		return id_talla;
	}

	public void setId_talla(Integer id_talla) {
		this.id_talla = id_talla;
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