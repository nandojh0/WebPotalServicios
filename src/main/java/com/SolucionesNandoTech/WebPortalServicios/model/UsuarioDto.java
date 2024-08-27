/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nando
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    
    @NotBlank
    private String nombre;
    
    @Email
    @NotBlank
    private String email;
    private String telefono;
    private String direccion;
    @NotBlank
    private String password;
    private Set<String> roles;
    private String especialidad;
    private Double calificacion;
    
    String regexNombre = "^[A-Za-z������������\\s]+$";
    String regexEmail = "^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,6}$";
    String regexTelefono = "^(\\+\\d{1,3})?\\d{7,10}$";
    String regexDireccion = "^[A-Za-z0-9������������\\s.,#-]+$";
    String regexPassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";



    public String validateAll() {
        String response = "ok";
        if (this.nombre == null || !this.nombre.matches(regexNombre)) {
            response = "nombre";
        } 

        if (this.email == null || this.email.matches(regexEmail)) {
            response = "email";
        }

        if (this.telefono == null || this.telefono.matches(regexTelefono)) {
             response = "telefono";
        }

        if (this.direccion == null || this.direccion.matches(regexDireccion)) {
            response = "direccion";
        }

        if (this.password == null || this.password.matches(regexPassword)) {
             response = "password";
        } 
        return response;
    }
    
}
