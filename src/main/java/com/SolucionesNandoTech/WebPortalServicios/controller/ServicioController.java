/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/user/servicios")
public class ServicioController {

    private final ServicioService servicioService;
    @Autowired
    private UsuarioService usuarioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/listar")
    public String listarServicios(Authentication authentication, Model model) {
        String prueba = "prueba";
        String emain = (String) authentication.getPrincipal();
        Usuario creador = usuarioService.authenticateUser(emain);
        List<Servicio> servicios = servicioService.obtenerServiciosPorCreador(creador.getId());
        model.addAttribute("servicios", servicios);
        return "servicios/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditarServicio(@PathVariable Long id, Model model) {
        Servicio servicio = servicioService.obtenerServicio(id);
        model.addAttribute("servicio", servicio);
        return "/user/ServiceForm";
    }


    @GetMapping("/crear")
    public String mostrarFormularioCrearServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "/user/ServiceForm";
    }

    @PostMapping("/guardar")
    public String crearServicio(@ModelAttribute Servicio servicio, Authentication authentication) {
        String emain = (String) authentication.getPrincipal();
        Usuario creador = usuarioService.authenticateUser(emain);
        servicio.setCreador(creador);
        servicioService.guardarServicio(servicio);
        return "redirect:/user/home";
    }
    
     @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        servicioService.eliminarServicio(id);
        return "redirect:/user/home";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalleDeServicio(@PathVariable Long id, Model model) {
        Servicio servicio = servicioService.obtenerServicio(id);
        model.addAttribute("servicio", servicio);
        return "/user/ServiceDetail";
    }
    
    
}
