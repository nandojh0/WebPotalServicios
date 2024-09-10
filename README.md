# Proyecto Spring Boot

Este proyecto es una aplicación Spring Boot que se conecta a una base de datos MySQL. A continuación, se detallan las instrucciones para configurar y ejecutar el proyecto.

## Requisitos
- JDK 17
- Maven
- MySQL


## Implementación en la nube
## Configuración
Para que la aplicación funcione correctamente, debes configurar las siguientes variables de entorno en Railway.app:

- **Crea una Cuenta en Railway.app:

- **Regístrate en Railway.app si aún no lo has hecho.
Crea un Nuevo Proyecto: https://github.com/nandojh0/WebPotalServicios.git

- **Inicia sesión en Railway.app y crea un nuevo proyecto.
Conecta tu Repositorio de GitHub:

- **Dentro del proyecto en Railway.app, selecciona la opción para conectar un repositorio de GitHub. Esto permitirá que Railway.app construya y despliegue tu proyecto directamente desde GitHub.

- **MYSQL_HOSTNAME**: El nombre del host de tu base de datos.
portalserviciostecnicos-portalserviciostecnicos.g.aivencloud.com

- **MYSQL_PORT**: El puerto en el que tu base de datos está escuchando.
20796

- **MYSQL_DATABASE**: El nombre de la base de datos a la que te conectarás.
portal_servicios_tecnicos

- **MYSQL_USERNAME**: El nombre de usuario para la base de datos.
avnadmin

- **MYSQL_PASSWORD**: La contraseña para el nombre de usuario de la base de datos.
AVNS_nJXMtMYS6GQw2Vj2eHg

Configuración en Railway.app
Accede a tu proyecto en Railway.app.
Navega a la sección de "Settings" o "Environment Variables".
Agrega cada una de las variables de entorno mencionadas anteriormente.
Guarda los cambios.
Reinicia la aplicación para que los cambios tomen efecto.

## Archivo application.properties
Asegúrate de que el archivo application.properties de tu aplicación esté configurado de la siguiente manera:
spring.datasource.url=jdbc:mysql://${MYSQL_HOSTNAME}:${MYSQL_PORT}/${MYSQL_DATABASE}
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
Esto permitirá que la aplicación se conecte a la base de datos utilizando las variables de entorno configuradas en Railway.app.

## Implementación server tomcat
Generrar war del proyecto

Artefactos involucrados: 
●	WebPortalServicios.war
●	GoogleReCAPTCHA.jks
Preparación de ambiente: 
●	Versión JDK: 11
●	Ruta de gitHub: https://github.com/nandojh0/WebPotalServicios.git

Pasos de instalación:
●	Descargar  los archivos involucrados desde la ruta de gitHub.
●	Ingresar a la ruta /srv/apache-tomcat-17/webapps/ del servidor especificado.
●	Ingresar al manager del Tomcat y desplegar el archivo nuevo WebPortalServicios.war.





