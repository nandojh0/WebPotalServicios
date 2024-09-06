/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.repository;

/**
 *
 * @author nando
 */
import com.SolucionesNandoTech.WebPortalServicios.model.ERole;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    boolean existsByEmail(String email);
    
    @Override
    Optional<Usuario> findById(Long id);
    
    // Método para obtener usuarios por rol y que estén habilitados
    @Query("SELECT u FROM Usuario u JOIN u.roles r WHERE r.name = :roleName AND u.enabled = :enabled")
    List<Usuario> findByRoleAndEnabled(@Param("roleName") ERole roleName, @Param("enabled") boolean enabled);

}
