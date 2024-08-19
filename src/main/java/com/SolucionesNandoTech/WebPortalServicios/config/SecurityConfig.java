/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.config;

import com.SolucionesNandoTech.WebPortalServicios.interfaces.UsuarioRepository;
import com.SolucionesNandoTech.WebPortalServicios.models.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    public SecurityConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/WebPortalServicios/css/**").permitAll()
                .requestMatchers("/WebPortalServicios/js/**").permitAll()
                .requestMatchers("/WebPortalServicios/index.html").permitAll()
                .requestMatchers("/WebPortalServicios/usuarios").permitAll()
                .requestMatchers("/WebPortalServicios/public/**").permitAll()
                .requestMatchers("/WebPortalServicios/user/**").hasRole("USER")
                .requestMatchers("/WebPortalServicios/tech/**").hasRole("TECHNICIAN")
                .requestMatchers("/WebPortalServicios/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Usuario usuario = usuarioRepository.findByEmail(email);
            if (usuario == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new org.springframework.security.core.userdetails.User(
                    usuario.getNombre(),
                    usuario.getPassword(),
                    usuario.isEnabled(),
                    true,
                    true,
                    true,
                    usuario.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .collect(Collectors.toSet())
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
