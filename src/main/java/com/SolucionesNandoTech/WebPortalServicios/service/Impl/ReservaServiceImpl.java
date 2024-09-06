/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.service.Impl;

/**
 *
 * @author nando
 */
import com.SolucionesNandoTech.WebPortalServicios.model.Reserva;
import com.SolucionesNandoTech.WebPortalServicios.repository.ReservaRepository;
import com.SolucionesNandoTech.WebPortalServicios.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva saveReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public Optional<Reserva> findReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> findAllReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public void deleteReserva(Long id) {
        reservaRepository.deleteById(id);
    }

    @Override
    public List<Reserva> findAllReservasById(Long id) {
        return reservaRepository.findAllByClienteId(id);
    }
}
