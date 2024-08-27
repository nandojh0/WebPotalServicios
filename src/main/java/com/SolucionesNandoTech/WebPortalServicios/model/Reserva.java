/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author nando
 */
@Entity
@Table(name = "reservas")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Usuario Usuario;
    
    @ManyToOne
    @JoinColumn(name = "tecnico_id", nullable = false)
    private Usuario tecnico;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(name = "fecha_reserva", updatable = false, nullable = false)
    private java.sql.Timestamp fechaReserva = new java.sql.Timestamp(System.currentTimeMillis());

    @Column(name = "fecha_servicio")
    private java.sql.Timestamp fechaServicio;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    public enum EstadoReserva {
        PENDIENTE,
        COMPLETADO,
        CANCELADO
    }
}
