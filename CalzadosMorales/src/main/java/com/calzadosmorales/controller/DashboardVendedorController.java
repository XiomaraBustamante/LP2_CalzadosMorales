package com.calzadosmorales.controller;

import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.calzadosmorales.entity.Usuario;
import com.calzadosmorales.repository.UsuarioRepository;
import com.calzadosmorales.service.DashboardVendedorService;
import com.calzadosmorales.service.UsuarioService;

@Controller
public class DashboardVendedorController {

    @Autowired
    private DashboardVendedorService dashboardService;

    @Autowired
    private UsuarioRepository usuarioRepo; // <--- Cambiamos a Repository para buscar por nombre de usuario

    @GetMapping("/index")
    public String dashboard(@RequestParam(name = "idUsuario", required = false) Integer idUsuario, 
                            Model model, 
                            Authentication auth) {
        
        // 1. Si venimos del Login, el idUsuario es null. Lo buscamos por Authentication.
        if (idUsuario == null && auth != null) {
            String username = auth.getName(); // Trae "adminXio"
            Usuario u = usuarioRepo.findByUsuario(username); // Busca todo el perfil
            
            if (u != null) {
                idUsuario = u.getId_usuario(); // ¡Ya tenemos el ID real!
                model.addAttribute("userNombreCompleto", u.getNombre());
                model.addAttribute("userRol", u.getRol().getNombre());
            }
        }

        // 2. Si logramos obtener un ID (ya sea por URL o por búsqueda), cargamos los servicios de Andrés
        if (idUsuario != null) {
            model.addAttribute("ventasMes", dashboardService.ventasMes(idUsuario));
            model.addAttribute("comision", dashboardService.comisionMes(idUsuario));
            model.addAttribute("cantidadVentas", dashboardService.cantidadVentas(idUsuario));
            model.addAttribute("paresVendidos", dashboardService.paresVendidos(idUsuario));
            model.addAttribute("productoEstrella", dashboardService.productoEstrella(idUsuario));
        }

        return "index";
    }
}