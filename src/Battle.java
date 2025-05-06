import java.util.ArrayList;

public class Battle {
	private ArrayList<MilitaryUnit>[] planetArmy; // nuestra flota
	private ArrayList<MilitaryUnit>[] enemyArmy; // flota enemiga
	private ArrayList[][] armies; //ArrayList de dos filas y siete columnas
								  // Primera fila (nosotros) Segunda fila (enemigo)
	
	private String battleDevelopment; // Guardamos desarollo de la batalla paso  paso
	private int[][] initialCostFleet;
	private int initialNumberUnitsPlanet;
	private int initialNumberUnitsEnemy;
	private int[] wasteMetalDeuterium;
	private int[] enemyDrops;
	private int[] plantetDrops;
	private int[][] resourcesLooses;
	private int[][] initialArmies;
	private int[] actualNumberUnitsPlanet;
	private int[] actualNumberUnitsEnemy;
	
	
	public ArrayList<MilitaryUnit>[] getPlanetArmy() {
		System.out.println("branch Alex");
		return planetArmy;
	}


	public ArrayList<MilitaryUnit>[] getEnemyArmy() {
		return enemyArmy;
	}
	
	
	
}
