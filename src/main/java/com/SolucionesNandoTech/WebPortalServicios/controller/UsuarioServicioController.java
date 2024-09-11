/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.ERole;
import com.SolucionesNandoTech.WebPortalServicios.model.Especialidad;
import com.SolucionesNandoTech.WebPortalServicios.model.Response;
import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nando
 */
@Controller
@RequestMapping("/user/servicios")
public class UsuarioServicioController {

    private final ServicioService servicioService;

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("/listar")
    public String listarServicios(Authentication authentication, Model model) {
        String emain = (String) authentication.getPrincipal();
        Usuario creador = usuarioService.authenticateUser(emain);
        List<Servicio> servicios = servicioService.obtenerServiciosPorCreador(creador.getId());
        model.addAttribute("servicios", servicios);
        return "servicios/lista";
    }

    @PostMapping("/editar")
    public String mostrarFormularioDeEditarServicio(@RequestParam("id") Long id, Model model) {
        Servicio servicio = servicioService.obtenerServicio(id);
        model.addAttribute("servicio", servicio);
        return "user/ServiceForm";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "user/ServiceForm";
    }

    @PostMapping("/guardar")
    public String crearServicio(@ModelAttribute Servicio servicio, Authentication authentication, RedirectAttributes redirectAttributes) {
// Verificar que el principal es una instancia de UsuarioDetails
        if (authentication.getPrincipal() instanceof Usuario creador) {
        servicio.setCreador(creador);
        servicioService.guardarServicio(servicio);
        return "redirect:/user/home";
        } else {
            redirectAttributes.addFlashAttribute("No autenticado");
            return "redirect:/login";
        }
    }

    @GetMapping("/eliminar/{id}")
    public ResponseEntity<Response<String>> eliminarServicio(@PathVariable Long id) {
        servicioService.eliminarServicio(id);
        Response<String> response = new Response<>("success", "Servicio eliminado con éxito", "home");
        return ResponseEntity.ok(response);
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
        // Asegurar que la reserva sea cargada
        List<Usuario> tecnicos = usuarioService.findByRolesNombreAndEnabled(ERole.TECHNICIAN, true);
        // Crear listas únicas para el filtrado
        List<String> nombresUnicos = tecnicos.stream()
                .map(Usuario::getNombre)
                .distinct()
                .collect(Collectors.toList());

        List<String> especialidadesUnicas = tecnicos.stream()
                .flatMap(t -> t.getEspecialidades().stream())
                .map(Especialidad::getNombre)
                .distinct()
                .collect(Collectors.toList());

        List<String> puntuacionesUnicas = tecnicos.stream()
                .map(t -> String.valueOf(t.getPuntuacion()))
                .distinct()
                .collect(Collectors.toList());
// Verifica si el servicio existe
        if (servicio == null) {
            // Maneja el caso cuando el servicio no se encuentra
            model.addAttribute("error", "Servicio no encontrado");
            return "error";
        }

        // Agrega el detalle del servicio al modelo
        model.addAttribute("servicio", servicio);
        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("nombresUnicos", nombresUnicos);
        model.addAttribute("especialidadesUnicas", especialidadesUnicas);
        model.addAttribute("puntuacionesUnicas", puntuacionesUnicas);
        return "user/ServiceDetail";
    }

}
