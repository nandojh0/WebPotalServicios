document.addEventListener('DOMContentLoaded', function () {
    const tipoUsuarioSelect = document.getElementById('tipo-usuario');
    const camposTecnicoDiv = document.getElementById('campos-tecnico');

    tipoUsuarioSelect.addEventListener('change', function () {
        if (this.value === 'tecnico') {
            camposTecnicoDiv.classList.remove('oculto');
        } else {
            camposTecnicoDiv.classList.add('oculto');
        }
    });

    document.getElementById('registro-form').addEventListener('submit', function (event) {
        event.preventDefault(); // Previene el envío por defecto del formulario

        // Obtén los valores del formulario
        const nombre = document.getElementById('nombre').value;
        const email = document.getElementById('email').value;
        const telefono = document.getElementById('telefono').value;
        const direccion = document.getElementById('direccion').value;
        const password = document.getElementById('password').value;
        const tipoUsuario = document.getElementById('tipo-usuario').value;
        const especialidad = tipoUsuario === 'tecnico' ? document.getElementById('especialidad').value : null;
       

        // Obtén el token CSRF
        const csrfToken = document.getElementById('csrf-token').value;


        // Construye el objeto JSON con los datos del usuario
        const userData = {
            email: email,
            password: password,
            nombre: nombre,
            telefono: telefono,
            direccion: direccion,
            roles: tipoUsuario === 'tecnico' ? ['TECHNICIAN'] : ['USER'],
            especialidad: especialidad
        };
        console.log('CSRF Token:', csrfToken);
        console.log('User Data:', userData);

        
        // Envia una solicitud POST al backend con los datos del usuario en JSON
        fetch('/WebPortalServicios/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': csrfToken
                },
                body: JSON.stringify(userData)
            })
            .then(response => {
                console.log('Response Status:', response.status); // Verifica el estado de la respuesta
                return response.text(); // Usa text() para obtener la respuesta cruda
            })
            .then(text => {
                console.log('Response Text:', text); // Muestra la respuesta en bruto
                try {
                    const data = JSON.parse(text); // Intenta analizar el texto como JSON
                    if (data.code == '000') {
                        alert('Registro exitoso');
                        window.location.href = '/WebPortalServicios/login';
                    } else {
                        alert('Error al registrar usuario: ' + data.message);
                    }
                } catch (e) {
                    console.error('Error parsing JSON:', e);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });

    });
});
