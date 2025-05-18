<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" />

    <xsl:template match="/">
        <html>
            <head>
                <title>Reporte de Batalla</title>
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