
public class IonCannon extends Defense {

	public IonCannon(int armor, int baseDamage) {
		super(ARMOR_IONCANNON, ARMOR_IONCANNON, BASE_DAMAGE_IONCANNON);
		
	}

	public int attack() {
		return getBaseDamage();
	}

	public void takeDamage(int receivedDamage) {
		setArmor(ARMOR_IONCANNON - receivedDamage);
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_IONCANNON;
	}

	public int getDeteriumCost() {
		return DEUTERIUM_COST_IONCANNON;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_IONCANNON;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_IONCANNON;
	}

	public void resetArmor() {
		setArmor(ARMOR_IONCANNON);
	}

}
