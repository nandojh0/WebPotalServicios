package com.SolucionesNandoTech.WebPortalServicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebPortalServiciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebPortalServiciosApplication.class, args);
	}

}
