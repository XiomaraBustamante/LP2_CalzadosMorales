package com.calzadosmorales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calzadosmorales.entity.Rol;
import com.calzadosmorales.entity.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsuario(String usuario);
    
   
    Usuario findByNombreAndRol(String nombre, Rol rol);
}