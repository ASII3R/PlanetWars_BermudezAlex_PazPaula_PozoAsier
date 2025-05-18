import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Juego implements Variables {
    private JFrame ventana;
    private JPanel panelBattleReports;
    private JTextArea textAreaBattleReports;
    private JPanel panelStart, panelMainMenu, panelBuild, panelBuildTroops, panelBuildDefenses, panelUpgradeTechnology;
    private ArrayList<MilitaryUnit>[] planetArmy;
    private Planet planet;
    private Battle battle;

    private boolean flg_main_menu = true;
    private boolean flg_build_menu = false;
    private boolean flg_build_troops_menu = false;
    private boolean flg_build_defenses_menu = false;
    private boolean flg_upgrade_technology_menu = false;

    // Añade este contador para simular el número de batalla (puedes mejorarlo según tu lógica)
    private int battleCounter = 1;

    // Añade un indicador para evitar múltiples peleas consecutivas
    private boolean isBattleInProgress = false;

    public Juego() throws ResourceException {
        // Initialize planet army
        planetArmy = new ArrayList[7];
        for (int i = 0; i < planetArmy.length; i++) {
            planetArmy[i] = new ArrayList<>();
        }
        planetArmy[0].add(new LightHunter());
        planetArmy[1].add(new HeavyHunter());
        planetArmy[2].add(new BattleShip());
        planetArmy[3].add(new ArmoredShip());
        planetArmy[4].add(new MissileLauncher(ARMOR_MISSILELAUNCHER, BASE_DAMAGE_MISSILELAUNCHER));
        planetArmy[5].add(new IonCannon(ARMOR_IONCANNON, BASE_DAMAGE_PLASMACANNON));
        planetArmy[6].add(new PlasmaCannon(ARMOR_PLASMACANNON, BASE_DAMAGE_PLASMACANNON));

        // Initialize enemy army
        ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[7];
        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }
        enemyArmy[0].add(new LightHunter());
        enemyArmy[0].add(new LightHunter());
        enemyArmy[1].add(new HeavyHunter());
        enemyArmy[2].add(new BattleShip());
        enemyArmy[3].add(new ArmoredShip());
        enemyArmy[4].add(new MissileLauncher(ARMOR_MISSILELAUNCHER, BASE_DAMAGE_MISSILELAUNCHER));
        enemyArmy[5].add(new IonCannon(ARMOR_IONCANNON, BASE_DAMAGE_IONCANNON));
        enemyArmy[6].add(new PlasmaCannon(ARMOR_PLASMACANNON, BASE_DAMAGE_PLASMACANNON));

        // Initialize planet and battle
        this.planet = new Planet(0, 0, 53500, 26800, UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST, UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST, planetArmy, 1);
        this.battle = new Battle();
        this.battle.setPlanetArmy(planetArmy);
        this.battle.setEnemyArmy(enemyArmy);

        // Timer tasks
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                planet.setMetal(planet.getMetal() + PLANET_METAL_GENERATED);
                planet.setDeuterium(planet.getDeuterium() + PLANET_DEUTERIUM_GENERATED);
                SwingUtilities.invokeLater(() -> 
                    JOptionPane.showMessageDialog(ventana, "Recolected Resources...\n", "Resources", JOptionPane.INFORMATION_MESSAGE)
                );
            }
        }, 60000, 60000);

        timer.schedule(new TimerTask() {
            public void run() {
                ArrayList<MilitaryUnit>[] enemy = battle.getEnemyArmy();
                boolean anyEnemy = false;

                if (enemy != null) {
                    for (ArrayList<MilitaryUnit> group : enemy) {
                        if (!group.isEmpty()) {
                            anyEnemy = true;
                            break;
                        }
                    }
                }

                if (anyEnemy && !isBattleInProgress) {
                    isBattleInProgress = true; // Marcar que la batalla está en progreso
                    SwingUtilities.invokeLater(() -> resolveBattle());
                }
            }
        }, 0, 30000);

        // Initialize GUI
        ventana = new JFrame("Space Wars Game");
        ventana.setSize(600, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new CardLayout());

        initializeScreens();
        ventana.setVisible(true);
    }
    

    private void initializeScreens() {
       // Start panel
        panelStart = new JPanel();
        panelStart.setLayout(new BoxLayout(panelStart, BoxLayout.Y_AXIS));
        panelStart.setBackground(new Color(255, 140, 0));
        panelStart.setBorder(BorderFactory.createEmptyBorder(80, 50, 80, 50)); // Espacio alrededor

        JLabel title = new JLabel("SPACE WARS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton buttonPlay = new JButton("Play");
        buttonPlay.setPreferredSize(new Dimension(120, 40));
        buttonPlay.setMaximumSize(new Dimension(150, 50));
        buttonPlay.setBackground(new Color(255, 69, 0));
        buttonPlay.setForeground(Color.WHITE);
        buttonPlay.setFocusPainted(false);
        buttonPlay.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        buttonPlay.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPlay.addActionListener(e -> switchPanel(panelMainMenu));

        // Agregar componentes con espacio entre ellos
        panelStart.add(title);
        panelStart.add(Box.createRigidArea(new Dimension(0, 50))); // Espacio entre título y botón
        panelStart.add(buttonPlay);

        ventana.add(panelStart);

        // Main Menu panel
        panelMainMenu = new JPanel();
        panelMainMenu.setLayout(new BoxLayout(panelMainMenu, BoxLayout.Y_AXIS));
        panelMainMenu.setBackground(new Color(70, 130, 180));
        panelMainMenu.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100)); // Márgenes grandes

        // Título
        JLabel titleMainMenu = new JLabel("Main Menu", SwingConstants.CENTER);
        titleMainMenu.setFont(new Font("Arial", Font.BOLD, 50));
        titleMainMenu.setForeground(Color.WHITE);
        titleMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelMainMenu.add(titleMainMenu);
        panelMainMenu.add(Box.createRigidArea(new Dimension(0, 30))); // Espacio debajo del título

        // Estilo común
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(500, 90);
        Color buttonColor = new Color(240, 248, 255); // Azul muy suave
        Color textColor = new Color(25, 25, 112); // Azul oscuro
        Border buttonBorder = BorderFactory.createLineBorder(Color.WHITE, 2);

        // Botón 1
        JButton buttonStats = new JButton("View Planet Stats");
        buttonStats.setMaximumSize(buttonSize);
        buttonStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonStats.setBackground(buttonColor);
        buttonStats.setForeground(textColor);
        buttonStats.setFont(buttonFont);
        buttonStats.setFocusPainted(false);
        buttonStats.setBorder(buttonBorder);
        panelMainMenu.add(buttonStats);
        panelMainMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón 2
        JButton buttonBuild = new JButton("Build");
        buttonBuild.setMaximumSize(buttonSize);
        buttonBuild.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBuild.setBackground(buttonColor);
        buttonBuild.setForeground(textColor);
        buttonBuild.setFont(buttonFont);
        buttonBuild.setFocusPainted(false);
        buttonBuild.setBorder(buttonBorder);
        panelMainMenu.add(buttonBuild);
        panelMainMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón 3
        JButton buttonUpgrade = new JButton("Upgrade Technology");
        buttonUpgrade.setMaximumSize(buttonSize);
        buttonUpgrade.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonUpgrade.setBackground(buttonColor);
        buttonUpgrade.setForeground(textColor);
        buttonUpgrade.setFont(buttonFont);
        buttonUpgrade.setFocusPainted(false);
        buttonUpgrade.setBorder(buttonBorder);
        panelMainMenu.add(buttonUpgrade);
        panelMainMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón 4
        JButton buttonBattleReports = new JButton("View Battle Reports");
        buttonBattleReports.setMaximumSize(buttonSize);
        buttonBattleReports.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBattleReports.setBackground(buttonColor);
        buttonBattleReports.setForeground(textColor);
        buttonBattleReports.setFont(buttonFont);
        buttonBattleReports.setFocusPainted(false);
        buttonBattleReports.setBorder(buttonBorder);
        panelMainMenu.add(buttonBattleReports);
        panelMainMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Botón 5
        JButton buttonExit = new JButton("Exit");
        buttonExit.setMaximumSize(buttonSize);
        buttonExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonExit.setBackground(buttonColor);
        buttonExit.setForeground(textColor);
        buttonExit.setFont(buttonFont);
        buttonExit.setFocusPainted(false);
        buttonExit.setBorder(buttonBorder);
        panelMainMenu.add(buttonExit);
        panelMainMenu.add(Box.createRigidArea(new Dimension(0, 15)));

        // Agregar panel a ventana
        ventana.add(panelMainMenu);
            
        buttonStats.addActionListener(e -> {
        JOptionPane.showMessageDialog(ventana,
            "Planet Stats:\n\n" +
            "Metal: " + planet.getMetal() + "\n" +
            "Deuterium: " + planet.getDeuterium() + "\n\n" +
            "Attack Tech Level: " + planet.getTechnologyAttack() + "\n" +
            "Defense Tech Level: " + planet.getTechnologyDefense() + "\n\n" +
            "Upgrade Cost (Attack): " + planet.getUpgradeAttackTechnologyDeuteriumCost() + " Deuterium\n" +
            "Upgrade Cost (Defense): " + planet.getUpgradeDefenseTechnologyDeuteriumCost() + " Deuterium\n\n" +
            "Army Composition:\n" +
            "- Light Hunters: " + planetArmy[0].size() + "\n" +
            "- Heavy Hunters: " + planetArmy[1].size() + "\n" +
            "- Battle Ships: " + planetArmy[2].size() + "\n" +
            "- Armored Ships: " + planetArmy[3].size() + "\n" +
            "- Missile Launchers: " + planetArmy[4].size() + "\n" +
            "- Ion Cannons: " + planetArmy[5].size() + "\n" +
            "- Plasma Cannons: " + planetArmy[6].size(),
            "Planet Statistics", JOptionPane.INFORMATION_MESSAGE
        );
        });

        buttonBuild.setBackground(buttonColor);
        buttonUpgrade.setBackground(buttonColor);
        buttonBattleReports.setBackground(buttonColor);
        buttonExit.setBackground(buttonColor);

        // Agregar acciones a los botones
        buttonStats.addActionListener(e -> planet.printStats());
        buttonBuild.addActionListener(e -> switchPanel(panelBuild));
        buttonUpgrade.addActionListener(e -> switchPanel(panelUpgradeTechnology));
        buttonBattleReports.addActionListener(e -> JOptionPane.showMessageDialog(ventana, "Displaying battle reports..."));
        buttonExit.addActionListener(e -> System.exit(0));

        // Build Menu panel
        panelBuild = new JPanel(new GridLayout(3, 1));
        panelBuild.setBackground(new Color(100, 149, 237));
        panelBuild.add(new JLabel("Build Menu", SwingConstants.CENTER));

        JButton buttonTroops = new JButton("Build Troops");
        buttonTroops.addActionListener(e -> switchPanel(panelBuildTroops));
        JButton buttonDefenses = new JButton("Build Defenses");
        buttonDefenses.addActionListener(e -> switchPanel(panelBuildDefenses));
        JButton buttonBack = new JButton("Go Back");
        buttonBack.addActionListener(e -> switchPanel(panelMainMenu));

        panelBuild.add(buttonTroops);
        panelBuild.add(buttonDefenses);
        panelBuild.add(buttonBack);

        // Build Troops panel
        panelBuildTroops = new JPanel(new GridLayout(5, 1));
        panelBuildTroops.setBackground(new Color(176, 196, 222));
        panelBuildTroops.add(new JLabel("Build Troops", SwingConstants.CENTER));

        // Crear botones como JButton con imágenes
        JButton buttonLH = new JButton("Build Light Hunter");
        buttonLH.setIcon(loadImageIcon("src/images/light_hunter.png", 50, 50)); // Imagen para Light Hunter

        JButton buttonHH = new JButton("Build Heavy Hunter");
        buttonHH.setIcon(loadImageIcon("src/images/heavy_hunter.png", 50, 50)); // Imagen para Heavy Hunter

        JButton buttonBS = new JButton("Build Battle Ship");
        buttonBS.setIcon(loadImageIcon("src/images/battle_ship.png", 50, 50)); // Imagen para Battle Ship

        JButton buttonAS = new JButton("Build Armored Ship");
        buttonAS.setIcon(loadImageIcon("src/images/armored_ship.png", 50, 50)); // Imagen para Armored Ship

        JButton buttonBackTroops = new JButton("Go Back");

        // Agregar ActionListeners (ya existentes en tu código)
        buttonLH.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Light Hunters:");
            LightHunter temp = new LightHunter();

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int posiblesMetal = (metalCost > 0) ? planet.getMetal() / metalCost : 0;
            int posiblesDeut = (deutCost > 0) ? planet.getDeuterium() / deutCost : 0;

            int posibles = Math.min(posiblesMetal, posiblesDeut);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Light Hunters.");
            } else {
                int aConstruir = Math.min(cantidad, posibles);

                planet.newLightHunter(aConstruir);
                //DatabaseManager.insertPlanetBattleArmy(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0);

                //enemy army:
                //DatabaseManager.insertEnemyArmy(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0);

                // Update planet stats after building
                DatabaseManager.updatePlanetStats(
                    planet.getIdPlanet(),
                    "PlanetName",
                    planet.getMetal(),
                    planet.getDeuterium(),
                    planet.getTechnologyDefense(),
                    planet.getTechnologyAttack(),
                    0,
                    planetArmy[4].size(),
                    planetArmy[5].size(),
                    planetArmy[6].size(),
                    planetArmy[0].size(),
                    planetArmy[1].size(),
                    planetArmy[2].size(),
                    planetArmy[3].size()
                );
                // Update battle army table
                DatabaseManager.updatePlanetBattleArmy(
                    planet.getIdPlanet(),
                    battleCounter,
                    aConstruir, 0, 0,0
                );

                if (aConstruir < cantidad) {
                    JOptionPane.showMessageDialog(ventana, "Only " + aConstruir + " Light Hunters were built due to limited resources.");
                } else {
                    JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Light Hunters.");
                }
            }
        });

        // Heavy Hunter
        buttonHH.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Heavy Hunters:");
            HeavyHunter temp = new HeavyHunter();

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int posiblesMetal = (metalCost > 0) ? planet.getMetal() / metalCost : 0;
            int posiblesDeut = (deutCost > 0) ? planet.getDeuterium() / deutCost : 0;

            int posibles = Math.min(posiblesMetal, posiblesDeut);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Heavy Hunters.");
            } else {
                int aConstruir = Math.min(cantidad, posibles);

                planet.newHeavyHunter(aConstruir);
                //DatabaseManager.insertPlanetBattleArmy(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0);


                DatabaseManager.updatePlanetStats(
                    planet.getIdPlanet(),
                    "PlanetName",
                    planet.getMetal(),
                    planet.getDeuterium(),
                    planet.getTechnologyDefense(),
                    planet.getTechnologyAttack(),
                    0,
                    planetArmy[4].size(),
                    planetArmy[5].size(),
                    planetArmy[6].size(),
                    planetArmy[0].size(),
                    planetArmy[1].size(),
                    planetArmy[2].size(),
                    planetArmy[3].size()
                );
                DatabaseManager.updatePlanetBattleArmy(
                    planet.getIdPlanet(),
                    battleCounter,
                    0, 0, aConstruir,0
                );

                if (aConstruir < cantidad) {
                    JOptionPane.showMessageDialog(ventana, "Only " + aConstruir + " Heavy Hunters were built due to limited resources.");
                } else {
                    JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Heavy Hunters.");
                }
            }
        });

        // Battle Ship
        buttonBS.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Battle Ships:");
            BattleShip temp = new BattleShip();

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int posiblesMetal = (metalCost > 0) ? planet.getMetal() / metalCost : 0;
            int posiblesDeut = (deutCost > 0) ? planet.getDeuterium() / deutCost : 0;

            int posibles = Math.min(posiblesMetal, posiblesDeut);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Battle Ships.");
            } else {
                int aConstruir = Math.min(cantidad, posibles);

                planet.newBattleShip(aConstruir);
                //DatabaseManager.insertPlanetBattleArmy(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0);

                DatabaseManager.updatePlanetStats(
                    planet.getIdPlanet(),
                    "PlanetName",
                    planet.getMetal(),
                    planet.getDeuterium(),
                    planet.getTechnologyDefense(),
                    planet.getTechnologyAttack(),
                    0,
                    planetArmy[4].size(),
                    planetArmy[5].size(),
                    planetArmy[6].size(),
                    planetArmy[0].size(),
                    planetArmy[1].size(),
                    planetArmy[2].size(),
                    planetArmy[3].size()
                );
                DatabaseManager.updatePlanetBattleArmy(
                    planet.getIdPlanet(),
                    battleCounter,
                    0, 0, 0,aConstruir
                    );

                if (aConstruir < cantidad) {
                    JOptionPane.showMessageDialog(ventana, "Only " + aConstruir + " Battle Ships were built due to limited resources.");
                } else {
                    JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Battle Ships.");
                }
            }
        });

        // Armored Ship
        buttonAS.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Armored Ships:");
            ArmoredShip temp = new ArmoredShip();

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int posiblesMetal = (metalCost > 0) ? planet.getMetal() / metalCost : 0;
            int posiblesDeut = (deutCost > 0) ? planet.getDeuterium() / deutCost : 0;

            int posibles = Math.min(posiblesMetal, posiblesDeut);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Armored Ships.");
            } else {
                int aConstruir = Math.min(cantidad, posibles);

                planet.newArmoredShip(aConstruir);
                //DatabaseManager.insertPlanetBattleArmy(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0);

                DatabaseManager.updatePlanetStats(
                    planet.getIdPlanet(),
                    "PlanetName",
                    planet.getMetal(),
                    planet.getDeuterium(),
                    planet.getTechnologyDefense(),
                    planet.getTechnologyAttack(),
                    0,
                    planetArmy[4].size(),
                    planetArmy[5].size(),
                    planetArmy[6].size(),
                    planetArmy[0].size(),
                    planetArmy[1].size(),
                    planetArmy[2].size(),
                    planetArmy[3].size()
                );
                DatabaseManager.updatePlanetBattleArmy(
                    planet.getIdPlanet(),
                    battleCounter,
                    0, 0, 0,aConstruir
                );

                if (aConstruir < cantidad) {
                    JOptionPane.showMessageDialog(ventana, "Only " + aConstruir + " Armored Ships were built due to limited resources.");
                } else {
                    JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Armored Ships.");
                }
            }
        });

        buttonBackTroops.addActionListener(e -> switchPanel(panelBuild));

        // Agregar botones al panel
        panelBuildTroops.add(buttonLH);
        panelBuildTroops.add(buttonHH);
        panelBuildTroops.add(buttonBS);
        panelBuildTroops.add(buttonAS);
        panelBuildTroops.add(buttonBackTroops);

        // Build Defenses panel
        panelBuildDefenses = new JPanel(new GridLayout(5, 1));
        panelBuildDefenses.setBackground(new Color(205, 92, 92));
        panelBuildDefenses.add(new JLabel("Build Defenses", SwingConstants.CENTER));

        // Missile Launcher
        JButton buttonML = new JButton("Build Missile Launcher");
        buttonML.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Missile Launchers:");
            MissileLauncher temp = new MissileLauncher(ARMOR_MISSILELAUNCHER, BASE_DAMAGE_MISSILELAUNCHER);

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            if (metalCost == 0 || deutCost == 0) {
                JOptionPane.showMessageDialog(ventana, "Error: Cost values cannot be zero.");
                return;
            }

            int maxMetal = planet.getMetal() / metalCost;
            int maxDeut = planet.getDeuterium() / deutCost;

            int posibles = Math.min(maxMetal, maxDeut);

            int aConstruir = Math.min(cantidad, posibles);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Missile Launchers.");
                return;
            }

            planet.newMissileLauncher(aConstruir);
            //DatabaseManager.insertPlanetBattleDefense(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0, 0, 0);

            DatabaseManager.updatePlanetStats(
                planet.getIdPlanet(),
                "PlanetName",
                planet.getMetal(),
                planet.getDeuterium(),
                planet.getTechnologyDefense(),
                planet.getTechnologyAttack(),
                0,
                planetArmy[4].size(),
                planetArmy[5].size(),
                planetArmy[6].size(),
                planetArmy[0].size(),
                planetArmy[1].size(),
                planetArmy[2].size(),
                planetArmy[3].size()
            );
            DatabaseManager.updatePlanetBattleDefense(
                planet.getIdPlanet(),
                battleCounter,
                aConstruir, 0, 0, 0, 0, 0
            );

            JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Missile Launchers.");
        });

        // Back Button
        JButton buttonBackDefenses = new JButton("Go Back");
        buttonBackDefenses.addActionListener(e -> switchPanel(panelBuild));

        // Add buttons to panel
        panelBuildDefenses.add(buttonML);
        panelBuildDefenses.add(buttonBackDefenses);

        // Ion Cannon
        JButton buttonIC = new JButton("Build Ion Cannon");
        buttonIC.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Ion Cannons:");
            IonCannon temp = new IonCannon(ARMOR_IONCANNON, BASE_DAMAGE_IONCANNON);

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int maxMetal = planet.getMetal() / metalCost;
            int maxDeut = planet.getDeuterium() / deutCost;
            
            int posibles = Math.min(maxMetal, maxDeut);

            int aConstruir = Math.min(cantidad, posibles);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Ion Cannons.");
                return;
            }

            planet.newIonCannon(aConstruir);
            //DatabaseManager.insertPlanetBattleDefense(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0, 0, 0);

            DatabaseManager.updatePlanetStats(
                planet.getIdPlanet(),
                "PlanetName",
                planet.getMetal(),
                planet.getDeuterium(),
                planet.getTechnologyDefense(),
                planet.getTechnologyAttack(),
                0,
                planetArmy[4].size(),
                planetArmy[5].size(),
                planetArmy[6].size(),
                planetArmy[0].size(),
                planetArmy[1].size(),
                planetArmy[2].size(),
                planetArmy[3].size()
            );
            DatabaseManager.updatePlanetBattleDefense(
                planet.getIdPlanet(),
                battleCounter,
                aConstruir, 0, 0, 0, 0, 0
            );

            JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Ion Cannons.");
        });

        // Plasma Cannon
        JButton buttonPC = new JButton("Build Plasma Cannon");
        buttonPC.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Plasma Cannons:");
            PlasmaCannon temp = new PlasmaCannon(ARMOR_PLASMACANNON, BASE_DAMAGE_PLASMACANNON);

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int maxMetal = planet.getMetal() / metalCost;
            int maxDeut = planet.getDeuterium() / deutCost;
            
            int posibles = Math.min(maxMetal, maxDeut);

            int aConstruir = Math.min(cantidad, posibles);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Plasma Cannons.");
                return;
            }

            planet.newPlasmaCannon(aConstruir);
            //DatabaseManager.insertPlanetBattleDefense(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0, 0, 0);

            DatabaseManager.updatePlanetStats(
                planet.getIdPlanet(),
                "PlanetName",
                planet.getMetal(),
                planet.getDeuterium(),
                planet.getTechnologyDefense(),
                planet.getTechnologyAttack(),
                0,
                planetArmy[4].size(),
                planetArmy[5].size(),
                planetArmy[6].size(),
                planetArmy[0].size(),
                planetArmy[1].size(),
                planetArmy[2].size(),
                planetArmy[3].size()
            );
            DatabaseManager.updatePlanetBattleDefense(
                planet.getIdPlanet(),
                battleCounter,
                aConstruir, 0, 0, 0, 0, 0
            );

            JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Plasma Cannons.");
        });

        // Armored Ship (opcional, si quieres permitir construir desde defensas)
        JButton buttonArmoredShip = new JButton("Build Armored Ship");
        buttonArmoredShip.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Armored Ships:");
            ArmoredShip temp = new ArmoredShip(ARMOR_ARMOREDSHIP, BASE_DAMAGE_ARMOREDSHIP);

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int maxMetal = planet.getMetal() / metalCost;
            int maxDeut = planet.getDeuterium() / deutCost;

            int posibles = Math.min(maxMetal, maxDeut);

            int aConstruir = Math.min(cantidad, posibles);

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Armored Ships.");
                return;
            }

            planet.newArmoredShip(aConstruir);
            //DatabaseManager.insertPlanetBattleArmy(planet.getIdPlanet(), battleCounter, 0, 0, 0, 0);

            DatabaseManager.updatePlanetStats(
                planet.getIdPlanet(),
                "PlanetName",
                planet.getMetal(),
                planet.getDeuterium(),
                planet.getTechnologyDefense(),
                planet.getTechnologyAttack(),
                0,
                planetArmy[4].size(),
                planetArmy[5].size(),
                planetArmy[6].size(),
                planetArmy[0].size(),
                planetArmy[1].size(),
                planetArmy[2].size(),
                planetArmy[3].size()
            );
            DatabaseManager.updatePlanetBattleArmy(
                planet.getIdPlanet(),
                battleCounter,
                aConstruir, 0, 0, 0
            );

            JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Armored Ships.");
        });

        //  Panel para mejorar tecnología
        panelUpgradeTechnology = new JPanel(new GridLayout(4, 1));
        panelUpgradeTechnology.setBackground(new Color(59, 131, 189)); // azul
        panelUpgradeTechnology.add(new JLabel("Upgrade Technology", SwingConstants.CENTER));
        panelUpgradeTechnology.setFont(new Font("Arial", Font.BOLD, 60));
        //  Botón para mejorar defensa
        JButton buttonDefense = new JButton("Upgrade Defense Technology");
        buttonDefense.setPreferredSize(new Dimension(80, 25));
        buttonDefense.setBackground(new Color(240, 248, 255)); // 
        buttonDefense.setForeground(Color.BLACK);
        buttonDefense.setFocusPainted(false);
        buttonDefense.setFont(new Font("Arial", Font.BOLD, 18));

        buttonDefense.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        buttonDefense.addActionListener(e -> {
            int previousLevel = planet.getTechnologyDefense();
            int cost = planet.getUpgradeDefenseTechnologyDeuteriumCost();

            if (planet.getDeuterium() >= cost) {
                planet.upgradeTechnologyDefense();
                int newLevel = planet.getTechnologyDefense();
                planet.setDeuterium(planet.getDeuterium() - cost);

                // Update planet stats after upgrading
                DatabaseManager.updatePlanetStats(
                    planet.getIdPlanet(),
                    "PlanetName",
                    planet.getMetal(),
                    planet.getDeuterium(),
                    planet.getTechnologyDefense(),
                    planet.getTechnologyAttack(),
                    0,
                    planetArmy[4].size(),
                    planetArmy[5].size(),
                    planetArmy[6].size(),
                    planetArmy[0].size(),
                    planetArmy[1].size(),
                    planetArmy[2].size(),
                    planetArmy[3].size()
                );

                JOptionPane.showMessageDialog(ventana, "Defense Technology upgraded!\nPrevious Level: " + previousLevel +
                        "\nNew Level: " + newLevel + "\nCost: " + cost + " Deuterium\nRemaining Deuterium: " + planet.getDeuterium());
            } else {
                JOptionPane.showMessageDialog(ventana, "Not enough deuterium!\nRequired: " + cost + " | Available: " + planet.getDeuterium());
            }
        });

        // Botón para mejorar ataque
        JButton buttonAttack = new JButton("Upgrade Attack Technology");
        buttonAttack.setPreferredSize(new Dimension(80, 25));
        buttonAttack.setBackground(new Color(240, 248, 255)); // Rojo intenso
        buttonAttack.setForeground(Color.BLACK);
        buttonAttack.setFocusPainted(false);
        buttonAttack.setFont(new Font("Arial", Font.BOLD, 18));
        buttonAttack.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        buttonAttack.addActionListener(e -> {
            int previousLevel = planet.getTechnologyAttack();
            int cost = planet.getUpgradeAttackTechnologyDeuteriumCost();

            if (planet.getDeuterium() >= cost) {
                planet.upgradeTechnologyAttack();
                int newLevel = planet.getTechnologyAttack();
                planet.setDeuterium(planet.getDeuterium() - cost);

                DatabaseManager.updatePlanetStats(
                    planet.getIdPlanet(),
                    "PlanetName",
                    planet.getMetal(),
                    planet.getDeuterium(),
                    planet.getTechnologyDefense(),
                    planet.getTechnologyAttack(),
                    0,
                    planetArmy[4].size(),
                    planetArmy[5].size(),
                    planetArmy[6].size(),
                    planetArmy[0].size(),
                    planetArmy[1].size(),
                    planetArmy[2].size(),
                    planetArmy[3].size()
                );

                JOptionPane.showMessageDialog(ventana, "Attack Technology upgraded!\nPrevious Level: " + previousLevel +
                        "\nNew Level: " + newLevel + "\nCost: " + cost + " Deuterium\nRemaining Deuterium: " + planet.getDeuterium());
            } else {
                JOptionPane.showMessageDialog(ventana, "Not enough deuterium!\nRequired: " + cost + " | Available: " + planet.getDeuterium());
            }
        });

        // Botón para volver
        JButton buttonBackUpgrade = new JButton("Go Back");
        buttonBackUpgrade.setPreferredSize(new Dimension(80, 25));
        buttonBackUpgrade.setBackground(new Color(240, 248, 255)); // Azul brillante
        buttonBackUpgrade.setForeground(Color.BLACK);
        buttonBackUpgrade.setFocusPainted(false);
        buttonBackUpgrade.setFont(new Font("Arial", Font.BOLD, 18));
        buttonBackUpgrade.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        buttonBackUpgrade.addActionListener(e -> switchPanel(panelMainMenu));

        // Añadir botones al panel de mejora de tecnología
        panelUpgradeTechnology.add(buttonDefense);
        panelUpgradeTechnology.add(buttonAttack);
        panelUpgradeTechnology.add(buttonBackUpgrade);

        panelBattleReports = new JPanel(new BorderLayout());
