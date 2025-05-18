function cargarBatalla() {
    const numeroBatalla = document.getElementById("numeroBatalla").value;

    if (!numeroBatalla || numeroBatalla <= 0) {
        alert("Por favor, introduce un número de batalla válido.");
        return;
    }

    const archivoBatalla = `battle${numeroBatalla}.html`;
    const iframe = document.getElementById("iframeBatalla");
    iframe.src = archivoBatalla;
    iframe.style.display = "block";
    iframe.style.border = "4px solid #00ff99";
    iframe.style.boxShadow = "0 0 30px #00ff99";

    iframe.onload = () => {
        try {
            const doc = iframe.contentDocument || iframe.contentWindow.document;

            // Asegura que hay <head>
            if (!doc.head) {
                const head = doc.createElement("head");
                doc.documentElement.insertBefore(head, doc.body);
            }

            // Inyecta la fuente pixelada
            const fontLink = doc.createElement("link");
            fontLink.rel = "stylesheet";
            fontLink.href = "https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap";
            doc.head.appendChild(fontLink);

            // Inyecta los estilos retro
            const style = doc.createElement("style");
            style.textContent = `
                body, html {
                    background: #220044 !important;
                    color: #00ff99 !important;
                    font-family: 'Press Start 2P', 'Courier New', monospace !important;
                    font-size: 1em !important;
                    margin: 0 !important;
                    padding: 20px !important;
                }
                h1, h2, h3, h4, h5, h6 {
                    color: #00ff99 !important;
                    font-family: 'Press Start 2P', 'Courier New', monospace !important;
                    text-shadow: 0 0 8px #00ff99;
                }
                table, th, td {
                    border: 2px solid #00ff99 !important;
                    color: #00ff99 !important;
                    font-family: 'Press Start 2P', 'Courier New', monospace !important;
                    background: transparent !important;
                }
                tr:nth-child(even) { background: #0a0033 !important; }
                tr:nth-child(odd) { background: #220044 !important; }
                a { color: #00ff99 !important; text-decoration: underline; }
                strong, b { color: #00ff99 !important; }
            `;
            doc.head.appendChild(style);
        } catch (e) {
            // Si hay un error (por ejemplo, por CORS), no hacer nada
        }
    };

    iframe.onerror = () => {
        alert(`El reporte de la batalla número ${numeroBatalla} no existe.`);
        iframe.style.display = "none";
    };
}