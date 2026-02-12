package com.calzadosmorales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.calzadosmorales.entity.PersonaNatural;

@Repository
public interface PersonaNaturalRepository extends JpaRepository<PersonaNatural, Integer> {
    PersonaNatural findByDni(String dni);
}