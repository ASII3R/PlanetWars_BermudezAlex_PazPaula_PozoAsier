
public class ArmoredShip extends Ship{
	// [CONSTRUCTOR 1]
	public ArmoredShip(int armor, int baseDamage) {
		super(armor, armor, baseDamage);
	}
	// [CONSTRUCTOR 2]
	public ArmoredShip() {
		super(ARMOR_ARMOREDSHIP, ARMOR_ARMOREDSHIP, BASE_DAMAGE_ARMOREDSHIP);
		// Crea un objeto que se crea con sus propiedades por defecto
	}
	
	public int attack() {
		return 0;
	}

	public void takeDamage(int receivedDamage) {
		
	}

	public int getActualArmor() {
		return 0;
	}

	public int getMetalCost() {
		return 0;
	}

	public int getDeteriumCost() {

		return 0;
	}

	public int getChanceGeneratingWaste() {
		return 0;
	}

	public int getChanceAttackAgain() {
		return 0;
	}

	public void resetArmor() {
		
	}

}
