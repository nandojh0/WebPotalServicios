// scripts.js
document.getElementById('registrationForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = {
        nombre: document.getElementById('nombre').value,
        email: document.getElementById('email').value,
        telefono: document.getElementById('telefono').value,
        direccion: document.getElementById('direccion').value,
        password: document.getElementById('password').value,
        enabled: true,
        roles: [{ id: getRoleId(document.getElementById('role').value), name: document.getElementById('role').value }]
    };

    fetch('/WebPortalServicios/usuarios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
    .then(response => response.json())
    .then(data => {
        alert('Usuario registrado exitosamente');
        console.log(data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
});

function getRoleId(roleName) {
    switch(roleName) {
        case 'USER':
            return 1; // Reemplaza con el ID real del rol USER
        case 'TECHNICIAN':
            return 3; // Reemplaza con el ID real del rol TECHNICIAN
        default:
            return null;
    }
}
