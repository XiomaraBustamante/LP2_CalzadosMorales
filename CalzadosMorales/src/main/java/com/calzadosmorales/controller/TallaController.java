package com.calzadosmorales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calzadosmorales.entity.Talla;
import com.calzadosmorales.repository.TallaRepository;
import com.calzadosmorales.service.TallaService;



@Controller
@RequestMapping("/tallas")
public class TallaController {

    @Autowired
    private TallaService service;
    
    @Autowired
    private TallaRepository repo;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tallas", service.listarTodas());
        model.addAttribute("talla", new Talla());
        return "tallas"; 
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Talla talla, RedirectAttributes flash) {
      
        Talla posibleDuplicado = repo.findByNombre(talla.getNombre());

        if (posibleDuplicado != null) {
            if (talla.getId_talla() == null) {
                flash.addFlashAttribute("error", "Ya existe la talla '" + talla.getNombre() + "'.");
                return "redirect:/tallas";
            }
            if (!talla.getId_talla().equals(posibleDuplicado.getId_talla())) {
                flash.addFlashAttribute("error", "No puedes usar '" + talla.getNombre() + "' porque ya existe.");
                return "redirect:/tallas";
            }
        }

    
        if(talla.getId_talla() == null) {
            talla.setEstado(true);
        } else {
            Talla actual = service.buscarPorId(talla.getId_talla());
            if(actual != null) {
                talla.setEstado(actual.getEstado());
            }
        }
        
        service.guardar(talla);
        flash.addFlashAttribute("success", "Talla guardada correctamente.");
        return "redirect:/tallas";
    }

    @GetMapping("/cambiarEstado/{id}/{estado}")
    public String cambiarEstado(@PathVariable("id") Integer id, @PathVariable("estado") boolean nuevoEstado, RedirectAttributes flash) {
        Talla talla = service.buscarPorId(id);
        if (talla != null) {
            talla.setEstado(nuevoEstado);
            service.guardar(talla);
            String mensaje = nuevoEstado ? "Talla activada." : "Talla desactivada.";
            flash.addFlashAttribute("info", mensaje);
        }
        return "redirect:/tallas";
    }
}