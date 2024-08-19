/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.services;
/**
 *
 * @author hernando.hernandez
 */
import com.SolucionesNandoTech.WebPortalServicios.utils.DocumentManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Service
public class GetIpAddress {

    @Autowired
    private DocumentManager doc;

    public String getIP() {
        try {
            // Obtener los atributos de la solicitud HTTP
            ServletRequestAttributes serv = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (serv != null) {
                HttpServletRequest req = serv.getRequest();
                return req.getRemoteAddr(); // Obtener y devolver la dirección IP
            } else {
                return "IP no capturada"; // No hay solicitud, retornar mensaje de error
            }
        } catch (Exception e) {
            // Registrar el error y devolver mensaje de error
            doc.write(1, "Error obteniendo IP: " + e.getMessage());
            return "IP no capturada";
        }
    }
}

