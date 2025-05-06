import java.util.ArrayList;

public class Planet {
	private int technologyDefense;
	private int technologyAttack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;
	private ArrayList<MilitaryUnit>[] army = new ArrayList[7];
	
	public Planet(int technologyDefense, int technologyAttack, int metal, int deuterium,
			int upgradeDefenseTechnologyDeuteriumCost, int upgradeAttackTechnologyDeuteriumCost,
			ArrayList<MilitaryUnit>[] army) 
	{
		super();
		this.technologyDefense = technologyDefense;
		this.technologyAttack = technologyAttack;
		this.metal = metal;
		this.deuterium = deuterium;
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
		this.army = army;
	}

	public int getTechnologyDefense() {
		return technologyDefense;
	}

	public void setTechnologyDefense(int technologyDefense) {
		this.technologyDefense = technologyDefense;
	}

	public int getTechnologyAttack() {
		return technologyAttack;
	}

	public void setTechnologyAttack(int technologyAttack) {
		this.technologyAttack = technologyAttack;
	}

	public int getMetal() {
		return metal;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public int getDeuterium() {
		return deuterium;
	}

	public void setDeuterium(int deuterium) {
		this.deuterium = deuterium;
	}

	public int getUpgradeDefenseTechnologyDeuteriumCost() {
		return upgradeDefenseTechnologyDeuteriumCost;
	}

	public void setUpgradeDefenseTechnologyDeuteriumCost(int upgradeDefenseTechnologyDeuteriumCost) {
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
	}

	public int getUpgradeAttackTechnologyDeuteriumCost() {
		return upgradeAttackTechnologyDeuteriumCost;
	}

	public void setUpgradeAttackTechnologyDeuteriumCost(int upgradeAttackTechnologyDeuteriumCost) {
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return army;
	}

	public void setArmy(ArrayList<MilitaryUnit>[] army) {
		this.army = army;
	}
	
	// [ Funciones del Planeta ]
	
	// Actualizar "Mejoras" de Defensa
	public void upgradeTechnologyDefense() throws ResourceException {
		int precioAntiguoDefensa = this.getUpgradeAttackTechnologyDeuteriumCost();
		
		if (this.getDeuterium() > this.getUpgradeDefenseTechnologyDeuteriumCost()) {
			this.setTechnologyDefense(this.getTechnologyDefense() + 1); // Sube 1 punto de defensa
			
			// Restar material que tienes
			this.setDeuterium(getDeuterium() - this.getUpgradeDefenseTechnologyDeuteriumCost());
			
			// Actualizar precio de mejora de defensa
			this.setUpgradeDefenseTechnologyDeuteriumCost(
					(int) (this.getUpgradeDefenseTechnologyDeuteriumCost() * 1.10 )); //10%
			System.out.println("PRECIO MEJORA DEFENSA ANTES = " + precioAntiguoDefensa + " AHORA " + this.getUpgradeDefenseTechnologyDeuteriumCost());
			System.out.println("DETERIUM ACTUAL = " + this.getDeuterium());
		} else {
			// Si no tienes suficiente material salta el error
			throw new ResourceException("No tienes Deuterium suficiente!");
		}
	}
	// Actualizar "Mejoras" de Ataque
	public void upgradeTechnologyAttack() throws ResourceException {
		int precioAntiguoAtaque = this.getUpgradeDefenseTechnologyDeuteriumCost();
		if (this.getDeuterium() > this.getUpgradeAttackTechnologyDeuteriumCost()) {
			this.setTechnologyAttack(this.getTechnologyAttack() + 1); // Sumamos 1 punto al lvl de ataque
			
			// Restar material que tienes
			this.setDeuterium(getDeuterium() - this.getUpgradeAttackTechnologyDeuteriumCost());
			
			// Actualizar precio de mejora de ataque
			this.setUpgradeAttackTechnologyDeuteriumCost(
					(int) (this.getUpgradeAttackTechnologyDeuteriumCost() * 1.10)); // 10%
			System.out.println("PRECIO MEJORA ATAQUE ANTES = " + precioAntiguoAtaque + " AHORA " + this.getUpgradeAttackTechnologyDeuteriumCost());
			System.out.println("DETERIUM ACTUAL = " + this.getDeuterium());
		} else {
			// Si no tienes suficiente material salta el error
			throw new ResourceException("No tienes Deuterium suficiente!");
		}
	}
	
	// Añadir nuevas tropas a cambio de material (metal)
	
	public void newLightHunter(int n) {
		try {
			for (int i = 0; i < n; i++) {
				army[0].add(new LightHunter());
			}
		} catch (Exception e) {
			
		}
	}
	
	public void newHeavyHunter(int n) {
		for (int i = 0; i < n; i++) {
			army[1].add(new HeavyHunter());		}
	}
	
	public void newBattleShip(int n) {
		for (int i = 0; i < n; i++) {
			army[2].add(new BattleShip());
		}
	}
	
	public void newArmoredShip(int n) {
		for (int i = 0; i < n; i++) {
			army[3].add(new ArmoredShip());
		}
	}
	
	public void newMissileLauncher(int n) {
		for (int i = 0; i < n; i++) {
			army[4].add(new MissileLauncher(i, i));
		}
	}
	
	public void newIonCannon(int n) {
		for (int i = 0; i < n; i++) {
			army[5].add(new IonCannon(i, i));
		}
	}
	
	public void newPlasmaCannon(int n) {
		for (int i = 0; i < n; i++) {
			army[6].add(new PlasmaCannon(i, i));
		}
	}
	
	public int calcDamageShip(int damage, int plus_damage) {
		int damageTotal;
		int damageBonus = (this.getTechnologyAttack() * plus_damage * damage) / 100;
		damageTotal = damage + damageBonus;
		return damageTotal;
	}
	
	public int calcArmorShip(int armor, int plus_armor) {
		int armorTotal;
		int armorBonus = (this.getTechnologyDefense() * plus_armor * armor) / 100;
		armorTotal = armor + armorBonus;
		return armorTotal;
	}
	
	public int calcArmorDefense(int armor, int plus_armor) {
		int armorTotal;
		int armorBonus = (this.getTechnologyDefense() * plus_armor * armor) / 100;
		armorTotal = armor + armorBonus;
		return armorTotal;
	}
	
	public int calcDamageDefense(int damage, int plus_damage) {
		int damageTotal;
		int damageBonus = (this.getTechnologyAttack() * plus_damage * damage) /  100;
		damageTotal = damage + damageBonus;
		return damageTotal;
	}
	
	// Ver esto del planeta (informe general) habra que hacerlo formateado
	public void printStats() {
		System.out.println("Plantet Stats:\nTECHNOLOGY");
		System.out.println("Attack Technology  " + this.getTechnologyAttack() + "\nDefense Technology  " + this.getTechnologyAttack());
		System.out.println("DEFENSES");
		//habra que hacer un for-each con cada clase de barco
		System.out.println("RESOURCES\n Metal  " + this.getMetal() + "\nDeuterium  " + this.getDeuterium());
		
	}
}
