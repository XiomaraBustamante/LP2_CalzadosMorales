package com.calzadosmorales.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calzadosmorales.entity.Color;
import com.calzadosmorales.repository.ColorRepository;


@Service
public class ColorService {

    @Autowired
    private ColorRepository repo;

    public List<Color> listarTodos() {
        return repo.findAll();
    }

    public void guardar(Color color) {
        repo.save(color);
    }
    
    
    public Color buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }
}