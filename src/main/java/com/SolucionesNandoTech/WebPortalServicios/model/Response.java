/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 *
 * @author hernando.hernandez
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    
    private String message;
    private String code;
    private T data;

    public Response( String code,String message, T data) {
        this.message = message;
        this.code = code;
        this.data = data = data;
    }

    public Response(String code,String message) {
        this.message = message;
        this.code = code;
    }

    public Response() {
    }

    public void sendRedirect(String redirectUrl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
