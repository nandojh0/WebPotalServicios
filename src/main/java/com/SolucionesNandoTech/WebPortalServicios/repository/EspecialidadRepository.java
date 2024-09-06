/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.repository;

import com.SolucionesNandoTech.WebPortalServicios.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nando
 */
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    
}
