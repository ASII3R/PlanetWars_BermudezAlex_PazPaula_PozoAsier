
public abstract class Ship implements MilitaryUnit, Variables{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	
	public Ship(int armor, int initialArmor, int baseDamage) {
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getInitialArmor() {
		return initialArmor;
	}

	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
	
	
}
