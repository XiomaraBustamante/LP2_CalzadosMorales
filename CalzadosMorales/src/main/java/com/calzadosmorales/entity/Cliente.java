package com.calzadosmorales.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_cliente;

	@NotBlank(message = "La dirección es obligatoria.")
	@Column(length = 100)
	private String direccion;

	@Pattern(regexp = "[0-9]{9}", message = "El teléfono es obligatorio y debe tener 9 dígitos.")
	@Column(unique = true, length = 9)
	private String telefono;

	@NotBlank(message = "El email es obligatorio.")
	@Email(message = "Formato inválido (ejemplo@correo.com).")
	@Column(unique = true, length = 50)
	private String email;

	private LocalDate fecha_registro;
	private Boolean estado;

	@PrePersist
	public void prePersist() {
		this.fecha_registro = LocalDate.now();
		this.estado = true;
	}

	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(LocalDate fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}
}