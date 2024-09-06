/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.service;

import com.SolucionesNandoTech.WebPortalServicios.model.ERole;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author nando
 */
public interface UsuarioService extends UserDetailsService {

    public Usuario registerNewUser(UsuarioDto registroDTO);

     public Usuario authenticateUser(String email);
     
     public Usuario obtenerUsuarioPorId(long id);
    
    public boolean isEmailTaken(String email);
    // Método para obtener usuarios por rol y que estén habilitados
    List<Usuario> findByRolesNombreAndEnabled(ERole roleName, boolean enabled);
    
}
