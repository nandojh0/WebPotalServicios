/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Data
@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Usuario cliente;

    private String comentario;
    private int puntuacion;


    private Double calificacion;

    @Column(name = "fecha_resena", updatable = false, nullable = false)
    private java.sql.Timestamp fechaResena = new java.sql.Timestamp(System.currentTimeMillis());
}
