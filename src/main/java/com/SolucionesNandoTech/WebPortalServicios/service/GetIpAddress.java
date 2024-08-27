/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.service;


import com.SolucionesNandoTech.WebPortalServicios.utils.DocumentManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author hernando.hernandez
 */
@Service
public class GetIpAddress {

    @Autowired
    private DocumentManager doc;

    public String getIP() {

        String ipAdr;
        try {
            if (RequestContextHolder.getRequestAttributes() != null) {
                ServletRequestAttributes serv = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (serv != null) {
                    HttpServletRequest req = serv.getRequest();
                    ipAdr = req.getRemoteAddr();
                } else {
                    ipAdr = "IP no capturada";
                }

            } else {
                ipAdr = "IP no capturada";
            }
        } catch (Exception e) {
            doc.write(1, "  Error obteniendo IP: " + e);
            ipAdr = "IP no capturada";
        }
        return ipAdr;
    }
}
