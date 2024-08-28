/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.model.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nando
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicioDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double costo;
    private Integer duracionEstimada; // Duración en minutos
    private Long creadorId; // Referencia al ID del creador
    private Set<Long> reservasIds; // Referencia a los IDs de las reservas
}
