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
	
	
	
}
