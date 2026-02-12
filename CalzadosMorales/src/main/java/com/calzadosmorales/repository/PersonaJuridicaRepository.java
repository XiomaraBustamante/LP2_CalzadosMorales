package com.calzadosmorales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.calzadosmorales.entity.PersonaJuridica;

@Repository
public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Integer> {
    PersonaJuridica findByRuc(String ruc);
    PersonaJuridica findByRazonSocial(String razonSocial);
}