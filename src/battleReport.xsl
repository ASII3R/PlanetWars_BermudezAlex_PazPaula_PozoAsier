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
                <h2>N&uacute;mero de Batalla: <xsl:value-of select="battleReport/battleNumber" /></h2>
                <h3>Ej&eacute;rcito del Planeta</h3>
                <ul style="letter-spacing:2px;">
                    <xsl:for-each select="battleReport/planetArmy/units/unit">
                        <li style="letter-spacing:2px;">
                            <xsl:value-of select="@type" /> x<xsl:value-of select="@count" />
                        </li>
                    </xsl:for-each>
                </ul>
                <h3>Ej&eacute;rcito Enemigo</h3>
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