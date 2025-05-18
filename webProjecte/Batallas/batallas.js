function cargarBatalla() {
    // Obtener el número de batalla ingresado por el usuario
    const numeroBatalla = document.getElementById("numeroBatalla").value;

    // Verificar si el número de batalla es válido
    if (!numeroBatalla || numeroBatalla <= 0) {
        alert("Por favor, introduce un número de batalla válido.");
        return;
    }

    // Construir la ruta del archivo HTML correspondiente
    const archivoBatalla = `battle${numeroBatalla}.html`;

    // Verificar si el archivo existe (esto requiere un servidor para funcionar correctamente)
    const iframe = document.getElementById("iframeBatalla");
    iframe.src = archivoBatalla;
    iframe.style.display = "block";

    // Mostrar un mensaje en caso de que el archivo no exista
    iframe.onerror = () => {
        alert(`El reporte de la batalla número ${numeroBatalla} no existe.`);
        iframe.style.display = "none";
    };
}