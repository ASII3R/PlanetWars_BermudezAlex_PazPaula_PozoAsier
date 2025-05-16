import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements Variables {

    public static void main(String[] args) throws ResourceException {
        int battleNumber = 1;
        ArrayList<MilitaryUnit>[] planetArmy = new ArrayList[7];
        ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[7];

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

        for (int i = 0; i < enemyArmy.length; i++) {
            enemyArmy[i] = new ArrayList<>();
        }

        Planet planet = new Planet(0, 0, 53500, 26800, UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST, UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST, planetArmy,1);
        int wins = 0;
        // [TIMER]
        Timer timer = new Timer();
        TimerTask recolectResources = new TimerTask() {
            public void run() {
                // Recoger Metal
                planet.setMetal(planet.getMetal() + PLANET_METAL_GENERATED);
                // Recoger Deuterium
                planet.setDeuterium(planet.getDeuterium() + PLANET_DEUTERIUM_GENERATED);
                System.out.println("\nRecolected Resources...\n");
            }
        };
        timer.schedule(recolectResources, 60000, 60000); // Cada 1 minuto recolecta recursos

        // [BATTLE SOURCES]
        Battle battle = new Battle();
        battle.setPlanetArmy(planetArmy);
        battle.setEnemyArmy(enemyArmy);

        // Crear instancia de Batalla
        Batalla batalla = new Batalla(battle, planet);

        Scanner input = new Scanner(System.in);

        String content_menu_principal = "\n1) View Planet Stats\n2) Build\n3) Upgrade Technology\n"
                + "4) View Battle Reports\n0) Exit";
        String content_build = "\n1) Build Troops\n2) Build Defenses\n3) Go Back";
        String content_build_troops = "\n1) Build Light Hunter\n2) Build Heavy Hunter\n"
                + "3) Build Battle Ship\n4) Build Armored Ship\n5) Go Back";
        String content_build_defenses = "\n1) Build Missile Launcher\n2) Build Ion Cannon\n"
                + "3) Build Plasma Cannon\n4) Go Back";

        boolean running = true;

        while (running) {
            boolean flg_menu_principal = true;
            boolean flg_menu_build = false;
            boolean flg_menu_build_troops = false;
            boolean flg_menu_build_defenses = false;
            boolean flg_menu_upgrade_technology = false;

            while (flg_menu_principal) {
                System.out.println(content_menu_principal);
                System.out.print("Option > ");

                try {
                    int user_input = input.nextInt();
                    if (user_input == 1) {
                        planet.printStats();
                    } else if (user_input == 2) {
                        flg_menu_principal = false;
                        flg_menu_build = true;
                    } else if (user_input == 3) {
                        flg_menu_principal = false;
                        flg_menu_upgrade_technology = true;

                    } else if (user_input == 4) {
                        System.out.println("Mostrando reportes de batalla...");
                        batalla.resolveBattle(planet, enemyArmy);

                        

                        // Aquí deberías calcular los datos necesarios para el resumen de batalla
                        // (planetUnits, planetDrops, etc.) antes de llamar a buildBattleSummary
                        int[] planetUnits = {11, 3, 1, 1, 11, 1, 1};
                        int[] planetDrops = {8, 1, 0, 0, 9, 1, 0};
                        int[] enemyUnits = {19, 7, 1, 1};
                        int[] enemyDrops = {17, 5, 1, 0};

                        // Variables de tipo int
                        int planetMetalCost = planet.getMetal();
                        int planetDeutCost = planet.getDeuterium();
                        int enemyMetalCost = 420;
                        int enemyDeutCost = 100;

                        int planetMetalLoss = 254;
                        int planetDeutLoss = 300;
                        int enemyMetalLoss = 100;
                        int enemyDeutLoss = 400;

                        int planetWasteMetal = planet.getMetal();
                        int planetWasteDeut = planet.getDeuterium();
                        boolean planetWins = (wins == 1);

                        System.out.println("Mostrando reportes de batalla...");
                        batalla.resolveBattle(planet, enemyArmy);

                        // calcula los datos y llama a buildBattleSummary
                        String xmlFileName = "batalla" + battleNumber + ".xml";
                        battle.buildBattleSummary(
                            battleNumber,
                            planetUnits, planetDrops, enemyUnits, enemyDrops,
                            planetMetalCost, planetDeutCost, enemyMetalCost, enemyDeutCost,
                            planetMetalLoss, planetDeutLoss, enemyMetalLoss, enemyDeutLoss,
                            planetWasteMetal, planetWasteDeut, planetWins
                        );
                        battle.exportBattleToXML(battleNumber, xmlFileName);

                        int htmlBattleNumber = 1;
                        File htmlFile;
                        do {
                            htmlFile = new File("battleReport" + htmlBattleNumber + ".html");
                            htmlBattleNumber++;
                        } while (htmlFile.exists());
                        htmlBattleNumber--;
                        String htmlFileName = "battleReport" + htmlBattleNumber + ".html";

                        // Transforma el XML a HTML
                        String xslFileName = "src/battleReport.xsl"; // Ajusta la ruta si es necesario
                        battle.transformXMLToHTML(xmlFileName, xslFileName, htmlFileName);
                        System.out.println("HTML generado: " + htmlFileName);
                        // ----------------------------------------
                        do {
                            htmlFile = new File("battleReport" + htmlBattleNumber + ".html");
                            htmlBattleNumber++;
                        } while (htmlFile.exists());
                        // Al salir del bucle, htmlBattleNumber se ha incrementado una vez de más
                        htmlBattleNumber--;

                    } else if (user_input == 0) {
                        running = false;
                        flg_menu_principal = false;
                    } else {
                        System.out.println("Invalid Option\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect Option\n");
                    input.next();
                }
            }

            while (flg_menu_build) {
                System.out.println(content_build);
                System.out.print("Option > ");

                try {
                    int user_input = input.nextInt();
                    if (user_input == 1) {
                        flg_menu_build = false;
                        flg_menu_build_troops = true;
                    } else if (user_input == 2) {
                        flg_menu_build = false;
                        flg_menu_build_defenses = true;
                    } else if (user_input == 3) {
                        flg_menu_build = false;
                        flg_menu_principal = true;
                    } else {
                        System.out.println("Invalid Option\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect Option\n");
                    input.next();
                }
            }

            while (flg_menu_build_troops) {
                System.out.println(content_build_troops);
                System.out.print("Option > ");

                try {
                    int user_input = input.nextInt();

                    if (user_input == 1) {
                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newLightHunter(user_amount);
                    } else if (user_input == 2) {
                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newHeavyHunter(user_amount);
                    } else if (user_input == 3) {
                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newBattleShip(user_amount);
                    } else if (user_input == 4) {
                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newArmoredShip(user_amount);
                    } else if (user_input == 5) {
                        flg_menu_build_troops = false;
                        flg_menu_build = true;
                    } else {
                        System.out.println("Invalid Option\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect Option\n");
                    input.next();
                }
            }

            while (flg_menu_build_defenses) {
                System.out.println(content_build_defenses);
                System.out.print("Option > ");

                try {
                    int user_input = input.nextInt();

                    if (user_input == 1) {

                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newMissileLauncher(user_amount);
                    } else if (user_input == 2) {
                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newIonCannon(user_amount);
                    } else if (user_input == 3) {
                        System.out.print("\nAmount of Units\nAmount: > ");
                        int user_amount = input.nextInt();
                        planet.newPlasmaCannon(user_amount);
                    } else if (user_input == 4) {
                        flg_menu_build_defenses = false;
                        flg_menu_build = true;
                    } else {
                        System.out.println("Invalid Option\n");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect Option\n");
                    input.next();
                }
            }

            while (flg_menu_upgrade_technology) {
                System.out.printf("%-20s %d\n %-20s %d\n",
                        "\nActual Defense Technology: ", planet.getTechnologyDefense(),
                        "Actual Attack Techology: ", planet.getTechnologyAttack());
                System.out.printf("\n%-20s %d %-20s\n%-20s %d %-20s\n%-20s",
                        "1)Upgrade Defense Technology. Cost: ", planet.getUpgradeDefenseTechnologyDeuteriumCost(), "Deuterium",
                        "2)Upgrade Attack Technology. Cost: ", planet.getUpgradeAttackTechnologyDeuteriumCost(), " Deuterium",
                        "3)Go Back");
                System.out.print("\nOption > ");
                try {
                    int user_input = input.nextInt();
                    if (user_input == 1) {
                        planet.upgradeTechnologyDefense();
                    } else if (user_input == 2) {
                        planet.upgradeTechnologyAttack();
                    } else if (user_input == 3) {
                        flg_menu_upgrade_technology = false;
                        flg_menu_principal = true;

                    }
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect Option\n");
                    input.next();
                }

            }
        }

        System.out.println("Closing ...");
        System.exit(0);
        input.close();
    }
}
class Batalla {
    private Timer timer = new Timer();
    private Battle battle;
    private Planet planet;

    public Batalla(Battle battle, Planet planet) {
        this.battle = battle;
        this.planet = planet;

        // Tareas periódicas
        timer.schedule(new TimerTask() {
            public void run() {
                viewThreat();
            }
        }, 0, 30000); // Cada 30s

        timer.schedule(new TimerTask() {
            public void run() {
                updateResourcesLooses();
            }
        }, 0, 60000); // Cada 60s

        timer.schedule(new TimerTask() {
            public void run() {
                fleetResourceCost(battle.getPlanetArmy());
            }
        }, 0, 90000); // Cada 90s
    }

    private void viewThreat() {
        System.out.println("\n[THREAT WARNING] Enemy Army approaching:");
        if (battle.getEnemyArmy() == null) {
            System.out.println("Enemy army data is not available.");
            return;
        }
        for (int i = 0; i < battle.getEnemyArmy().length; i++) {
            if (!battle.getEnemyArmy()[i].isEmpty()) {
                System.out.println(battle.getEnemyArmy()[i].get(0).getClass().getSimpleName()
                        + " x" + battle.getEnemyArmy()[i].size());
            }
        }
    }

    void updateResourcesLooses() {
        System.out.println("Updating resource losses...");
    }

    void fleetResourceCost(ArrayList<MilitaryUnit>[] army) {
        int totalMetalCost = 0;
        int totalDeuteriumCost = 0;

        for (ArrayList<MilitaryUnit> group : army) {
            for (MilitaryUnit unit : group) {
                totalMetalCost += unit.getMetalCost();
                totalDeuteriumCost += unit.getDeteriumCost();
            }
        }

        System.out.println("Total Metal Cost: " + totalMetalCost);
        System.out.println("Total Deuterium Cost: " + totalDeuteriumCost);
    }

    public void resolveBattle(Planet planet, ArrayList<MilitaryUnit>[] enemyArmy) {
        System.out.println("\n[Battle Report]");

        // Aplica daño mutuo entre ejércitos
        applyMutualDamage();

        // Elimina naves destruidas
        updatePlanetArmyAfterBattle();
        updateEnemyArmyAfterBattle();

        // Recalcula poder
        int planetPower = calculateArmyPower(battle.getPlanetArmy());
        int enemyPower = calculateArmyPower(battle.getEnemyArmy());

        if (planetPower > enemyPower) {
            System.out.println("Planet wins the battle!");
        
            int totalRecoveredMetal = 0;
            int totalRecoveredDeuterium = 0;
        
            for (ArrayList<MilitaryUnit> unitList : enemyArmy) {
                if (unitList != null) {
                    for (MilitaryUnit unit : unitList) {
                        // 50% de probabilidad de recuperar materiales de cada unidad
                        if (Math.random() < 0.5) {
                            totalRecoveredMetal += unit.getMetalCost();
                            totalRecoveredDeuterium += unit.getDeteriumCost();
                        }
                    }
                }
            }
        
            planet.receiveMetal(totalRecoveredMetal);
            planet.receiveDeuterium(totalRecoveredDeuterium);
        
            System.out.println("The planet recovers " + totalRecoveredMetal + " Metal and " + totalRecoveredDeuterium + " Deuterium from enemy wreckage.");
        
        } else {
            System.out.println("Planet lost the battle. No rewards.");
        }
        

        System.out.println("Updated planet army after the battle.");
    }

    private void applyMutualDamage() {
        int planetPower = calculateArmyPower(battle.getPlanetArmy());
        int enemyPower = calculateArmyPower(battle.getEnemyArmy());

        for (ArrayList<MilitaryUnit> group : battle.getPlanetArmy()) {
            for (MilitaryUnit unit : group) {
                unit.takeDamage(enemyPower);
            }
        }

        for (ArrayList<MilitaryUnit> group : battle.getEnemyArmy()) {
            for (MilitaryUnit unit : group) {
                unit.takeDamage(planetPower);
            }
        }
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

    private void updatePlanetArmyAfterBattle() {
        ArrayList<MilitaryUnit>[] planetArmy = battle.getPlanetArmy();

        for (ArrayList<MilitaryUnit> group : planetArmy) {
            Iterator<MilitaryUnit> iterator = group.iterator();
            while (iterator.hasNext()) {
                MilitaryUnit unit = iterator.next();
                if (unit.getActualArmor() <= 0) {
                    // Aumentamos contador de destrucciones de esa nave
                    if (unit instanceof Ship) {
                        Ship ship = (Ship) unit;
                        ship.setShipConstruido(ship.getShipDestruido() + 1);
                    }
                    // Eliminamos del ejército
                    iterator.remove();
                }
            }
        }
    }


    private void updateEnemyArmyAfterBattle() {
    ArrayList<MilitaryUnit>[] enemyArmy = battle.getEnemyArmy();

    for (ArrayList<MilitaryUnit> group : enemyArmy) {
        // Usamos un iterator para poder eliminar mientras recorremos, p
        Iterator<MilitaryUnit> iterator = group.iterator();
        while (iterator.hasNext()) {
            MilitaryUnit unit = iterator.next();
            if (unit.getActualArmor() <= 0) {
                // Incrementar contador destrucciones antes de eliminar
                Ship shipUnit = (Ship) unit;
                shipUnit.setShipDestruido(shipUnit.getShipDestruido() + 1);
                iterator.remove();
            }
        }
    }
}
}

