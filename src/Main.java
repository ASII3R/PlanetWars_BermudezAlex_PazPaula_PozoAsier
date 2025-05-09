import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main implements Variables{

	public static void main(String[] args) throws ResourceException {
		ArrayList<MilitaryUnit>[] planetArmy = new ArrayList[7];
		ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[7];
		

		for (int i = 0; i < planetArmy.length; i++){
			planetArmy[i] = new ArrayList<>();
		}
		planetArmy[0].add(new LightHunter());
		planetArmy[1].add(new HeavyHunter());
		planetArmy[2].add(new BattleShip());
		planetArmy[3].add(new ArmoredShip());
		planetArmy[4].add(new MissileLauncher(ARMOR_MISSILELAUNCHER, BASE_DAMAGE_MISSILELAUNCHER));
		planetArmy[5].add(new IonCannon(ARMOR_IONCANNON, BASE_DAMAGE_PLASMACANNON));
		planetArmy[6].add(new PlasmaCannon(ARMOR_PLASMACANNON, BASE_DAMAGE_PLASMACANNON));
		
		Planet planet = new Planet(0,0, 53500,26800,UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST,UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST,planetArmy);
	//	Batalla batalla = new Batalla();

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
		timer.schedule(recolectResources, 60000,60000); // Cada 1 minuto recolecta recursos
		
		// [BATTLE SOURCES]
		Battle battle = new Battle();
		battle.setPlanetArmy(planetArmy);
		
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
			
			while(flg_menu_upgrade_technology){
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
					if (user_input == 1){
						planet.upgradeTechnologyDefense();
					} else if(user_input == 2){
						planet.upgradeTechnologyAttack();
					} else if (user_input == 3){
						flg_menu_upgrade_technology = false;
						flg_menu_principal = true;

					}
				} catch (InputMismatchException e) {
					System.out.println("Incorrect Option\n");
					input.next();
				}

			}
		}

			class Batalla {
				TimerTask batalla = new TimerTask() {
				
				public void run() {
					// Aquí va la lógica principal de ejecución de la batalla.
				}

				private void viewThreat() { //para ver que tipo de amenaza es
					System.out.println("\n[THREAT WARNING] Enemy Army approaching:");
					for (int i = 0; i < battle.getEnemyArmy().length; i++) {
						if (!battle.getEnemyArmy()[i].isEmpty()) {
							System.out.println(battle.getEnemyArmy()[i].get(0).getClass().getSimpleName()
									+ " x" + battle.getEnemyArmy()[i].size());
						}
					}
				}

				public void initInitialArmies() {
					// para inicializar el array initialArmies y poder calcular los reportes
				}
			
				void updateResourcesLooses() {
					// para generar el array de pérdidas
				}
			
				void fleetResourceCost(ArrayList<MilitaryUnit>[] army) {
					// para calcular costes de las flotas
				}
			
				void initialFleetNumber(ArrayList<MilitaryUnit>[] army) {
					// para calcular el número de unidades iniciales de cada flota
				}
			
				int remainderPercentageFleet(ArrayList<MilitaryUnit>[] army) {
					// para calcular los porcentajes de unidades que quedan respecto los ejércitos iniciales
					return 0;
				};
			
				int getGroupDefender(ArrayList<MilitaryUnit>[] army) {
					// para escoger grupo defensor
					return 0;
				}
			
				int getPlanetGroupAttacker() {
					// para escoger grupo atacante del planeta
					return 0;
				}
			
				int getEnemyGroupAttacker() {
					// para escoger grupo atacante enemigo
					return 0;
				}
			
				void resetArmyArmor() {
					// resetear los blindajes de nuestro ejército
				}
			};
		}
		
		System.out.println("Closing ...");
		input.close();
	}
}
