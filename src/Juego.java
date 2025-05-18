import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;


public class Juego implements Variables {
    private JFrame ventana;
    private JPanel panelStart, panelMainMenu, panelBuild, panelBuildTroops, panelBuildDefenses, panelUpgradeTechnology;
    private ArrayList<MilitaryUnit>[] planetArmy;
    private Planet planet;
    private Battle battle;

    private boolean flg_main_menu = true;
    private boolean flg_build_menu = false;
    private boolean flg_build_troops_menu = false;
    private boolean flg_build_defenses_menu = false;
    private boolean flg_upgrade_technology_menu = false;

    public Juego() throws ResourceException {
        // Initialize units and battle
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

        this.planet = new Planet(0, 0, 53500, 26800, UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST, UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST, planetArmy,1);
        
        battle = new Battle();
        battle.setPlanetArmy(planetArmy);

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

        // Agregar botones al panel
        panelMainMenu.add(buttonStats);
        panelMainMenu.add(buttonBuild);
        panelMainMenu.add(buttonUpgrade);
        panelMainMenu.add(buttonBattleReports);
        panelMainMenu.add(buttonExit);

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

        // Crear botones como JButton
        JButton buttonLH = new JButton("Build Light Hunter");
        JButton buttonHH = new JButton("Build Heavy Hunter");
        JButton buttonBS = new JButton("Build Battle Ship");
        JButton buttonAS = new JButton("Build Armored Ship");
        JButton buttonBackTroops = new JButton("Go Back");

        // Light Hunter
        buttonLH.addActionListener(e -> {
            int cantidad = getUnitAmount("Enter the number of Light Hunters:");
            LightHunter temp = new LightHunter();

            int metalCost = temp.getMetalCost();
            int deutCost = temp.getDeteriumCost();

            int posiblesMetal = planet.getMetal() / metalCost;
            int posiblesDeut = planet.getDeuterium() / deutCost;

            int posibles;
            if (posiblesMetal < posiblesDeut) {
                posibles = posiblesMetal;
            } else {
                posibles = posiblesDeut;
            }

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Light Hunters.");
            } else {
                int aConstruir;
                if (cantidad < posibles) {
                    aConstruir = cantidad;
                } else {
                    aConstruir = posibles;
                }

                planet.newLightHunter(aConstruir);

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

            int posiblesMetal = planet.getMetal() / metalCost;
            int posiblesDeut = planet.getDeuterium() / deutCost;

            int posibles;
            if (posiblesMetal < posiblesDeut) {
                posibles = posiblesMetal;
            } else {
                posibles = posiblesDeut;
            }

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Heavy Hunters.");
            } else {
                int aConstruir;
                if (cantidad < posibles) {
                    aConstruir = cantidad;
                } else {
                    aConstruir = posibles;
                }

                planet.newHeavyHunter(aConstruir);

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

            int posiblesMetal = planet.getMetal() / metalCost;
            int posiblesDeut = planet.getDeuterium() / deutCost;

            int posibles;
            if (posiblesMetal < posiblesDeut) {
                posibles = posiblesMetal;
            } else {
                posibles = posiblesDeut;
            }

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Battle Ships.");
            } else {
                int aConstruir;
                if (cantidad < posibles) {
                    aConstruir = cantidad;
                } else {
                    aConstruir = posibles;
                }

                planet.newBattleShip(aConstruir);

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

            int posiblesMetal = planet.getMetal() / metalCost;
            int posiblesDeut = planet.getDeuterium() / deutCost;

            int posibles;
            if (posiblesMetal < posiblesDeut) {
                posibles = posiblesMetal;
            } else {
                posibles = posiblesDeut;
            }

            if (posibles <= 0) {
                JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Armored Ships.");
            } else {
                int aConstruir;
                if (cantidad < posibles) {
                    aConstruir = cantidad;
                } else {
                    aConstruir = posibles;
                }

                planet.newArmoredShip(aConstruir);

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

    // Verificar que los costos no sean 0 para evitar división por cero
    if (metalCost == 0 || deutCost == 0) {
        JOptionPane.showMessageDialog(ventana, "Error: Cost values cannot be zero.");
        return;
    }

    int maxMetal = planet.getMetal() / metalCost;
    int maxDeut = planet.getDeuterium() / deutCost;

    int posibles;
    if (maxMetal < maxDeut) {
        posibles = maxMetal;
    } else {
        posibles = maxDeut;
    }

    int aConstruir;
    if (cantidad < posibles) {
        aConstruir = cantidad;
    } else {
        aConstruir = posibles;
    }

    if (posibles <= 0) {
        JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Missile Launchers.");
        return;
    }

    planet.newMissileLauncher(aConstruir);
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
    
    int posibles;
    if (maxMetal < maxDeut) {
        posibles = maxMetal;
    } else {
        posibles = maxDeut;
    }

    int aConstruir;
    if (cantidad < posibles) {
        aConstruir = cantidad;
    } else {
        aConstruir = posibles;
    }

    if (posibles <= 0) {
        JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Ion Cannons.");
        return;
    }

    planet.newIonCannon(aConstruir);
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
    
    int posibles;
    if (maxMetal < maxDeut) {
        posibles = maxMetal;
    } else {
        posibles = maxDeut;
    }

    int aConstruir;
    if (cantidad < posibles) {
        aConstruir = cantidad;
    } else {
        aConstruir = posibles;
    }

    if (posibles <= 0) {
        JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Plasma Cannons.");
        return;
    }

    planet.newPlasmaCannon(aConstruir);
    JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Plasma Cannons.");
});

// Armored Ship
JButton buttonArmoredShip = new JButton("Build Armored Ship");
buttonArmoredShip.addActionListener(e -> {
    int cantidad = getUnitAmount("Enter the number of Armored Ships:");
    ArmoredShip temp = new ArmoredShip(ARMOR_ARMOREDSHIP, BASE_DAMAGE_ARMOREDSHIP);

    int metalCost = temp.getMetalCost();
    int deutCost = temp.getDeteriumCost();

    int maxMetal = planet.getMetal() / metalCost;
    int maxDeut = planet.getDeuterium() / deutCost;

    int posibles = Math.min(maxMetal, maxDeut);

    int aConstruir;
    if (cantidad < posibles) {
        aConstruir = cantidad;
    } else {
        aConstruir = posibles;
    }

    if (posibles <= 0) {
        JOptionPane.showMessageDialog(ventana, "Not enough resources to build any Armored Ships.");
        return;
    }

    planet.newArmoredShip(aConstruir);
    JOptionPane.showMessageDialog(ventana, "Successfully built " + aConstruir + " Armored Ships.");
});

// Add buttons to panel
panelBuildDefenses.add(buttonML);
panelBuildDefenses.add(buttonIC);
panelBuildDefenses.add(buttonPC);
panelBuildDefenses.add(buttonBackDefenses);


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

    public static void main(String[] args) {
        try {
            new Juego();
        } catch (ResourceException e) {
            e.printStackTrace();
        }
    }
}