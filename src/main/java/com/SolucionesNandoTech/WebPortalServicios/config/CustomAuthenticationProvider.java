/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.config;

/**
 *
 * @author nando
 */

import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Implementa los métodos necesarios, como authenticate y supports
    @Override
    public Authentication authenticate(Authentication authentication) {
        String emain = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user;

        try {
            // Carga los detalles del usuario usando el UsuarioService
            user = usuarioService.loadUserByUsername(emain);
        } catch (UsernameNotFoundException e) {
            // Si el usuario no se encuentra, lanzar una excepción personalizada
            throw new BadCredentialsException("Usuario no encontrado");
        }
        
        // Verifica la contraseña
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        } else {
            throw new BadCredentialsException("Contraseña incorrecta");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
