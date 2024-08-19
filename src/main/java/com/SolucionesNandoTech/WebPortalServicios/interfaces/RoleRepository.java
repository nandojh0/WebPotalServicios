/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.interfaces;

import com.SolucionesNandoTech.WebPortalServicios.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nando
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}