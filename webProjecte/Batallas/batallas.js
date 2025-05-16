function cargarBatalla() {
    const num = document.getElementById('numeroBatalla').value;
    const iframe = document.getElementById('iframeBatalla');
    const contenido = document.getElementById('batallaContenido');
    if (num && !isNaN(num)) {
        iframe.src = `battleReport${num}.html`;
        iframe.style.display = 'block';
        if (contenido) {
            const ps = contenido.getElementsByTagName('p');
            if (ps.length > 0) ps[0].style.display = 'none';
        }
    } else {
        iframe.style.display = 'none';
        iframe.src = '';
        alert('Introduce un número de batalla válido');
    }
}