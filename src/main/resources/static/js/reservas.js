document.addEventListener('DOMContentLoaded', function () {
        const form = document.querySelector('#editReservaModal form');
        const fechaServicioInput = document.querySelector('#fechaServicio');

        form.addEventListener('submit', function (event) {
            const fechaServicio = new Date(fechaServicioInput.value);
            const fechaActual = new Date();

            // Comparar la fecha de servicio con la fecha actual
            if (fechaServicio < fechaActual) {
                event.preventDefault(); // Evitar el envío del formulario
                alert('La fecha de servicio no puede ser menor a la fecha actual.');
            }
        });
    });