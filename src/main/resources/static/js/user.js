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

//document.addEventListener('DOMContentLoaded', function() {
//    var tecnicoElement = document.getElementById('tecnico');
//    if (tecnicoElement) {
//        tecnicoElement.addEventListener('change', function() {
//            console.log("Evento change disparado");
//
//            var selectedTecnico = this.options[this.selectedIndex];
//            console.log("Nombre del técnico seleccionado:", selectedTecnico.text);
//            console.log("Especialidad:", selectedTecnico.getAttribute('data-especialidad'));
//            console.log("Puntuación:", selectedTecnico.getAttribute('data-puntuacion'));
//
//            document.getElementById('tecnicoNombre').textContent = selectedTecnico.text;
//            document.getElementById('tecnicoEspecialidad').textContent = selectedTecnico.getAttribute('data-especialidad');
//            document.getElementById('tecnicoPuntuacion').textContent = selectedTecnico.getAttribute('data-puntuacion');
//        });
//    }
//});

document.addEventListener('DOMContentLoaded', function() {
    // Obtener elementos del modal
    const tecnicoModal = document.getElementById('tecnicoModal');
    const tecnicoNombre = document.getElementById('tecnicoNombre');
    const tecnicoEspecialidad = document.getElementById('tecnicoEspecialidad');
    const tecnicoPuntuacion = document.getElementById('tecnicoPuntuacion');
    const modalTecnicoId = document.getElementById('modalTecnicoId'); // Input oculto para el ID del técnico

    // Seleccionar todos los botones de detalles del técnico
    const viewDetailsButtons = document.querySelectorAll('.view-details');

    // Agregar un evento de clic a cada botón
    viewDetailsButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Obtener los atributos de datos del botón
            const nombre = this.getAttribute('data-nombre');
            const especialidades = this.getAttribute('data-especialidades');
            const puntuacion = this.getAttribute('data-puntuacion');
            const tecnicoId = this.getAttribute('data-id');

            // Actualizar el contenido del modal con los datos del técnico
            tecnicoNombre.textContent = nombre;
            tecnicoEspecialidad.textContent = especialidades;
            tecnicoPuntuacion.textContent = puntuacion;
            modalTecnicoId.value = tecnicoId; // Establecer el ID del técnico en el input oculto
        });
    });
});

//buscar tecnico input "searchInput"
 document.getElementById('searchInput').addEventListener('keyup', function() {
            let input = document.getElementById('searchInput');
            let filter = input.value.toLowerCase();
            let rows = document.querySelectorAll('tbody tr');

            rows.forEach(function(row) {
                let nombre = row.querySelector('td:nth-child(1)').textContent.toLowerCase();
                let especialidades = row.querySelector('td:nth-child(2)').textContent.toLowerCase();
                let puntuacion = row.querySelector('td:nth-child(3)').textContent.toLowerCase();

                if (nombre.indexOf(filter) > -1 || especialidades.indexOf(filter) > -1 || puntuacion.indexOf(filter) > -1) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        });
document.getElementById('filterNombre').addEventListener('change', filterTable);
document.getElementById('filterEspecialidades').addEventListener('change', filterTable);
document.getElementById('filterPuntuacion').addEventListener('change', filterTable);

function filterTable() {
    let nombreSelect = document.getElementById('filterNombre').value.toLowerCase();
    let especialidadesSelect = document.getElementById('filterEspecialidades').value.toLowerCase();
    let puntuacionSelect = document.getElementById('filterPuntuacion').value.toLowerCase();
    let tableBody = document.getElementById('tecnicoTableBody');
    let rows = tableBody.getElementsByTagName('tr');

    for (var i = 0; i < rows.length; i++) {
        let row = rows[i];
        let nameCell = row.getElementsByTagName('td')[0];
        let specialtiesCell = row.getElementsByTagName('td')[1];
        let scoreCell = row.getElementsByTagName('td')[2];

        let nameText = nameCell.textContent.toLowerCase();
        let specialtiesText = specialtiesCell.textContent.toLowerCase();
        let scoreText = scoreCell.textContent.toLowerCase();

        if (
            (nombreSelect === "" || nameText.includes(nombreSelect)) &&
            (especialidadesSelect === "" || specialtiesText.includes(especialidadesSelect)) &&
            (puntuacionSelect === "" || scoreText.includes(puntuacionSelect))
        ) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    }
}




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



