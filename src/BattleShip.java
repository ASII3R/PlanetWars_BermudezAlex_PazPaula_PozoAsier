
public class BattleShip extends Ship{
	// [CONSTRUCTOR 1]
	public BattleShip(int armor, int baseDamage) {
		super(armor, armor, baseDamage);
	}

	// [CONSTRUCTOR 2]
	public BattleShip() {
		super(ARMOR_BATTLESHIP, ARMOR_BATTLESHIP, BASE_DAMAGE_BATTLESHIP);
		// Crea un objeto que se crea con sus propiedades por defecto
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void takeDamage(int receivedDamage) {
		setArmor(ARMOR_BATTLESHIP - receivedDamage);
	}
	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_BATTLESHIP;
	}

	public int getDeteriumCost() {
		return DEUTERIUM_COST_BATTLESHIP;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_BATTLESHIP;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_BATTLESHIP;
	}

	public void resetArmor() {
		setArmor(ARMOR_BATTLESHIP);
	}

}
