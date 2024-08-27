/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author nando
 */
@Controller
public class TecnicoController {
    
    @GetMapping("/tech/home")
    public String login(Authentication authentication, Model model) {
        if (authentication != null) {
            model.addAttribute("roles", authentication.getAuthorities().toString());
        }
        return "home"; // Devuelve el nombre del archivo sin la extensión .html
    }
    
}
