import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main implements Variables{

	public static void main(String[] args) {
		Planet planet = new Planet(0,0, 53500,26800,UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST,UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST,null);
		Battle battle = new Battle();
		
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

			while (flg_menu_principal) {
				System.out.println(content_menu_principal);
				System.out.print("Option > ");

				try {
					int user_input = input.nextInt();
					if (user_input == 1) {
						System.out.println("Mostrando estadísticas del planeta...");
					} else if (user_input == 2) {
						flg_menu_principal = false;
						flg_menu_build = true;
					} else if (user_input == 3) {
						System.out.println("Mejorando tecnologías...");
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
						System.out.println("Construyendo Light Hunter...");
					} else if (user_input == 2) {
						System.out.println("Construyendo Heavy Hunter...");
					} else if (user_input == 3) {
						System.out.println("Construyendo Battle Ship...");
					} else if (user_input == 4) {
						System.out.println("Construyendo Armored Ship...");
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
						System.out.println("Construyendo Missile Launcher...");
					} else if (user_input == 2) {
						System.out.println("Construyendo Ion Cannon...");
					} else if (user_input == 3) {
						System.out.println("Construyendo Plasma Cannon...");
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
		}

		System.out.println("Closing ...");
		input.close();
	}
}
