/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.controller;

import com.SolucionesNandoTech.WebPortalServicios.model.Response;
import com.SolucionesNandoTech.WebPortalServicios.model.dto.UsuarioDto;
import com.SolucionesNandoTech.WebPortalServicios.service.UsuarioService;
import com.SolucionesNandoTech.WebPortalServicios.utils.DocumentManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UsuarioDto usuarioDto) {
        Response response;
        try {
            usuarioService.registerNewUser(usuarioDto);
            response = new Response("000", "Succes");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response = new Response("020", "Excepcion en el Servicio", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
