
public class LightHunter extends Ship{
	// [CONSTRUCTOR 1]
	public LightHunter(int armor, int baseDamage) {
		super(armor, armor, baseDamage);
		
	}
	
	// [CONSTRUCTOR 2]
	public LightHunter() {
		super(ARMOR_LIGTHHUNTER, ARMOR_LIGTHHUNTER, BASE_DAMAGE_LIGTHHUNTER);
		// Crea un objeto que se crea con sus propiedades por defecto
	}

	
	public int attack() {
		return getBaseDamage();
	}

	public void takeDamage(int receivedDamage) {
		setArmor(ARMOR_LIGTHHUNTER - receivedDamage);
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_LIGHTHUNTER;
	}

	public int getDeteriumCost() {
		return DEUTERIUM_COST_LIGHTHUNTER;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_LIGTHHUNTER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_LIGTHHUNTER;
	}

	public void resetArmor() {
		setArmor(ARMOR_LIGTHHUNTER);
	}

}
