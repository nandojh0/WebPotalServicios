/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.service.Impl;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.repository.ServicioRepository;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author nando
 */
@Service
public class ServicioServiceImpl implements ServicioService {

    
    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }
    
    @Override
    public List<Servicio> obtenerServiciosPorCreador(Long creadorId) {
        return servicioRepository.findByCreadorId(creadorId);
    }

    @Override
    public Servicio guardarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio obtenerServicio(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }
    
    @Override
    public void eliminarServicio(Long id) {
        servicioRepository.deleteById(id);
    }

}
