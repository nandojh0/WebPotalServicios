document.addEventListener('DOMContentLoaded', function () {
    const tipoUsuarioSelect = document.getElementById('tipo-usuario');
    const camposTecnicoDiv = document.getElementById('campos-tecnico');
    const especialidadesSelect = document.getElementById('especialidades');
    const listaEspecialidades = document.getElementById('lista-especialidades');
    const agregarEspecialidadBtn = document.getElementById('agregar-especialidad');
     const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('confirm-password');
    const passwordMatchMessage = document.getElementById('password-match-message');
    const buscadorEspecialidades = document.getElementById('buscador-especialidades');
    const listaEspecialidadesSelect = document.getElementById('especialidades');
    tipoUsuarioSelect.addEventListener('change', function () {
        if (this.value === 'tecnico') {
            camposTecnicoDiv.classList.remove('oculto');
        } else {
            camposTecnicoDiv.classList.add('oculto');
            listaEspecialidades.innerHTML = ''; // Clear the list if not "Técnico"
        }
    });


    agregarEspecialidadBtn.addEventListener('click', function () {
        const selectedOption = especialidadesSelect.options[especialidadesSelect.selectedIndex];
        if (selectedOption) {
            const especialidadId = selectedOption.value;
            const especialidadNombre = selectedOption.textContent;

            if (!document.querySelector(`li[data-id='${especialidadId}']`)) {
                const li = document.createElement('li');
                li.classList.add('especialidad-item');
                li.setAttribute('data-id', especialidadId);
                li.textContent = especialidadNombre;

                const eliminarBtn = document.createElement('button');
                eliminarBtn.textContent = 'Eliminar';
                eliminarBtn.addEventListener('click', function () {
                    listaEspecialidades.removeChild(li);
                });

                li.appendChild(eliminarBtn);
                listaEspecialidades.appendChild(li);
            }
        }
    });
    // Filtrado de especialidades mediante el buscador
    buscadorEspecialidades.addEventListener('input', function() {
        const filter = buscadorEspecialidades.value.toLowerCase();
        const options = listaEspecialidadesSelect.getElementsByTagName('option');

        for (let i = 0; i < options.length; i++) {
            const especialidad = options[i].textContent || options[i].innerText;
            if (especialidad.toLowerCase().indexOf(filter) > -1) {
                options[i].style.display = '';
            } else {
                options[i].style.display = 'none';
            }
        }
    });
    
    // Verificación en tiempo real de las contraseñas
    function checkPasswordMatch() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        if (confirmPassword !== '' && password !== confirmPassword) {
            passwordMatchMessage.style.display = 'block';
        } else {
            passwordMatchMessage.style.display = 'none';
        }
    }

    passwordInput.addEventListener('input', checkPasswordMatch);
    confirmPasswordInput.addEventListener('input', checkPasswordMatch);

    // Al enviar el formulario
    document.getElementById('registro-form').addEventListener('submit', function (event) {
        event.preventDefault(); // Previene el envío por defecto del formulario
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;

        // Evitar el envío si las contraseñas no coinciden
        if (password !== confirmPassword) {
           Swal.fire({
                icon: 'error',
                title: 'Error',
                text: 'Las contraseñas no coinciden. Por favor, verifica.'
            });
            return;
        }
        
        // Obtén los valores del formulario
        const nombre = document.getElementById('nombre').value;
        const email = document.getElementById('email').value;
        const telefono = document.getElementById('telefono').value;
        const direccion = document.getElementById('direccion').value;
        const tipoUsuario = document.getElementById('tipo-usuario').value;
        // Obtén las especialidades seleccionadas si el tipo de usuario es 'tecnico'
        let especialidades = [];
        if (tipoUsuario === 'tecnico') {
            const especialidadesListItems = document.querySelectorAll('#lista-especialidades li');
            especialidadesListItems.forEach(item => {
                especialidades.push(parseInt(item.getAttribute('data-id'), 10));  // Convertir a número y agregar al array
            });
        }

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
            especialidades: especialidades // Ahora es un array de IDs de especialidades
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
                           Swal.fire({
                            icon: 'success',
                            title: 'Registro exitoso',
                            text: 'Registro exitoso. Redirigiendo al login...',
                            timer: 2000,
                            showConfirmButton: false
                        }).then(() => {
                            window.location.href = '/WebPortalServicios/login';
                        });
                        } else if (data.code === '020') {
                             Swal.fire({
                            icon: 'warning',
                            title: 'Advertencia',
                            text: 'El usuario ya esta registrado'
                        });
                        } else {
                            Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Error al registrar usuario: ' + data.data
                        });
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
