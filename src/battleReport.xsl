<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" />

    <xsl:template match="/">
        <html>
            <head>
                <title>Reporte de Batalla</title>
                <link href="https://fonts.googleapis.com/css2?family=Press+Start+2P&amp;display=swap" rel="stylesheet"/>
                <style>
                    html, body, * {
                        background: #220044 !important;
                        color: #00ff99 !important;
                        font-family: 'Press Start 2P', 'Courier New', monospace !important;
                        font-size: 1em !important;
                        margin: 0 !important;
                        padding: 0 !important;
                        box-sizing: border-box;
                    }
                    h1, h2, h3, h4, h5, h6 {
                        color: #00ff99 !important;
                        text-shadow: 0 0 8px #00ff99;
                    }
                    ul, li {
                        color: #00ff99 !important;
                        font-size: 1em !important;
                    }
                    strong, b { color: #00ff99 !important; }
                    table, th, td {
                        border: 2px solid #00ff99 !important;
                        color: #00ff99 !important;
                        background: transparent !important;
                    }
                    tr:nth-child(even) { background: #0a0033 !important; }
                    tr:nth-child(odd) { background: #220044 !important; }
                    a { color: #00ff99 !important; text-decoration: underline; }
                </style>
            </head>
            <body>
                <h1>Reporte de Batalla</h1>
                <h2>Número de Batalla: <xsl:value-of select="battleReport/battleNumber" /></h2>

                <h3>Ejército del Planeta</h3>
                <ul>
                    <xsl:for-each select="battleReport/planetArmy/units/unit">
                        <li>
                            <xsl:value-of select="@type" />: <xsl:value-of select="@count" />
                        </li>
                    </xsl:for-each>
                </ul>

                <h3>Ejército Enemigo</h3>
                <ul>
                    <xsl:for-each select="battleReport/enemyArmy/units/unit">
                        <li>
                            <xsl:value-of select="@type" />: <xsl:value-of select="@count" />
                        </li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>