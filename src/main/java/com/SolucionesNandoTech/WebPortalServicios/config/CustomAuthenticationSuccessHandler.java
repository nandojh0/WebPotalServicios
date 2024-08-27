/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.config;

/**
 *
 * @author nando
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER"); // Valor por defecto si no se encuentra ningún rol
         HttpSession session = request.getSession();
        // Guardar información del usuario en la sesión
        session.setAttribute("username", authentication.getName());
        session.setAttribute("authorities", authentication.getAuthorities());
        switch (role) {
            case "ADMIN":
                response.sendRedirect("/admin-home");
                break;
            case "TECHNICIAN":
                response.sendRedirect("/technician-home");
                break;
            default:
                response.sendRedirect("/home");
                break;
        }
    }
}
