package com.calzadosmorales.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "persona_natural")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class PersonaNatural extends Cliente {


	@Pattern(regexp = "[0-9]{8}", message = "El DNI es obligatorio y debe tener 8 dígitos.")
	@Column(unique = true, length = 8)
	private String dni;

	@Size(min = 2, message = "El nombre es obligatorio (min. 2 letras).")
	private String nombre;

	@Size(min = 2, message = "El apellido es obligatorio (min. 2 letras).")
	private String apellido;

	@NotNull(message = "Seleccione un género.")
	private Integer genero;

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