textAreaBattleReports = new JTextArea();
textAreaBattleReports.setEditable(false);
textAreaBattleReports.setFont(new Font("Monospaced", Font.PLAIN, 14));
JScrollPane scrollPane = new JScrollPane(textAreaBattleReports);

JButton buttonBackBattleReports = new JButton("Go Back");
buttonBackBattleReports.addActionListener(e -> switchPanel(panelMainMenu));

panelBattleReports.add(scrollPane, BorderLayout.CENTER);
panelBattleReports.add(buttonBackBattleReports, BorderLayout.SOUTH);

// Modifica el actionListener de buttonBattleReports:
buttonBattleReports.addActionListener(e -> {
    StringBuilder sb = new StringBuilder();
    sb.append("[Battle Report]\n\n");
    sb.append("Planet Army:\n");
    for (int i = 0; i < planetArmy.length; i++) {
        if (!planetArmy[i].isEmpty()) {
            sb.append("- ").append(planetArmy[i].get(0).getClass().getSimpleName())
              .append(" x").append(planetArmy[i].size()).append("\n");
        }
    }
    sb.append("\n");

    // Mostrar solo si hay enemigos atacando
    ArrayList<MilitaryUnit>[] enemy = battle.getEnemyArmy();
    boolean anyEnemy = false;
    StringBuilder enemySb = new StringBuilder();
    if (enemy != null) {
        for (int i = 0; i < enemy.length; i++) {
            if (!enemy[i].isEmpty()) {
                anyEnemy = true;
                enemySb.append("- ").append(enemy[i].get(0).getClass().getSimpleName())
                       .append(" x").append(enemy[i].size()).append("\n");
            }
        }
    }
    if (anyEnemy) {
        sb.append("¡Te están atacando! Enemigos detectados:\n");
        sb.append(enemySb);
    } else {
        sb.append("No hay enemigos atacando en este momento.\n");
    }

    textAreaBattleReports.setText(sb.toString());
    switchPanel(panelBattleReports);
});

        // Panel de créditos
        JPanel panelCredits = new JPanel();
        panelCredits.setLayout(new BoxLayout(panelCredits, BoxLayout.Y_AXIS));
        panelCredits.setBackground(new Color(176, 196, 222));
        panelCredits.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100)); // Márgenes grandes

        JLabel titleCredits = new JLabel("Credits", SwingConstants.CENTER);
        titleCredits.setFont(new Font("Arial", Font.BOLD, 50));
        titleCredits.setForeground(Color.WHITE);
        titleCredits.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCredits.add(titleCredits);
        panelCredits.add(Box.createRigidArea(new Dimension(0, 30))); // Espacio debajo del título


        // Botón Back
        JButton buttonBackCredits = new JButton("Back");
        buttonBackCredits.setMaximumSize(new Dimension(200, 50));
        buttonBackCredits.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBackCredits.setBackground(new Color(240, 248, 255)); // Azul muy suave
        buttonBackCredits.setForeground(new Color(25, 25, 112)); // Azul oscuro
        buttonBackCredits.setFont(new Font("Arial", Font.BOLD, 16));
         buttonBackCredits.setFocusPainted(false);
        buttonBackCredits.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        buttonBackCredits.addActionListener(e -> switchPanel(panelMainMenu));
        panelCredits.add(Box.createRigidArea(new Dimension(0, 30))); // Espacio antes del botón
        panelCredits.add(buttonBackCredits);

        // Agregar panel de créditos a la ventana (asegúrate de que esto esté en el lugar correcto de tu código)
        ventana.add(panelCredits);
    }
    private int getUnitAmount(String message) {
        String input = JOptionPane.showInputDialog(message);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Invalid input, defaulting to 1 unit.");
            return 1;
        }
    }

    public class TextAreaOutputStream extends OutputStream {
        private final JTextArea textArea;

        public TextAreaOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) throws IOException {
            textArea.append(String.valueOf((char) b));
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    private void switchPanel(JPanel newPanel) {
        ventana.getContentPane().removeAll();
        ventana.add(newPanel);
        ventana.revalidate();
        ventana.repaint();
    }
    
    private void resolveBattle() {
        // Simular el resultado de la batalla
        boolean planetWins = simulateBattle();

        int metalGained = 0; // Metal ganado
        int deuteriumGained = 0; // Deuterium ganado

        if (planetWins) {
            // Calcular el costo total de las tropas enemigas
            ArrayList<MilitaryUnit>[] enemyArmy = battle.getEnemyArmy();
            for (ArrayList<MilitaryUnit> group : enemyArmy) {
                for (MilitaryUnit unit : group) {
                    metalGained += unit.getMetalCost();
                    deuteriumGained += unit.getDeteriumCost();
                }
            }

            // Dividir los materiales ganados a la mitad
            metalGained /= 2;
            deuteriumGained /= 2;

            // Agregar los materiales obtenidos al inventario del planeta
            planet.setMetal(planet.getMetal() + metalGained);
            planet.setDeuterium(planet.getDeuterium() + deuteriumGained);
        }

        // Generar reporte de batalla
        StringBuilder planetArmyReport = new StringBuilder();
        planetArmyReport.append("<units>\n");
        for (int i = 0; i < planetArmy.length; i++) {
            if (!planetArmy[i].isEmpty()) {
                planetArmyReport.append("<unit type=\"")
                    .append(planetArmy[i].get(0).getClass().getSimpleName())
                    .append("\" count=\"")
                    .append(planetArmy[i].size())
                    .append("\" />\n");
            }
        }
        planetArmyReport.append("</units>\n");

        StringBuilder enemyArmyReport = new StringBuilder();
        enemyArmyReport.append("<units>\n");
        ArrayList<MilitaryUnit>[] enemy = battle.getEnemyArmy();
        if (enemy != null) {
            for (int i = 0; i < enemy.length; i++) {
                if (!enemy[i].isEmpty()) {
                    enemyArmyReport.append("<unit type=\"")
                        .append(enemy[i].get(0).getClass().getSimpleName())
                        .append("\" count=\"")
                        .append(enemy[i].size())
                        .append("\" />\n");
                }
            }
        }
        enemyArmyReport.append("</units>\n");

        // Generar archivo XML
        generateBattleReportXML(battleCounter, planetArmyReport.toString(), enemyArmyReport.toString());

        // Transformar XML a HTML
        String xmlFileName = "battle" + battleCounter + ".xml";
        String xslFileName = "battleReport.xsl";
        String htmlFileName = "battle" + battleCounter + ".html";
        transformXMLToHTML(xmlFileName, xslFileName, htmlFileName);

        // Incrementar el contador de batallas
        battleCounter++;

        // Crear una nueva ventana para mostrar el resultado
        JFrame resultWindow = new JFrame("Battle Result");
        resultWindow.setSize(400, 250);
        resultWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultWindow.setLayout(new BorderLayout());

        String resultMessage = planetWins
            ? "¡Has ganado!\nHas obtenido:\n" + metalGained + " de metal\n" + deuteriumGained + " de deuterium."
            : "Has perdido.\nNo has obtenido materiales.";

        JLabel resultLabel = new JLabel("<html><div style='text-align: center;'>" + resultMessage + "</div></html>", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
        resultLabel.setForeground(planetWins ? Color.GREEN : Color.RED);

        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> {
            resultWindow.dispose();
            isBattleInProgress = false; // Restablecer el indicador después de cerrar la ventana
        });

        resultWindow.add(resultLabel, BorderLayout.CENTER);
        resultWindow.add(closeButton, BorderLayout.SOUTH);

        resultWindow.setLocationRelativeTo(ventana); // Centrar respecto a la ventana principal
        resultWindow.setVisible(true);

        // Restablecer el indicador incluso si la ventana no se cierra manualmente
        isBattleInProgress = false;
    }

    private boolean simulateBattle() {
        // Simulación básica: puedes reemplazar esto con una lógica más compleja
        int planetPower = calculateArmyPower(planetArmy);
        int enemyPower = calculateArmyPower(battle.getEnemyArmy());

        return planetPower > enemyPower; // El planeta gana si su poder es mayor
    }

    private int calculateArmyPower(ArrayList<MilitaryUnit>[] army) {
        int totalPower = 0;
        for (ArrayList<MilitaryUnit> group : army) {
            for (MilitaryUnit unit : group) {
                totalPower += unit.attack();
            }
        }
        return totalPower;
    }

    private ImageIcon loadImageIcon(String path, int width, int height) {
        try {
            System.out.println("Cargando imagen desde: " + path); // Mensaje de depuración
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("Image not found: " + path);
            ImageIcon placeholderIcon = new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
            Graphics g = placeholderIcon.getImage().getGraphics();
            g.setColor(Color.RED);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.WHITE);
            g.drawString("Image Not Found", 5, height / 2);
            g.dispose();
            return placeholderIcon;
        }
    }

    private void generateBattleReportXML(int battleNumber, String planetArmyReport, String enemyArmyReport) {
    String xmlFileName = "battle" + battleNumber + ".xml";

    try {
        // Crear el documento XML
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // Elemento raíz
        Element rootElement = doc.createElement("battleReport");
        doc.appendChild(rootElement);

        // Número de batalla
        Element battleNumberElement = doc.createElement("battleNumber");
        battleNumberElement.appendChild(doc.createTextNode(String.valueOf(battleNumber)));
        rootElement.appendChild(battleNumberElement);

        // Ejército del planeta
        Element planetArmyElement = doc.createElement("planetArmy");
        planetArmyElement.appendChild(doc.createTextNode(planetArmyReport.trim()));
        rootElement.appendChild(planetArmyElement);

        // Ejército enemigo
        Element enemyArmyElement = doc.createElement("enemyArmy");
        enemyArmyElement.appendChild(doc.createTextNode(enemyArmyReport.trim()));
        rootElement.appendChild(enemyArmyElement);

        // Escribir el contenido en el archivo XML con formato bonito
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(xmlFileName));

        transformer.transform(source, result);

        System.out.println("Archivo XML generado: " + xmlFileName);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void transformXMLToHTML(String xmlFileName, String xslFileName, String htmlFileName) {
    try {
        // Ruta de salida para los archivos HTML
        String outputPath = "webProjecte/Batallas/" + htmlFileName;

        // Ruta del archivo XSL (ajusta según la ubicación real)
        String xslPath = "src/" + xslFileName;

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslPath));
        transformer.transform(new StreamSource(xmlFileName), new StreamResult(outputPath));

        System.out.println("Archivo HTML generado: " + outputPath);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        try {
            new Juego();
            //DatabaseManager.resetAllBattleArmies();
            //DatabaseManager.resetAllBattleDefenses();
        } catch (ResourceException e) {
            e.printStackTrace();
 }
}
}