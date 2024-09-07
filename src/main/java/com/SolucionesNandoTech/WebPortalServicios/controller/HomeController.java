/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Especialidad;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import com.SolucionesNandoTech.WebPortalServicios.repository.EspecialidadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author nando
 */
@Controller
public class HomeController {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @GetMapping("/")
    public String index() {
        return "index"; // Página de inicio pública
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Página de login
    }

    @GetMapping("/registrar")
    public String register(Model model) {
        String Prueba = "prueb";
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

}
