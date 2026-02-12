package com.calzadosmorales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calzadosmorales.entity.PersonaNatural;
import com.calzadosmorales.entity.PersonaJuridica;
import com.calzadosmorales.entity.Cliente;
import com.calzadosmorales.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personasNaturales", service.listarPersonasNaturales());
        model.addAttribute("personasJuridicas", service.listarPersonasJuridicas());
        return "clientes";
    }

    @PostMapping("/guardarNatural")
    public String guardarPersonaNatural(
            @RequestParam String dni,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam Integer genero,
            @RequestParam String telefono,
            @RequestParam String email,
            @RequestParam String direccion,
            RedirectAttributes flash) {

        // Validar DNI duplicado
        PersonaNatural existeDni = service.buscarPorDni(dni);
        if (existeDni != null) {
            flash.addFlashAttribute("error", "El DNI '" + dni + "' ya está registrado.");
            return "redirect:/clientes";
        }

        // Validar Email duplicado
        Cliente existeEmail = service.buscarPorEmail(email);
        if (existeEmail != null) {
            flash.addFlashAttribute("error", "El Email '" + email + "' ya está registrado.");
            return "redirect:/clientes";
        }

        // Validar Teléfono duplicado
        Cliente existeTelefono = service.buscarPorTelefono(telefono);
        if (existeTelefono != null) {
            flash.addFlashAttribute("error", "El Teléfono '" + telefono + "' ya está registrado.");
            return "redirect:/clientes";
        }

        // Crear y guardar
        PersonaNatural persona = new PersonaNatural();
        persona.setDni(dni);
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setGenero(genero);
        persona.setTelefono(telefono);
        persona.setEmail(email);
        persona.setDireccion(direccion);

        service.guardarPersonaNatural(persona);
        flash.addFlashAttribute("success", "Persona Natural registrada exitosamente.");
        return "redirect:/clientes";
    }

    @PostMapping("/guardarJuridica")
    public String guardarPersonaJuridica(
            @RequestParam String ruc,
            @RequestParam String razonSocial,
            @RequestParam String repreLegal,
            @RequestParam String telefono,
            @RequestParam String email,
            @RequestParam String direccion,
            RedirectAttributes flash) {

        // Validar RUC duplicado
        PersonaJuridica existeRuc = service.buscarPorRuc(ruc);
        if (existeRuc != null) {
            flash.addFlashAttribute("error", "El RUC '" + ruc + "' ya está registrado.");
            return "redirect:/clientes";
        }

        // Validar Razón Social duplicada
        PersonaJuridica existeRazon = service.buscarPorRazonSocial(razonSocial);
        if (existeRazon != null) {
            flash.addFlashAttribute("error", "La Razón Social '" + razonSocial + "' ya está registrada.");
            return "redirect:/clientes";
        }

        // Validar Email duplicado
        Cliente existeEmail = service.buscarPorEmail(email);
        if (existeEmail != null) {
            flash.addFlashAttribute("error", "El Email '" + email + "' ya está registrado.");
            return "redirect:/clientes";
        }

        // Validar Teléfono duplicado
        Cliente existeTelefono = service.buscarPorTelefono(telefono);
        if (existeTelefono != null) {
            flash.addFlashAttribute("error", "El Teléfono '" + telefono + "' ya está registrado.");
            return "redirect:/clientes";
        }

        // Crear y guardar
        PersonaJuridica persona = new PersonaJuridica();
        persona.setRuc(ruc);
        persona.setRazonSocial(razonSocial);  // ✅ CORRECTO: razon_social
        persona.setRepreLegal(repreLegal);    // ✅ CORRECTO: repre_legal
        persona.setTelefono(telefono);
        persona.setEmail(email);
        persona.setDireccion(direccion);

        service.guardarPersonaJuridica(persona);
        flash.addFlashAttribute("success", "Persona Jurídica registrada exitosamente.");
        return "redirect:/clientes";
    }

    @GetMapping("/cambiarEstado/{id}/{estado}/{tipo}")
    public String cambiarEstado(
            @PathVariable("id") Integer id, 
            @PathVariable("estado") boolean nuevoEstado,
            @PathVariable("tipo") String tipo,
            RedirectAttributes flash) {
        
        if (tipo.equals("natural")) {
            PersonaNatural persona = service.buscarPersonaNaturalPorId(id);
            if (persona != null) {
                persona.setEstado(nuevoEstado);
                service.guardarPersonaNatural(persona);
                String mensaje = nuevoEstado ? "Cliente activado." : "Cliente desactivado.";
                flash.addFlashAttribute("info", mensaje);
            }
        } else {
            PersonaJuridica persona = service.buscarPersonaJuridicaPorId(id);
            if (persona != null) {
                persona.setEstado(nuevoEstado);
                service.guardarPersonaJuridica(persona);
                String mensaje = nuevoEstado ? "Cliente activado." : "Cliente desactivado.";
                flash.addFlashAttribute("info", mensaje);
            }
        }
        
        return "redirect:/clientes";
    }
}