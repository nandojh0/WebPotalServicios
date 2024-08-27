/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SolucionesNandoTech.WebPortalServicios.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author hernando.hernandez
 */
@Component
public class JWTUtil {
    
    
    @Autowired
    private Environment env;

   
     public String generateToken(UserDetails userDetails) {
        Key key = Keys.hmacShaKeyFor(env.getRequiredProperty("SPRING.JWT.KEY").getBytes());
        /*
         * Documentacion de seguridad: La funcion Date(long date) no esta
         * deprecada. Esto se puede corroborar en la documentación oficial de la
         * clase Date
         */
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * Integer.parseInt(env.getRequiredProperty("SPRING.JWT.TIMEOUT"))))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
private Claims getClaims(String token) {
        Key key = Keys.hmacShaKeyFor(env.getRequiredProperty("SPRING.JWT.KEY").getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
