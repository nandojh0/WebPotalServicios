/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller.filters;

/**
 *
 * @author nando
 */
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.utils.DocumentManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuthenticationFilter implements Filter {

    @Autowired
    private DocumentManager documentUtil;
    
    
    @Value("${SERVER.SERVLET.CONTEXT-PATH}")
    private String SERVER_PATH;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            // Verificar si el usuario está autenticado
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            String path = httpRequest.getRequestURI();
            
            // Verifica si la solicitud es a una URL pública (por ejemplo, login, css, js, imágenes, etc.)
            if (isPublicUrl(path)) {
                filterChain.doFilter(request, response);
            }

            if (authentication != null && authentication.getPrincipal() instanceof Usuario) {
                // El usuario está autenticado, continuar con la cadena de filtros
                filterChain.doFilter(request, response);
            } else {
                // El usuario no está autenticado, redirige a la página de inicio de sesión
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }

        } catch (ServletException | IOException e) {
            documentUtil.write(1, " Excepción: " + e);
        }
    }
    
    /**
     * Verifica si la URL es pública.
     * @param path Ruta de la solicitud.
     * @return true si la URL es pública, false en caso contrario.
     */
    private boolean isPublicUrl(String path) {
        return path.startsWith(SERVER_PATH + "/login") || 
               path.startsWith(SERVER_PATH + "/css") || 
               path.startsWith(SERVER_PATH + "/js") ||
               path.startsWith(SERVER_PATH + "/images") ||  // Agregado para imágenes
               path.startsWith(SERVER_PATH + "/fonts");     // Agregado para fuentes
    }
}
