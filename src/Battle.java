import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class Battle {
    private ArrayList<MilitaryUnit>[] planetArmy; // nuestra flota
    private ArrayList<MilitaryUnit>[] enemyArmy; // flota enemiga
    private ArrayList[][] armies; //ArrayList de dos filas y siete columnas

    private StringBuilder battleDevelopment = new StringBuilder(); // Guardamos desarrollo de la batalla paso a paso
    private String battleSummary = "";

    private int[][] initialCostFleet;
    private int initialNumberUnitsPlanet;
    private int initialNumberUnitsEnemy;
    private int[] wasteMetalDeuterium;
    private int[] enemyDrops;
    private int[] planetDrops;
    private int[][] resourcesLooses;
    private int[][] initialArmies;
    private int[] actualNumberUnitsPlanet;
    private int[] actualNumberUnitsEnemy;

    // --- ATRIBUTOS PARA XML Y RESUMEN ---
    private int[] planetUnits;
    private int[] enemyUnits;
    private int planetMetalCost, planetDeutCost, enemyMetalCost, enemyDeutCost;
    private int planetMetalLoss, planetDeutLoss, enemyMetalLoss, enemyDeutLoss;
    private int planetWasteMetal, planetWasteDeut;
    private boolean planetWins;
    private int battleNumber;

    // --- GETTERS Y SETTERS NECESARIOS ---
    public int[] getPlanetUnits() { return planetUnits; }
    public int[] getPlanetDrops() { return planetDrops; }
    public int[] getEnemyUnits() { return enemyUnits; }
    public int[] getEnemyDrops() { return enemyDrops; }
    public int getPlanetMetalCost() { return planetMetalCost; }
    public int getPlanetDeutCost() { return planetDeutCost; }
    public int getEnemyMetalCost() { return enemyMetalCost; }
    public int getEnemyDeutCost() { return enemyDeutCost; }
    public int getPlanetMetalLoss() { return planetMetalLoss; }
    public int getPlanetDeutLoss() { return planetDeutLoss; }
    public int getEnemyMetalLoss() { return enemyMetalLoss; }
    public int getEnemyDeutLoss() { return enemyDeutLoss; }
    public int getPlanetWasteMetal() { return planetWasteMetal; }
    public int getPlanetWasteDeut() { return planetWasteDeut; }
    public boolean isPlanetWins() { return planetWins; }
    public int getBattleNumber() { return battleNumber; }

    public ArrayList<MilitaryUnit>[] getPlanetArmy() {
        return planetArmy;
    }

    public void setPlanetArmy(ArrayList<MilitaryUnit>[] planetArmy) {
        this.planetArmy = planetArmy;
    }

    public ArrayList<MilitaryUnit>[] getEnemyArmy() {
        return enemyArmy;
    }

    public void setEnemyArmy(ArrayList<MilitaryUnit>[] enemyArmy) {
        this.enemyArmy = enemyArmy;
    }

    public void setInitialArmies(int[][] initialArmies) {
        this.initialArmies = initialArmies;
    }

    // Añade un evento al desarrollo de la batalla
    public void logEvent(String event) {
        battleDevelopment.append(event).append("\n");
    }

    // Devuelve el desarrollo paso a paso
    public String getBattleDevelopment() {
        return battleDevelopment.toString();
    }

    // Devuelve el resumen de la batalla
    public String getBattleReport(int battleNumber) {
        return battleSummary;
    }

    // Construye el resumen de la batalla y guarda los datos para el XML
    public void buildBattleSummary(
        int battleNumber,
        int[] planetUnits, int[] planetDrops, int[] enemyUnits, int[] enemyDrops,
        int planetMetalCost, int planetDeutCost, int enemyMetalCost, int enemyDeutCost,
        int planetMetalLoss, int planetDeutLoss, int enemyMetalLoss, int enemyDeutLoss,
        int planetWasteMetal, int planetWasteDeut, boolean planetWins
    ) {
        // Guardar los datos para el XML
        this.battleNumber = battleNumber;
        this.planetUnits = planetUnits;
        this.planetDrops = planetDrops;
        this.enemyUnits = enemyUnits;
        this.enemyDrops = enemyDrops;
        this.planetMetalCost = planetMetalCost;
        this.planetDeutCost = planetDeutCost;
        this.enemyMetalCost = enemyMetalCost;
        this.enemyDeutCost = enemyDeutCost;
        this.planetMetalLoss = planetMetalLoss;
        this.planetDeutLoss = planetDeutLoss;
        this.enemyMetalLoss = enemyMetalLoss;
        this.enemyDeutLoss = enemyDeutLoss;
        this.planetWasteMetal = planetWasteMetal;
        this.planetWasteDeut = planetWasteDeut;
        this.planetWins = planetWins;

        StringJoiner sj = new StringJoiner("\n");
        sj.add("BATTLE NUMBER: " + battleNumber);
        sj.add("BATTLE STATISTICS");
        sj.add("Army planet Units Drops Initial Army Enemy Units Drops");
        sj.add(String.format("Ligth Hunter %d %d Ligth Hunter %d %d", planetUnits[0], planetDrops[0], enemyUnits[0], enemyDrops[0]));
        sj.add(String.format("Heavy Hunter %d %d Heavy Hunter %d %d", planetUnits[1], planetDrops[1], enemyUnits[1], enemyDrops[1]));
        sj.add(String.format("Battle Ship %d %d Battle Ship %d %d", planetUnits[2], planetDrops[2], enemyUnits[2], enemyDrops[2]));
        sj.add(String.format("Armored Ship %d %d Armored Ship %d %d", planetUnits[3], planetDrops[3], enemyUnits[3], enemyDrops[3]));
        sj.add(String.format("Missile Launcher %d %d", planetUnits[4], planetDrops[4]));
        sj.add(String.format("Ion Cannon %d %d", planetUnits[5], planetDrops[5]));
        sj.add(String.format("Plasma Cannon %d %d", planetUnits[6], planetDrops[6]));
        sj.add("");
        sj.add(String.format("Cost Army planet Cost Army Enemy\nMetal: %d Metal: %d\nDeuterium: %d Deuterium: %d",
                planetMetalCost, enemyMetalCost, planetDeutCost, enemyDeutCost));
        sj.add("");
        sj.add(String.format("Losses Army planet Losses Army Enemy\nMetal: %d Metal: %d\nDeuterium: %d Deuterium: %d\nWeighted: %d Weighted: %d",
                planetMetalLoss, enemyMetalLoss, planetDeutLoss, enemyDeutLoss, planetMetalLoss + planetDeutLoss, enemyMetalLoss + enemyDeutLoss));
        sj.add("");
        sj.add("Waste Generated:");
        sj.add(String.format("Metal %d\nDeuterium %d", planetWasteMetal, planetWasteDeut));
        sj.add(planetWins ? "Battle Winned by Planet, We Collect Rubble" : "Battle Winned by Enemy");
        sj.add("##########################################################################");
        battleSummary = sj.toString();
    }

    // Exporta el XML completo con todos los datos
    public void exportBattleToXML(int battleNumber, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<battleReport>\n");
            writer.write("  <battleNumber>" + battleNumber + "</battleNumber>\n");
            writer.write("  <statistics>\n");
            writer.write("    <planetUnits>\n");
            writer.write("      <lightHunter units=\"" + planetUnits[0] + "\" drops=\"" + planetDrops[0] + "\" />\n");
            writer.write("      <heavyHunter units=\"" + planetUnits[1] + "\" drops=\"" + planetDrops[1] + "\" />\n");
            writer.write("      <battleShip units=\"" + planetUnits[2] + "\" drops=\"" + planetDrops[2] + "\" />\n");
            writer.write("      <armoredShip units=\"" + planetUnits[3] + "\" drops=\"" + planetDrops[3] + "\" />\n");
            writer.write("      <missileLauncher units=\"" + planetUnits[4] + "\" drops=\"" + planetDrops[4] + "\" />\n");
            writer.write("      <ionCannon units=\"" + planetUnits[5] + "\" drops=\"" + planetDrops[5] + "\" />\n");
            writer.write("      <plasmaCannon units=\"" + planetUnits[6] + "\" drops=\"" + planetDrops[6] + "\" />\n");
            writer.write("    </planetUnits>\n");
            writer.write("    <enemyUnits>\n");
            writer.write("      <lightHunter units=\"" + enemyUnits[0] + "\" drops=\"" + enemyDrops[0] + "\" />\n");
            writer.write("      <heavyHunter units=\"" + enemyUnits[1] + "\" drops=\"" + enemyDrops[1] + "\" />\n");
            writer.write("      <battleShip units=\"" + enemyUnits[2] + "\" drops=\"" + enemyDrops[2] + "\" />\n");
            writer.write("      <armoredShip units=\"" + enemyUnits[3] + "\" drops=\"" + enemyDrops[3] + "\" />\n");
            writer.write("    </enemyUnits>\n");
            writer.write("    <costs>\n");
            writer.write("      <planet metal=\"" + planetMetalCost + "\" deuterium=\"" + planetDeutCost + "\" />\n");
            writer.write("      <enemy metal=\"" + enemyMetalCost + "\" deuterium=\"" + enemyDeutCost + "\" />\n");
            writer.write("    </costs>\n");
            writer.write("    <losses>\n");
            writer.write("      <planet metal=\"" + planetMetalLoss + "\" deuterium=\"" + planetDeutLoss + "\" weighted=\"" + (planetMetalLoss + planetDeutLoss) + "\" />\n");
            writer.write("      <enemy metal=\"" + enemyMetalLoss + "\" deuterium=\"" + enemyDeutLoss + "\" weighted=\"" + (enemyMetalLoss + enemyDeutLoss) + "\" />\n");
            writer.write("    </losses>\n");
            writer.write("    <waste>\n");
            writer.write("      <metal>" + planetWasteMetal + "</metal>\n");
            writer.write("      <deuterium>" + planetWasteDeut + "</deuterium>\n");
            writer.write("    </waste>\n");
            writer.write("    <winner>" + (planetWins ? "Planet" : "Enemy") + "</winner>\n");
            writer.write("    <rubbleCollected>" + planetWins + "</rubbleCollected>\n");
            writer.write("  </statistics>\n");
            writer.write("</battleReport>\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //transformar xml a html usando XSLT
    public void transformXMLToHTML(String xmlPath, String xslPath, String htmlPath) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new java.io.File(xslPath));
            Transformer transformer = factory.newTransformer(xslt);

            Source xml = new StreamSource(new java.io.File(xmlPath));
            transformer.transform(xml, new StreamResult(new java.io.File(htmlPath)));
		} catch (Exception e) {
					e.printStackTrace();
			}
		}
	}