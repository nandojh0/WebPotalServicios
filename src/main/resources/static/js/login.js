document.addEventListener('DOMContentLoaded', function () {

    document.getElementById('login-form').addEventListener('submit', function (event) {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Obtén el token CSRF
        const csrfToken = document.getElementById('csrf-token').value;


        // Envia una solicitud POST al backend con los datos del usuario en JSON
        const loginData = {
            username: email,
            password: password
        };

        fetch('/WebPortalServicios/currentUser', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-XSRF-TOKEN': csrfToken // Incluir el token CSRF en el encabezado
            },
            body: JSON.stringify(loginData)
        })
                .then(response => response.json())
                .then(data => {
                    if (data.code === "000") {
                        // Redirige según el rol del usuario
                        switch (data.data) {
                            case 'ADMIN':
                                window.location.href = './admin/home';
                                break;
                            case 'USER':
//                                window.location.href = './user/home';
                                localStorage.setItem('token', data.token);
                                const token = localStorage.getItem('token');

                                fetch('/WebPortalServicios/user/home', {
                                    method: 'GET', // o 'POST', 'PUT', etc.
                                    headers: {
                                        'Authorization': `Bearer ${token}`,
                                        'Content-Type': 'application/json'
                                    }
                                })
                                        .then(response => response.json())
                                        .then(data => {
                                            console.log(data);
                                        })
                                        .catch(error => {
                                            console.error('Error:', error);
                                        });
                                SS
                                break;
                            case 'TECHNICIAN':
//                                window.location.href = '/WebPortalServicios/tech/home';
                                window.location.href = './tech/home';
//                                fetch('/WebPortalServicios/tech/home', {
//                                    method: 'GET',
//                                    credentials: 'same-origin', // Incluye cookies en la solicitud
//                                    headers: {
//                                        'X-XSRF-TOKEN': csrfToken // Incluye el token CSRF en el encabezado
//                                    }
//                                })
//                                        .then(response => {
//                                            if (!response.ok) {
//                                                throw new Error('No autorizado');
//                                            }
//                                            return response.json();
//                                        })
//                                        .then(data => {
//                                            // Maneja los datos de respuesta
//                                            console.log("Datos recibidos:", data);
//                                        })
//                                        .catch(error => {
//                                            console.error("Error:", error.message);
//                                            alert('Ocurrió un error. Por favor, inténtalo de nuevo más tarde 2.');
//                                        });
                                break;
                            default:
                                alert('Rol no reconocido.');
                        }
                    } else {
                        console.log("Login failed, message:", data.message);
                        alert('Credenciales inválidas. Inténtalo de nuevo.');
                    }
                })
                .catch(error => {
                    console.error("Error:", error.message);
                    alert('Ocurrió un error. Por favor, inténtalo de nuevo más tarde.');
                });
    });
});

