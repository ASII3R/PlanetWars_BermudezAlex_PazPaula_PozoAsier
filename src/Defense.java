
public abstract class Defense implements MilitaryUnit, Variables{

	private int armor;
	private int initialArmor;
	private int baseDamage;
	private int defenseConstruido;
	private int defenseDestruido;
	
	public Defense(int armor, int initialArmor, int baseDamage) {
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

	public int getDefenseConstruido(){
		return defenseConstruido;
	}

	public void setDefenseConstruido(int defenseConstruido){
		this.defenseConstruido = defenseConstruido;
	}

	public int getDefenseDestruido(){
		return defenseDestruido;
	}

	public void setDefenseDestruido(){
		this.defenseDestruido = defenseDestruido;
	}
}
