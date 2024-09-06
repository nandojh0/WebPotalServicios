/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nando
 */
@Controller
@RequestMapping("/user")
public class UsuarioController {
    
    @Autowired
    private ServicioService servicioService;

    @GetMapping("/home")
    public String userHome(Authentication authentication,Model model, RedirectAttributes redirectAttributes) {
        // Obtener el Authentication del SecurityContextHolder
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Verificar que el principal es una instancia de UsuarioDetails
        if (authentication.getPrincipal() instanceof Usuario) {
            Usuario usuarioDetails = (Usuario) authentication.getPrincipal();

            // Obtener el  roles del UsuarioDetails
            String role = usuarioDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("USER_NOT_DEFINED");
            

        model.addAttribute("username",usuarioDetails.getNombre());
        model.addAttribute("id",usuarioDetails.getId());
        model.addAttribute("phoneNumber",usuarioDetails.getTelefono());
        model.addAttribute("authorities", role);
        List<Servicio> servicios = servicioService.obtenerServiciosPorCreador(usuarioDetails.getId());
        model.addAttribute("servicios", servicios);
         } else {
          redirectAttributes.addFlashAttribute("No autenticado");
          return "redirect:/login";
        }
        return "/user/home"; // Página de inicio del usuario
    }

}
