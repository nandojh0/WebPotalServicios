/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.ServicioDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    
//    @PostMapping("/editar")
//    public String mostrarFormularioDeEditarServicio(@RequestBody ServicioDto servicioDto, Model model) {
//        Servicio servicio = servicioService.obtenerServicio(servicioDto.getId());
//        model.addAttribute("servicio", servicio);
//        return "/user/ServiceForm";
//    }
    
    @PostMapping("/editar")
    public String mostrarFormularioDeEditarServicio(@RequestParam("id") Long id, Model model) {
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

    //con js
//    @PostMapping("/detalle")
//    public String mostrarDetalleDeServicio(@RequestBody ServicioDto servicioDto, Model model) {
//        Servicio servicio = servicioService.obtenerServicio(servicioDto.getId());
//        model.addAttribute("servicio", servicio);
//        return "/user/ServiceDetail";
//    }
    @PostMapping("/detalle")
    public String postServicioDetalle(@RequestParam("id") Long id, Model model) {
        // Obtén el detalle del servicio usando el ID proporcionado
        Servicio servicio = servicioService.obtenerServicio(id);

        // Verifica si el servicio existe
        if (servicio == null) {
            // Maneja el caso cuando el servicio no se encuentra (opcional)
            model.addAttribute("error", "Servicio no encontrado");
            return "error";
        }

        // Agrega el detalle del servicio al modelo
        model.addAttribute("servicio", servicio);
        return "/user/ServiceDetail";
    }
    
    
}
