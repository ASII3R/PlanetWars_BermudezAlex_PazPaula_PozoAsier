
public class PlasmaCannon extends Defense{

	public PlasmaCannon(int armor, int baseDamage) {
		super(ARMOR_PLASMACANNON, ARMOR_PLASMACANNON, BASE_DAMAGE_PLASMACANNON);
	}

	public int attack() {
		return getBaseDamage();
	}

	public void takeDamage(int receivedDamage) {
		setArmor(ARMOR_PLASMACANNON - receivedDamage);
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_PLASMACANNON;
	}

	public int getDeteriumCost() {
		return DEUTERIUM_COST_PLASMACANNON;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_PLASMACANNON;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_PLASMACANNON;
	}

	public void resetArmor() {
		setArmor(ARMOR_PLASMACANNON);
	}

}
