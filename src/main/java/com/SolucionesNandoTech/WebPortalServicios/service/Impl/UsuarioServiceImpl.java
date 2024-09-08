/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.service.Impl;

import com.SolucionesNandoTech.WebPortalServicios.model.ERole;
import com.SolucionesNandoTech.WebPortalServicios.model.Especialidad;
import com.SolucionesNandoTech.WebPortalServicios.model.Role;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import com.SolucionesNandoTech.WebPortalServicios.repository.EspecialidadRepository;
import com.SolucionesNandoTech.WebPortalServicios.repository.RoleRepository;
import com.SolucionesNandoTech.WebPortalServicios.repository.UsuarioRepository;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

    @Autowired
    private EspecialidadRepository especialidadRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Usuario registerNewUser(UsuarioDto usuarioDto) {
        // Verificar si el usuario ya existe
        if (!isEmailTaken(usuarioDto.getEmail())) {
            // Mapeo de roles desde el DTO a la base de datos
        Set<Role> roles = usuarioDto.getRoles().stream()
                .map(roleName -> {
                    ERole eRole;
                    try {
                        eRole = ERole.valueOf(roleName);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid role: " + roleName, e);
                    }

                    return roleRepository.findByName(eRole)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                })
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
                // Obtén las especialidades desde la base de datos
                List<Especialidad> especialidades = especialidadRepository.findAllById(usuarioDto.getEspecialidades());
                Set<Especialidad> especialidadesSet = new HashSet<>(especialidades);
                usuario.setEspecialidades(especialidadesSet);
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

            // Construir el UsuarioDetails con los datos obtenidos
            return Usuario.builder()
                    .id(usuario.getId())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .roles(usuario.getRoles())
                    .nombre(usuario.getNombre())
                    .telefono(usuario.getTelefono())
                    .direccion(usuario.getDireccion())
                    .especialidades(usuario.getEspecialidades())
                    .enabled(usuario.isEnabled())
                    .build();
        } else { // Lanza una excepci�n si no se encuentra el usuario en la base de datos
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

    }

    @Override
    public boolean isEmailTaken(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario authenticateUser(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> findByRolesNombreAndEnabled(ERole roleName, boolean enabled) {
        return usuarioRepository.findByRoleAndEnabled(roleName, enabled);
    }

    @Override
    public Usuario obtenerUsuarioPorId(long id) {
        // Usa Optional para manejar la posible ausencia del usuario
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
