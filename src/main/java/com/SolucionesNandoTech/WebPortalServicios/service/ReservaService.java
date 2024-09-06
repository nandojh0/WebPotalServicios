/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.service;

import com.SolucionesNandoTech.WebPortalServicios.model.Reserva;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nando
 */
public interface ReservaService {
    
    Reserva saveReserva(Reserva reserva);
    Optional<Reserva> findReservaById(Long id);
    List<Reserva> findAllReservas();
    List<Reserva> findAllReservasById(Long id);
    void deleteReserva(Long id);
    
}
