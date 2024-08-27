/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author nando
 */
@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/listar")
    public String listarServicios(@PathVariable Long id,Model model) {
        model.addAttribute("servicios", servicioService.obtenerServiciosPorCreador(id));
        return "servicios/lista";
    }

    @GetMapping("/detalle/{id}")
    public String detalleServicio(@PathVariable Long id, Model model) {
        model.addAttribute("servicio", servicioService.obtenerServicio(id));
        return "servicios/detalle";
    }

    @PostMapping("/crear")
    public String crearServicio(@ModelAttribute Servicio servicio) {
        servicioService.guardarServicio(servicio);
        return "redirect:/servicios/listar";
    }
}