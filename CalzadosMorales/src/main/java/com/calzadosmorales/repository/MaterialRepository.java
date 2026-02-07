package com.calzadosmorales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calzadosmorales.entity.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Material findByNombre(String nombre);
}