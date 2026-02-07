package com.calzadosmorales.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calzadosmorales.entity.Material;
import com.calzadosmorales.repository.MaterialRepository;


@Service
public class MaterialService {

    @Autowired
    private MaterialRepository repo;

    public List<Material> listarTodos() {
        return repo.findAll();
    }

    public void guardar(Material material) {
        repo.save(material);
    }
    
    public Material buscarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }
}