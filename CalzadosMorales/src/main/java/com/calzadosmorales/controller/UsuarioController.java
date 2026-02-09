package com.calzadosmorales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calzadosmorales.entity.Usuario;
import com.calzadosmorales.repository.UsuarioRepository;
import com.calzadosmorales.service.UsuarioService;



@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    
    @Autowired
    private UsuarioRepository repo; 

    @GetMapping
    public String listar(Model model) {
       
        model.addAttribute("usuarios", service.listarUsuarios());
        model.addAttribute("roles", service.listarRoles());
        
       
        model.addAttribute("usuarioForm", new Usuario()); 
        
        return "usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuarioForm") Usuario usuario, RedirectAttributes flash) {
        
       
        Usuario existeUser = repo.findByUsuario(usuario.getUsuario());
        
        if (existeUser != null) {
           
            if (usuario.getId_usuario() == null || !usuario.getId_usuario().equals(existeUser.getId_usuario())) {
                flash.addFlashAttribute("error", "El login '" + usuario.getUsuario() + "' ya está ocupado.");
                return "redirect:/usuarios";
            }
        }

     
        Usuario existePersonaRol = repo.findByNombreAndRol(usuario.getNombre(), usuario.getRol());

        if (existePersonaRol != null) {
          
            String nombreRol = existePersonaRol.getRol().getNombre();

            
            if (usuario.getId_usuario() == null) {
                flash.addFlashAttribute("error", "El usuario '" + usuario.getNombre() + "' ya tiene el rol de " + nombreRol + ".");
                return "redirect:/usuarios";
            }
          
            if (!usuario.getId_usuario().equals(existePersonaRol.getId_usuario())) {
                flash.addFlashAttribute("error", "El usuario '" + usuario.getNombre() + "' ya tiene el rol de " + nombreRol + ".");
                return "redirect:/usuarios";
            }
        }

       
        if (usuario.getId_usuario() == null) {
            usuario.setEstado(true); 
        } else {
           
            Usuario actual = service.buscarUsuarioPorId(usuario.getId_usuario());
            if(actual != null) {
                usuario.setEstado(actual.getEstado());
            }
        }

        service.guardarUsuario(usuario);
        flash.addFlashAttribute("success", "Usuario registrado correctamente.");
        return "redirect:/usuarios";
    }

    @GetMapping("/cambiarEstado/{id}/{estado}")
    public String cambiarEstado(@PathVariable("id") Integer id, @PathVariable("estado") boolean nuevoEstado, RedirectAttributes flash) {
        Usuario u = service.buscarUsuarioPorId(id);
        if (u != null) {
            u.setEstado(nuevoEstado);
            service.guardarUsuario(u);
            String msg = nuevoEstado ? "Usuario habilitado." : "Usuario inhabilitado.";
            flash.addFlashAttribute("info", msg);
        }
        return "redirect:/usuarios";
    }
}