/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Reserva;
import com.SolucionesNandoTech.WebPortalServicios.model.Servicio;
import com.SolucionesNandoTech.WebPortalServicios.model.Usuario;
import com.SolucionesNandoTech.WebPortalServicios.service.ReservaService;
import com.SolucionesNandoTech.WebPortalServicios.service.ServicioService;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nando
 */
@Controller
@RequestMapping("/user/reservas")
public class UsuarioReservaController {

    private final ServicioService servicioService;

    private final UsuarioService usuarioService;

    private final ReservaService reservaService;

    public UsuarioReservaController(ServicioService servicioService, UsuarioService usuarioService, ReservaService reservaService) {
        this.servicioService = servicioService;
        this.usuarioService = usuarioService;
        this.reservaService = reservaService;
    }

    @PostMapping("/crear")
    public String crearReserva(Authentication authentication, @RequestParam("servicioId") Long servicioId, @RequestParam("tecnicoId") Long tecnicoId, RedirectAttributes redirectAttributes) {

        // Verificar que el principal es una instancia de UsuarioDetails
        if (authentication.getPrincipal() instanceof Usuario) {
            Usuario cliente = (Usuario) authentication.getPrincipal();
            Servicio servicio = servicioService.obtenerServicio(servicioId);
            Usuario tecnico = usuarioService.obtenerUsuarioPorId(tecnicoId);

            if (servicio != null && tecnico != null && servicio.getCreador().equals(cliente)) {
                Reserva reserva = new Reserva();
                reserva.setServicio(servicio);
                reserva.setCliente(cliente);
                reserva.setTecnico(tecnico);
                reserva.setEstado(Reserva.EstadoReserva.PENDIENTE);

                reservaService.saveReserva(reserva);
                return "redirect:/user/reservas/lista";
            } else {
                // Manejar el caso cuando el servicio no es válido o no pertenece al cliente
                return "redirect:/error";
            }
        } else {
            redirectAttributes.addFlashAttribute("No autenticado");
            return "redirect:/login";
        }
    }

    @GetMapping("/lista")
    public String listarReservas(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        // Verificar que el principal es una instancia de UsuarioDetails
        if (authentication.getPrincipal() instanceof Usuario) {
            Usuario cliente = (Usuario) authentication.getPrincipal();
            List<Reserva> reservas = reservaService.findAllReservasById(cliente.getId());
            model.addAttribute("reservas", reservas);
            return "user/ReservasList"; // pagina de reservas
        } else {
            redirectAttributes.addFlashAttribute("No autenticado");
            return "redirect:/login";
        }
    }

    @PostMapping("/detalle")
    public String obtenerDetalleReserva(@RequestParam("reservaId") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reserva> optionalReserva = reservaService.findReservaById(id);

        if (!optionalReserva.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Reserva no encontrada.");
            return "redirect:/user/reservas/lista";
        }
        Reserva reserva = optionalReserva.get();
        model.addAttribute("reserva", reserva);
        model.addAttribute("servicio", reserva.getServicio());
        model.addAttribute("cliente", reserva.getCliente());
        model.addAttribute("tecnico", reserva.getTecnico());
        return "user/reserva-detalle"; // nombre de la vista Thymeleaf
    }

    @PostMapping("/editar")
    public String editarReserva(@RequestParam("reservaId") Long reservaId,
            @RequestParam("nombreServicio") String nombreServicio,
            @RequestParam("detalles") String detalles,
            @RequestParam("fechaServicio") String fechaServicioStr,
            RedirectAttributes redirectAttributes, Model model) {
        Optional<Reserva> optionalReserva = reservaService.findReservaById(reservaId);

        if (!optionalReserva.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Reserva no encontrada.");
            return "redirect:/user/reservas/lista";
        }

        Reserva reserva = optionalReserva.get();
        Servicio servicio = reserva.getServicio();

        if (!servicio.getNombre().equals(nombreServicio)) {
            servicio.setNombre(nombreServicio);
        }
        if (!servicio.getDescripcion().equals(detalles)) {
            servicio.setDescripcion(detalles);
        }
        
                    model.addAttribute("reserva", reserva);
            model.addAttribute("servicio", reserva.getServicio());
            model.addAttribute("cliente", reserva.getCliente());
            model.addAttribute("tecnico", reserva.getTecnico());

        try {
            // Definir el formato esperado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            // Convertir la cadena a LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(fechaServicioStr, formatter);
            // Convertir LocalDateTime a Timestamp
            Timestamp fechaServicio = Timestamp.valueOf(localDateTime);
            reserva.setFechaServicio(fechaServicio);
            model.addAttribute("sucess", "Fecha del servicio asinada");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Fecha de servicio no válida.");
            redirectAttributes.addFlashAttribute("error", "Fecha de servicio no válida.");
            return "user/reserva-detalle";
        }

        reservaService.saveReserva(reserva);
        servicioService.guardarServicio(servicio);
        return "user/reserva-detalle";
    }

}
