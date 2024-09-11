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





