/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author nando
 */
@Controller
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
//    @GetMapping("/user/home")
//    public String login(Authentication authentication, Model model) {
//        String role = usuarioService.getCurrentUserRoles();
//        System.out.println("El usuario está autenticado con roles: " + role);
//        if (authentication != null) {
//            model.addAttribute("roles", authentication.getAuthorities().toString());
//        }
//        return "home"; // Devuelve el nombre del archivo sin la extensión .html
//    }
    
}
