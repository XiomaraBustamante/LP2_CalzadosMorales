package com.calzadosmorales.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "persona_juridica")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class PersonaJuridica extends Cliente {

	@Pattern(regexp = "[0-9]{11}", message = "El RUC es obligatorio y debe tener 11 dígitos.")
	@Column(unique = true, length = 11)
	private String ruc;

	@NotBlank(message = "La Razón Social es obligatoria.")
	@Column(unique = true, length = 60)
	private String razonSocial;

	@NotBlank(message = "El Representante Legal es obligatorio.")
	private String repreLegal;

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