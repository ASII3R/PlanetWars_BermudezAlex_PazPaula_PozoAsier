import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

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

        planet = new Planet(0, 0, 53500, 26800, UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST, 
                            UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST, planetArmy);
        
        battle = new Battle();
        battle.setPlanetArmy(planetArmy);

        ventana = new JFrame("Space Strategy Game");
        ventana.setSize(600, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new CardLayout());

        initializeScreens();
        ventana.setVisible(true);
    }

    private void initializeScreens() {
        // Start panel
        panelStart = new JPanel(new BorderLayout());
        panelStart.setBackground(new Color(255, 140, 0));

        JLabel title = new JLabel("Welcome to the game", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panelStart.add(title, BorderLayout.NORTH);

        JButton buttonPlay = new JButton("Play");
        buttonPlay.setPreferredSize(new Dimension(80, 25));
        buttonPlay.setBackground(new Color(255, 69, 0));
        buttonPlay.setForeground(Color.WHITE);
        buttonPlay.setFocusPainted(false);
        buttonPlay.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        buttonPlay.addActionListener(e -> switchPanel(panelMainMenu));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(buttonPlay);
        panelStart.add(buttonPanel, BorderLayout.CENTER);

        ventana.add(panelStart);

        // Main Menu panel
panelMainMenu = new JPanel(new GridLayout(5, 1));
panelMainMenu.setBackground(new Color(70, 130, 180));

JLabel titleMainMenu = new JLabel("Main Menu", SwingConstants.CENTER);
titleMainMenu.setFont(new Font("Arial", Font.BOLD, 18));
titleMainMenu.setForeground(Color.WHITE);
panelMainMenu.add(titleMainMenu);

// Crear botones
JButton buttonStats = new JButton("View Planet Stats");
JButton buttonBuild = new JButton("Build");
JButton buttonUpgrade = new JButton("Upgrade Technology");
JButton buttonBattleReports = new JButton("View Battle Reports");
JButton buttonExit = new JButton("Exit");

// Configurar colores de fondo
Color buttonColor = new Color(230, 230, 250);
buttonStats.setBackground(buttonColor);
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

JButton buttonLH = new JButton("Build Light Hunter");
JButton buttonHH = new JButton("Build Heavy Hunter");
JButton buttonBS = new JButton("Build Battle Ship");
JButton buttonAS = new JButton("Build Armored Ship");
JButton buttonBackTroops = new JButton("Go Back");

buttonBackTroops.addActionListener(e -> switchPanel(panelBuild));

panelBuildTroops.add(buttonLH);
panelBuildTroops.add(buttonHH);
panelBuildTroops.add(buttonBS);
panelBuildTroops.add(buttonAS);
panelBuildTroops.add(buttonBackTroops);

// Upgrade Technology panel
panelUpgradeTechnology = new JPanel(new GridLayout(3, 1));
panelUpgradeTechnology.setBackground(new Color(186, 85, 211));
panelUpgradeTechnology.add(new JLabel("Upgrade Technology", SwingConstants.CENTER));

JButton buttonDefense = new JButton("Upgrade Defense Technology");
buttonDefense.addActionListener(e -> planet.upgradeTechnologyDefense());
JButton buttonAttack = new JButton("Upgrade Attack Technology");
buttonAttack.addActionListener(e -> planet.upgradeTechnologyAttack());
JButton buttonBackUpgrade = new JButton("Go Back");

buttonBackUpgrade.addActionListener(e -> switchPanel(panelMainMenu));

panelUpgradeTechnology.add(buttonDefense);
panelUpgradeTechnology.add(buttonAttack);
panelUpgradeTechnology.add(buttonBackUpgrade);
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
