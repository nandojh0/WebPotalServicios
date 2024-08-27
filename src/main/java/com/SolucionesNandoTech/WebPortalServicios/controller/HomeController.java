/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;

/**
 *
 * @author nando
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // Página de inicio pública
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Página de login
    }

    @GetMapping("/registrar")
    public String register() {
        return "registrar"; // Página de registro
    }


//    @GetMapping("/home")
//    public String home(HttpSession session, Model model) {
//        // Obtener los datos del usuario de la sesión
//        String username = (String) session.getAttribute("username");
//        Object authorities = session.getAttribute("authorities");
//
//        // Añadir los datos al modelo
//        model.addAttribute("username", username);
//        model.addAttribute("authorities", authorities);
//        return "home"; // Página de inicio para usuarios
//    }

    @GetMapping("/admin-home")
    public String adminHome() {
        return "admin-home"; // Página de inicio para administradores
    }

    @GetMapping("/technician-home")
    public String technicianHome() {
        return "technician-home"; // Página de inicio para técnicos
    }
}
