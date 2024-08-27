/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.config;

import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.repository.UsuarioRepository;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import com.SolucionesNandoTech.WebPortalServicios.utils.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//        @Bean
//    public AuthenticationSuccessHandler authenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UsuarioService(); // Implementar este servicio para cargar usuarios
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                   .disable()
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/css/**").permitAll();
                    auth.requestMatchers("/js/**").permitAll();
                    auth.requestMatchers("/", "/login", "/registrar").permitAll(); // Index, login, and registrar.
                    auth.requestMatchers("/register", "/currentUser").permitAll(); // para los endpoint
                    auth.requestMatchers("/home").permitAll();
                    auth.requestMatchers("/home").hasAuthority("USER");
                    auth.requestMatchers("/user/**").hasAuthority("USER");
                    auth.requestMatchers("/tech/**").hasAuthority("TECHNICIAN");
                    auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // Usa el CustomAuthenticationEntryPoint
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//                            .invalidSessionStrategy(new SimpleRedirectInvalidSessionStrategy("/login"))
//                            .sessionFixation().newSession() // Opción para crear una nueva sesión en caso de fijación de sesión
//                            .maximumSessions(1) // Máximo número de sesiones por usuario
//                            .expiredUrl("/login?expired");
                })
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                )
                .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                .contentSecurityPolicy(csp -> csp
                .policyDirectives("default-src 'self'; style-src 'self' 'unsafe-inline' https://maxcdn.bootstrapcdn.com; script-src 'self' 'unsafe-inline' https://maxcdn.bootstrapcdn.com;"))
                );

        return http.build();
    }

//    @Bean
//    public CsrfTokenRepository csrfTokenRepository() {
//        CookieCsrfTokenRepository repository = new CookieCsrfTokenRepository();
//        repository.setCookieHttpOnly(false); // Permitir que JavaScript lea la cookie
//        repository.setCookieMaxAge(86400); // Configurar el tiempo de expiración de la cookie
//        return repository;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return email -> {
//            Usuario usuario = usuarioRepository.findByEmail(email);
//            if (usuario == null) {
//                throw new UsernameNotFoundException("User not found");
//            }
//            return new User(
//                    usuario.getEmail(),
//                    usuario.getPassword(),
//                    usuario.isEnabled(),
//                    true,
//                    true,
//                    true,
//                    usuario.getRoles().stream()
//                            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
//                            .collect(Collectors.toSet())
//            );
//        };
//    }
}
