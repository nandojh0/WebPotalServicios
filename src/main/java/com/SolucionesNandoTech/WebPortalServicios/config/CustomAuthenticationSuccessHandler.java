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
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Verificar si el usuario está habilitado
        if (!usuario.isEnabled()) {
            response.sendRedirect(request.getContextPath() + "/login?error=disabled");
            return;
        }
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("user no definido");
        HttpSession session = request.getSession();
        // Guardar información del usuario en la sesión
        session.setAttribute("username", authentication.getName());
        session.setAttribute("authorities", authentication.getAuthorities());
        switch (role) {
            case "ADMIN":
                response.sendRedirect("WebPortalServicios/admin-home");
                break;
            case "TECHNICIAN":
                response.sendRedirect(request.getContextPath() + "/tech/home");
                break;
            case "USER":
                response.sendRedirect(request.getContextPath() + "/user/home");
                break;
            default:
                response.sendRedirect("login");
                break;
        }
    }
}
