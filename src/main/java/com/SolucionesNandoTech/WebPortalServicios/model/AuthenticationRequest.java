/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.model;

import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author hernando.hernandez
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;

    private static final Pattern PAT_USER = Pattern.compile("^[A-Za-z0-9_<>.^*%$#!-]{7,29}$");
    private static final Pattern PAT_PASSWORD = Pattern.compile("^[A-Za-z0-9_<>.^*%$#!-]{7,29}$");

    public String validateRequest() {
        String accept = "ok";
        if (this.email == null || this.email.equals("") || !PAT_USER.matcher(this.email).matches()) {
            accept = "username";
        }
        if (this.password == null || this.password.equals("") || !PAT_PASSWORD.matcher(this.password).matches()) {
            accept = "password";
        }
        return accept;
    }

}
