package com.calzadosmorales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.calzadosmorales.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
	// Aquí luego pondremos reportes, por ahora basta con el básico
}