<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" encoding="UTF-8"/>
  <xsl:template match="/battleReport">
    <html>
      <head>
        <title>Battle Report <xsl:value-of select="battleNumber"/></title>
      </head>
      <body>
        <h1>Battle Report <xsl:value-of select="battleNumber"/></h1>
        <h2>Statistics</h2>
        <table border="1">
          <tr>
            <th>Unit</th>
            <th>Planet Units</th>
            <th>Planet Drops</th>
            <th>Enemy Units</th>
            <th>Enemy Drops</th>
          </tr>
          <tr>
            <td>Light Hunter</td>
            <td><xsl:value-of select="statistics/planetUnits/lightHunter/@units"/></td>
            <td><xsl:value-of select="statistics/planetUnits/lightHunter/@drops"/></td>
          </tr>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>