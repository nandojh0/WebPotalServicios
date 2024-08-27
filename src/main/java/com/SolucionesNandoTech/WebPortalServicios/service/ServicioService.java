/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.service;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import java.util.List;

/**
 *
 * @author nando
 */
public interface ServicioService {

    public List<Servicio> obtenerServiciosPorCreador(Long creadorId);

    public Servicio guardarServicio(Servicio servicio);

    public Servicio obtenerServicio(Long id);
    
    public void eliminarServicio(Long id);

}
