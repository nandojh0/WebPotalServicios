/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.repository;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nando
 */
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByCreadorId(Long creadorId);
}
