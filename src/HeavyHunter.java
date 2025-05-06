
public class HeavyHunter extends Ship{
	// [CONSTRUCTOR 1]
	public HeavyHunter(int armor, int baseDamage) {
		super(armor, armor, baseDamage);
	}
	
	// [CONSTRUCTOR 2]
	public HeavyHunter() {
		super(ARMOR_HEAVYHUNTER, ARMOR_HEAVYHUNTER, BASE_DAMAGE_HEAVYHUNTER);
		// Crea un objeto que se crea con sus propiedades por defecto
	}
	public int attack() {
		return getBaseDamage();
	}

	public void takeDamage(int receivedDamage) {
		setArmor(ARMOR_HEAVYHUNTER - receivedDamage);
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_HEAVYHUNTER;
	}

	public int getDeteriumCost() {
		return DEUTERIUM_COST_HEAVYHUNTER;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_HEAVYHUNTER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_HEAVYHUNTER;
	}

	public void resetArmor() {
		setArmor(ARMOR_HEAVYHUNTER);
	}

}
