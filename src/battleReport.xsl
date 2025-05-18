<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" />

    <xsl:template match="/">
        <html>
            <head>
                <META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <title>Reporte de Batalla</title>
            </head>
            <body>
                <h1>Reporte de Batalla</h1>
                <h2>Número de Batalla: <xsl:value-of select="battleReport/battleNumber" /></h2>

                <!-- Resultado de la batalla -->
                <h3>Resultado:</h3>
                <p>
                    <xsl:choose>
                        <xsl:when test="battleReport/result = 'win'">
                            ¡El Ejército del Planeta ha ganado!
                        </xsl:when>
                        <xsl:otherwise>
                            El Ejército del Planeta ha perdido.
                        </xsl:otherwise>
                    </xsl:choose>
                </p>

                <!-- Recursos ganados -->
                <h3>Recursos Ganados:</h3>
                <p>Metal: <xsl:value-of select="battleReport/resources/metal" /></p>
                <p>Deuterium: <xsl:value-of select="battleReport/resources/deuterium" /></p>

                <!-- Ejército del Planeta -->
                <h3>Ejército del Planeta</h3>
                <p><xsl:value-of select="battleReport/planetArmy/description" /></p>
                <ul style="letter-spacing:2px;">
                    <xsl:for-each select="battleReport/planetArmy/units/unit">
                        <li style="letter-spacing:2px;">
                            <xsl:value-of select="@type" /> x<xsl:value-of select="@count" />
                        </li>
                    </xsl:for-each>
                </ul>

                <!-- Ejército Enemigo -->
                <h3>Ejército Enemigo</h3>
                <p><xsl:value-of select="battleReport/enemyArmy/description" /></p>
                <ul class="enemigo" style="letter-spacing:2px;">
                    <xsl:for-each select="battleReport/enemyArmy/units/unit">
                        <li style="letter-spacing:2px;">
                            <xsl:value-of select="@type" /> x<xsl:value-of select="@count" />
                        </li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>