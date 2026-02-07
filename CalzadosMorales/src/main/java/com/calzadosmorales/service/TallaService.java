package com.calzadosmorales.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calzadosmorales.entity.Talla;
import com.calzadosmorales.repository.TallaRepository;


@Service
public class TallaService {

    @Autowired
    private TallaRepository repo;

    public List<Talla> listarTodas() {
        return repo.findAll();
    }

    public void guardar(Talla talla) {
        repo.save(talla);
    }
    
    public Talla buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }
}