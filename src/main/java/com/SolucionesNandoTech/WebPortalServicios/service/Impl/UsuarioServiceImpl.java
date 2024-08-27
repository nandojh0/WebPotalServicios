/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.service.Impl;

import com.SolucionesNandoTech.WebPortalServicios.model.ERole;
import com.SolucionesNandoTech.WebPortalServicios.model.Role;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import com.SolucionesNandoTech.WebPortalServicios.repository.UsuarioRepository;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import static org.atmosphere.annotation.AnnotationUtil.logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author nando
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario registerNewUser(UsuarioDto usuarioDto) {
        // Verificar si el usuario ya existe
        if (!isEmailTaken(usuarioDto.getEmail())) {
            Set<Role> roles = usuarioDto.getRoles().stream()
                    .map(role -> Role.builder()
                    .name(ERole.valueOf(role))
                    .build())
                    .collect(Collectors.toSet());
            
            Usuario usuario = Usuario.builder()
                    .nombre(usuarioDto.getNombre())
                    .email(usuarioDto.getEmail())
                    .password(passwordEncoder.encode(usuarioDto.getPassword()))
                    .direccion(usuarioDto.getTelefono())
                    .telefono(usuarioDto.getTelefono())
                    .enabled(true)
                    .roles(roles)
                    .build();

            String role = usuarioDto.getRoles().stream()
                    .findFirst()
                    .orElse("No role found");
            if (role.equals("TECHNICIAN")) {
                usuario.setPuntuacion(0.0);
                usuario.setEspecialidad(usuarioDto.getEspecialidad());
            }
            
            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("User already exists");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (isEmailTaken(email)) {
            // Busca el usuario en la base de datos utilizando el nombre de usuario proporcionado
            Usuario usuario = usuarioRepository.findByEmail(email);
            // Crea un objeto UserDetails utilizando las propiedades del usuario encontrado
            User userDetails = new User(
                    usuario.getEmail(),
                    usuario.getPassword(),
                    usuario.isEnabled(),
                    true, // accountNonExpired
                    true, // credentialsNonExpired
                    true, // accountNonLocked
                    getAuthorities(usuario.getRoles())
            );
            logger.info("Authenticated user: {}", userDetails);
            return userDetails;

        } else { // Lanza una excepci�n si no se encuentra el usuario en la base de datos
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())) // Sin prefijo ROLE_
                .collect(Collectors.toList());
    }

    @Override
    public boolean isEmailTaken(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario authenticateUser(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
