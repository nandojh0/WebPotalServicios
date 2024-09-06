/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.mapper;

/**
 *
 * @author nando
 */
import com.SolucionesNandoTech.WebPortalServicios.model.ERole;
import com.SolucionesNandoTech.WebPortalServicios.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleConverter {

    // Convertir Set<Role> a Collection<GrantedAuthority>
    public static Set<GrantedAuthority> convertRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toSet());
    }

    // Convertir Collection<GrantedAuthority> a Set<Role>
    public static Set<Role> convertAuthoritiesToRoles(Set<GrantedAuthority> authorities) {
        Set<Role> roles = new HashSet<>();
        for (GrantedAuthority authority : authorities) {
            ERole roleName = ERole.valueOf(authority.getAuthority());
            roles.add(new Role(null, roleName));
        }
        return roles;
    }
}
