/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.AuthenticationRequest;
import com.SolucionesNandoTech.WebPortalServicios.model.Response;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import com.SolucionesNandoTech.WebPortalServicios.utils.DocumentManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nando
 */
@Controller
public class PrincipalControllers {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocumentManager doc;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDto usuarioDto) {
        Response response;
        try {
            usuarioService.registerNewUser(usuarioDto);
            response = new Response("000", "Succes");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = new Response("020", "Excepcion en el Servicio", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

//    @PostMapping("/currentUser")
//    public String login(HttpServletRequest requestHttp, AuthenticationRequest request, RedirectAttributes redirectAttributes, Model model) {
//        try {
//            Usuario usuarioAuth = usuarioService.authenticateUser(request.getEmail(), request.getPassword());
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            if (auth != null && auth.isAuthenticated()) {
//                String role = auth.getAuthorities().stream()
//                        .map(GrantedAuthority::getAuthority)
//                        .findFirst()
//                        .orElse("No role found");
//                System.out.println("El usuario está autenticado con roles: " + auth.getAuthorities());
//                String redirectUrl = getRedirectUrlBasedOnRole(role);
//                model.addAttribute("email", auth.getName());
//                model.addAttribute("username", usuarioAuth.getNombre());
//                model.addAttribute("id", usuarioAuth.getId());
//                model.addAttribute("authorities", role);
//                return redirectUrl;
//            } else {
//                System.out.println("El usuario no está autenticado");
//                 redirectAttributes.addFlashAttribute("error", "El usuario no está autenticado");
//                return "redirect:/login";
//            }
//        } catch (RuntimeException e) {
//            System.out.println("El usuario no está autenticado");
//            redirectAttributes.addFlashAttribute("error", "El usuario no está autenticado");
//            return "redirect:/login";
//        }
//    }

    @GetMapping("/home")
    public String home(HttpServletRequest request, Authentication authentication, Model model) {
        System.out.println("Contexto de seguridad en /home: " + SecurityContextHolder.getContext().getAuthentication());
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("roles", authentication.getAuthorities().toString());
        } else {
            model.addAttribute("error", "No autenticado");
            return "redirect:/login";
        }
        return "user/home";
    }

//    private String getRedirectUrlBasedOnRole(String role) {
//        switch (role) {
//            case "ADMIN":
//                return "/admin-home";
//            case "TECHNICIAN":
//                return "/technician-home";
//            default:
//                return "/user/home"; // va ha home.index
//        }
//
//    }
}
