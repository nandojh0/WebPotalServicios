/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.config;

import com.SolucionesNandoTech.WebPortalServicios.utils.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //                   .disable()
                )
                .authenticationProvider(customAuthenticationProvider)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/css/**").permitAll();
                    auth.requestMatchers("/js/**").permitAll();
                    auth.requestMatchers("/", "/login", "/registrar").permitAll(); // Index, login, and registrar.
                    auth.requestMatchers("/register", "/currentUser").permitAll(); // para los endpoint
                    auth.requestMatchers("/home").hasAuthority("USER");
                    auth.requestMatchers("/user/**").hasAuthority("USER");
                    auth.requestMatchers("/tech/**").hasAuthority("TECHNICIAN");
                    auth.requestMatchers("/admin/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                // Configura el formulario de login
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler) // Usar el handler personalizado
                .failureUrl("/login?error=true")
                )
                .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // Usa el CustomAuthenticationEntryPoint
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                            .invalidSessionStrategy(new SimpleRedirectInvalidSessionStrategy("/login"))
                            .sessionFixation().newSession() // Opción para crear una nueva sesión en caso de fijación de sesión
                            .maximumSessions(1) // Máximo número de sesiones por usuario
                            .expiredUrl("/login?expired");
                })
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
                )
                .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                .contentSecurityPolicy(csp -> csp
                .policyDirectives("default-src 'self'; style-src 'self' 'unsafe-inline' https://maxcdn.bootstrapcdn.com https://stackpath.bootstrapcdn.com; script-src 'self' 'unsafe-inline' https://maxcdn.bootstrapcdn.com;"))
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
