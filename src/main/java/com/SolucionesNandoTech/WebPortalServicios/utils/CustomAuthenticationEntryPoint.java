/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.utils;


import com.SolucionesNandoTech.WebPortalServicios.exceptions.CustomAccessDeniedException;
import com.SolucionesNandoTech.WebPortalServicios.exceptions.CustomAuthenticationException;
import com.SolucionesNandoTech.WebPortalServicios.model.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;




/**
 *
 * @author hernando.hernandez
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            System.out.println("Authenticated user roles: " + auth.getAuthorities());
        }
        res.setContentType("application/json;charset=UTF-8");
       
        Response<String> response;

        if (authException instanceof CustomAuthenticationException) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response = new Response<>("011", "Custom Authentication Error: " + authException.getMessage());
        } else if (authException.getCause() instanceof CustomAccessDeniedException) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
            response = new Response<>("012", "Custom Access Denied: " +  authException.getCause().getMessage());
        } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response = new Response<>("013", "Unauthorized");
        }

        // Convertir la respuesta a JSON y enviarla al cliente
        String jsonResponse = objectMapper.writeValueAsString(response);
        res.getWriter().write(jsonResponse);
    }
}