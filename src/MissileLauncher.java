
public class MissileLauncher extends Defense{

	public MissileLauncher(int armor, int baseDamage) {
		super(ARMOR_MISSILELAUNCHER, ARMOR_MISSILELAUNCHER, BASE_DAMAGE_MISSILELAUNCHER);
	}

	public int attack() {
		return getBaseDamage();
	}

	public void takeDamage(int receivedDamage) {
		setArmor(ARMOR_MISSILELAUNCHER - receivedDamage);
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_MISSILELAUNCHER;
	}

	public int getDeteriumCost() {
		return DEUTERIUM_COST_MISSILELAUNCHER;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_MISSILELAUNCHER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;
	}

	public void resetArmor() {
		setArmor(ARMOR_MISSILELAUNCHER);
	}

}
