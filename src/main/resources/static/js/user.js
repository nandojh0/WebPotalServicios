function getCsrfToken() {
    // Obtiene el valor del input oculto
    const csrfToken = document.getElementById('csrfToken').value;

    if (!csrfToken) {
        console.error('CSRF token not found in hidden input');
    } else {
        console.log('CSRF token found:', csrfToken);
    }

    return csrfToken;
}

document.addEventListener("DOMContentLoaded", function () {
    // Se asegura que el DOM esté completamente cargado antes de ejecutar cualquier código
});

//function verDetalles(idServicio) {
//    const servicioDto = {
//        id: idServicio
//    };
//    fetch(`/WebPortalServicios/user/servicios/detalle`, {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json',
//            'X-XSRF-TOKEN': getCsrfToken() // Incluye el token CSRF
//        },
//        body: JSON.stringify(servicioDto)
//    })
//            .then(response => response.json())
//            .then(servicio => {
//                alert(`Detalles del Servicio:\nNombre: ${servicio.nombre}\nDescripción: ${servicio.descripcion}\nCosto: ${servicio.costo}\nDuración: ${servicio.duracionEstimada} min`);
//            });
//}

function verDetalles(idServicio) {
    const servicioDto = {
        id: idServicio
    };
    fetch(`/WebPortalServicios/user/servicios/detalle`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfToken() // Incluye el token CSRF
        },
        body: JSON.stringify(servicioDto)
    })
    .then(response => {
        if (response.ok) {
            return response.text(); // Esperamos el HTML de la nueva página
        } else {
            throw new Error('Error al obtener los detalles del servicio');
        }
    })
    .then(html => {
        // Cargamos la página de detalles en el DOM
        document.open();
        document.write(html);
        document.close();
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

//function editarServicio(idServicio) {
//    const servicioDto = {
//        id: idServicio
//    };
//    // Redirigir a la página de edición con el id del servicio
//    window.location.href = `/servicios/editar/${id}`;
//}
function editarServicio(idServicio) {
    const servicioDto = {
        id: idServicio
    };
    
    fetch(`/WebPortalServicios/user/servicios/editar`, {
       method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-XSRF-TOKEN': getCsrfToken() // Incluye el token CSRF
        },
        body: JSON.stringify(servicioDto)
    })
    .then(response => {
        if (response.ok) {
            return response.text(); // Esperamos el HTML de la página de edición
        } else {
            throw new Error('Error al cargar la página de edición');
        }
    })
    .then(html => {
        // Cargamos la página de edición en el DOM
        document.open();
        document.write(html);
        document.close();
    })
    .catch(error => {
        console.error('Error:', error);
    });
}


//function eliminarServicio(id) {
//    if (confirm('¿Está seguro de que desea eliminar este servicio?')) {
//        fetch(`/user/servicios/eliminar/${id}`, {
//            method: 'GET'
//        }).then(() => {
//            alert('Servicio eliminado correctamente');
//            window.location.reload(); // Recargar la página para actualizar la lista
//        });
//    }
//}
function eliminarServicio(id) {
    if (confirm('¿Está seguro de que desea eliminar este servicio?')) {
        fetch(`/WebPortalServicios/user/servicios/eliminar/${id}`, {
            method: 'GET',
            headers: {
                'X-XSRF-TOKEN': getCsrfToken() // Incluye el token CSRF
            }
        })
        .then(response => {
            if (response.ok) {
                return response.text(); // Esperamos el HTML de la nueva página o confirmación
            } else {
                throw new Error('Error al eliminar el servicio');
            }
        })
        .then(html => {
            // Cargamos la página de confirmación en el DOM
            document.open();
            document.write(html);
            document.close();
        })
        .catch(error => {
            console.error('Error:', error);
        });
    }
}



