/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nando
 */
@Controller
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ServicioService servicioService;

    @GetMapping("/home")
    public String userHome(Authentication authentication, Model model) {
        String emain = (String) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("user no definido");
        Usuario usuario = usuarioService.authenticateUser(emain);
        model.addAttribute("username", usuario.getNombre());
        model.addAttribute("id", usuario.getId());
        model.addAttribute("phoneNumber", usuario.getTelefono());
        model.addAttribute("authorities", role);
        List<Servicio> servicios = servicioService.obtenerServiciosPorCreador(usuario.getId());
        model.addAttribute("servicios", servicios);
        return "/user/home"; // Página de inicio del usuario
    }

}
