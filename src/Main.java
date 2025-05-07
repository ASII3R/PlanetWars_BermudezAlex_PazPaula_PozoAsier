import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main implements Variables{

	public static void main(String[] args) {
		ArrayList<MilitaryUnit>[] planetArmy = new ArrayList[7];
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
				System.out.printf("%-20s %d \n %-20s %d\n",
								"Actual Defense Technology: ", planet.getTechnologyDefense(),
								"Actual Attack Techology: ", planet.getTechnologyAttack());
				System.out.printf("\n%-20s %d %-20s\n%-20s %d %-20s\n%-20s",
							"1)Upgrade Defense Technology. Cost: ", planet.getUpgradeDefenseTechnologyDeuteriumCost(), "Deuterium",
									"2)Upgrade Attack Technology. Cost: ", planet.getUpgradeAttackTechnologyDeuteriumCost(), " Deuterium",
									"3)Go Back");
				System.out.print("Option > ");
				try {
					
				} catch (InputMismatchException e) {
					System.out.println("Incorrect Option\n");
					input.next();
				}

			}
		}

		System.out.println("Closing ...");
		input.close();
	}
}
