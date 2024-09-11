/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nando
 */
@Controller
@RequestMapping("/tech")
public class TecnicoController {

    @GetMapping("/home")
    public String userHome(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        // Obtener el Authentication del SecurityContextHolder
        // Verificar que el principal es una instancia de UsuarioDetails
        if (authentication.getPrincipal() instanceof Usuario usuarioDetails) {

            // Obtener el  roles del UsuarioDetails
            String role = usuarioDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER_NOT_DEFINED");

            model.addAttribute("tecnico", usuarioDetails);
            model.addAttribute("authorities", role);
        } else {
            redirectAttributes.addFlashAttribute("No autenticado");
            return "redirect:/login";
        }
        return "tech/home"; // Página de inicio del usuario
    }

}
