package com.calzadosmorales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calzadosmorales.entity.Color;
import com.calzadosmorales.repository.ColorRepository;
import com.calzadosmorales.service.ColorService;



@Controller
@RequestMapping("/colores")
public class ColorController {

    @Autowired
    private ColorService service;
    
    @Autowired
    private ColorRepository repo; 

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("colores", service.listarTodos());
        model.addAttribute("color", new Color()); 
        return "colores";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Color color, RedirectAttributes flash) {
       
        Color posibleDuplicado = repo.findByNombre(color.getNombre());

        if (posibleDuplicado != null) {
          
            if (color.getId_color() == null) {
                flash.addFlashAttribute("error", "Ya existe el color '" + color.getNombre() + "'.");
                return "redirect:/colores";
            }
           
            if (!color.getId_color().equals(posibleDuplicado.getId_color())) {
                flash.addFlashAttribute("error", "No puedes usar '" + color.getNombre() + "' porque ya existe.");
                return "redirect:/colores";
            }
        }


        if(color.getId_color() == null) {
            color.setEstado(true);
        } else {
            
            Color actual = service.buscarPorId(color.getId_color());
            if(actual != null) {
                color.setEstado(actual.getEstado());
            }
        }
        
        service.guardar(color);
        flash.addFlashAttribute("success", "Color guardado correctamente.");
        return "redirect:/colores";
    }

    @GetMapping("/cambiarEstado/{id}/{estado}")
    public String cambiarEstado(@PathVariable("id") Integer id, @PathVariable("estado") boolean nuevoEstado, RedirectAttributes flash) {
        Color color = service.buscarPorId(id);
        if (color != null) {
            color.setEstado(nuevoEstado);
            service.guardar(color);
            String mensaje = nuevoEstado ? "Color activado." : "Color desactivado.";
            flash.addFlashAttribute("info", mensaje);
        }
        return "redirect:/colores";
    }
}