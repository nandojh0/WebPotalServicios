/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Especialidad;
import com.SolucionesNandoTech.WebPortalServicios.model.Response;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import com.SolucionesNandoTech.WebPortalServicios.repository.EspecialidadRepository;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import com.SolucionesNandoTech.WebPortalServicios.utils.DocumentManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nando
 */
@Controller
public class PrincipalControllers {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocumentManager doc;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @GetMapping("/")
    public String index() {
        return "index"; // Página de inicio pública
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciales incorrectas");
        }
        return "login"; // Nombre de la plantilla Thymeleaf
    }

    @GetMapping("/registrar")
    public String register(Model model) {
        // Obtener todas las especialidades desde la base de datos
        List<Especialidad> especialidades = especialidadRepository.findAll();

        // Convertir la lista a un conjunto para evitar duplicados (si es necesario)
        Set<Especialidad> especialidadesSet = new HashSet<>(especialidades);
        // Añadir las especialidades al modelo con una clave específica
        model.addAttribute("especialidades", especialidadesSet);

        // Opcional: Añadir un objeto UsuarioDto al modelo si estás usando Thymeleaf
        model.addAttribute("usuarioDto", new UsuarioDto());

        // Devolver el nombre de la vista que Thymeleaf debe renderizar
        return "registrar"; // Página de registro
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDto usuarioDto) {
        Response response;
        try {
            usuarioService.registerNewUser(usuarioDto);
            response = new Response("000", "Succes");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = new Response("020", "Excepcion en el Servicio", e.getMessage());
            doc.write(1, "/register Excepction:" + e);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
