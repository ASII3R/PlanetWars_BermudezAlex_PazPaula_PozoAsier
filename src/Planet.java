import java.util.ArrayList;

public class Planet implements Variables{
	private int technologyDefense;
	private int technologyAttack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;
	private ArrayList<MilitaryUnit>[] army = new ArrayList[7];
	private int idPlanet;
	
	public Planet(int technologyDefense, int technologyAttack, int metal, int deuterium,
			int upgradeDefenseTechnologyDeuteriumCost, int upgradeAttackTechnologyDeuteriumCost,
			ArrayList<MilitaryUnit>[] army, int idPlanet) 
	{
		super();
		this.technologyDefense = technologyDefense;
		this.technologyAttack = technologyAttack;
		this.metal = metal;
		this.deuterium = deuterium;
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
		this.army = army;
		this.idPlanet = idPlanet;
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
	public int getIdPlanet() {
		return idPlanet;
	}
	
	// [ Funciones del Planeta ]
	
	// Actualizar "Mejoras" de Defensa
	public void upgradeTechnologyDefense(){
		int precioAntiguoDefensa = this.getUpgradeAttackTechnologyDeuteriumCost();
		
		if (this.getDeuterium() > this.getUpgradeDefenseTechnologyDeuteriumCost()) {
			this.setTechnologyDefense(this.getTechnologyDefense() + 1); // Sube 1 punto de defensa
			
			// Restar material que tienes
			this.setDeuterium(getDeuterium() - this.getUpgradeDefenseTechnologyDeuteriumCost());
			
			// Actualizar precio de mejora de defensa
			this.setUpgradeDefenseTechnologyDeuteriumCost(
					(int) (this.getUpgradeDefenseTechnologyDeuteriumCost() * 1.6 )); //60%
			System.out.println("PRECIO MEJORA DEFENSA ANTES = " + precioAntiguoDefensa + " AHORA " + this.getUpgradeDefenseTechnologyDeuteriumCost());
			System.out.println("DETERIUM ACTUAL = " + this.getDeuterium());
		} else {
			// Si no tienes suficiente material salta el error
			System.out.println("\nNo tienes Deuterium suficiente!\n");
		}
	}
	// Actualizar "Mejoras" de Ataque
	public void upgradeTechnologyAttack() {
		int precioAntiguoAtaque = this.getUpgradeDefenseTechnologyDeuteriumCost();
		if (this.getDeuterium() > this.getUpgradeAttackTechnologyDeuteriumCost()) {
			this.setTechnologyAttack(this.getTechnologyAttack() + 1); // Sumamos 1 punto al lvl de ataque
			
			// Restar material que tienes
			this.setDeuterium(getDeuterium() - this.getUpgradeAttackTechnologyDeuteriumCost());
			
			// Actualizar precio de mejora de ataque
			this.setUpgradeAttackTechnologyDeuteriumCost(
					(int) (this.getUpgradeAttackTechnologyDeuteriumCost() * 1.6)); // 60%
			System.out.println("PRECIO MEJORA ATAQUE ANTES = " + precioAntiguoAtaque + " AHORA " + this.getUpgradeAttackTechnologyDeuteriumCost());
			System.out.println("DETERIUM ACTUAL = " + this.getDeuterium());
		} else {
			// Si no tienes suficiente material salta el error
			System.out.println("\nNo tienes Deuterium suficiente!\n");
		}
	}
	
	// Añadir nuevas tropas a cambio de material (metal)
	
	public void newLightHunter(int n) {
		LightHunter infoLightHunter = new LightHunter();
		int metalCost = infoLightHunter.getMetalCost();
		int deuteriumCost = infoLightHunter.getDeteriumCost();
		int amount = 0;
		
		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[0].add(new LightHunter());
				amount++;

				// Añadimos +1 en las id (shipConstruido)
				infoLightHunter.setShipConstruido(infoLightHunter.getShipConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Light Hunter");
				break;
			}
		}
		System.out.println("Added " + amount + " Light Hunter");
	}
	
	public void newHeavyHunter(int n) {
		HeavyHunter infoHeavyHunter = new HeavyHunter();
		int metalCost = infoHeavyHunter.getMetalCost();
		int deuteriumCost = infoHeavyHunter.getMetalCost();
		int amount = 0;

		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[1].add(new HeavyHunter());
				amount++;

				infoHeavyHunter.setShipConstruido(infoHeavyHunter.getShipConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Heavy Hunter");
				break;
			}
		}
		System.out.println("Added " + amount + " Heavy Hunter");
	}
	
	public void newBattleShip(int n) {
		BattleShip infoBattleShip = new BattleShip();
		int metalCost = infoBattleShip.getMetalCost();
		int deuteriumCost = infoBattleShip.getMetalCost();
		int amount = 0;

		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[2].add(new BattleShip());
				amount++;

				infoBattleShip.setShipConstruido(infoBattleShip.getShipConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Battle Ship");
				break;
			}
		}
		System.out.println("Added " + amount + " Battle Ship");
	}
	
	public void newArmoredShip(int n) {
		ArmoredShip infoArmoredShip = new ArmoredShip();
		int metalCost = infoArmoredShip.getMetalCost();
		int deuteriumCost = infoArmoredShip.getMetalCost();
		int amount = 0;

		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[3].add(new ArmoredShip());
				amount++;

				infoArmoredShip.setShipConstruido(infoArmoredShip.getShipConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Armored Ship");
				break;
			}
		}
		System.out.println("Added " + amount + " Armored Ship");
	}
	
	public void newMissileLauncher(int n) {
		MissileLauncher infoMissileLauncher = new MissileLauncher(ARMOR_MISSILELAUNCHER,BASE_DAMAGE_MISSILELAUNCHER);
		int metalCost = infoMissileLauncher.getMetalCost();
		int deuteriumCost = infoMissileLauncher.getMetalCost();
		int amount = 0;

		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[4].add(new MissileLauncher(ARMOR_MISSILELAUNCHER,BASE_DAMAGE_MISSILELAUNCHER));
				amount++;
				
				infoMissileLauncher.setDefenseConstruido(infoMissileLauncher.getDefenseConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Missile Launcher");
				break;
			}
		}
		System.out.println("Added " + amount + " Missile Launcher");
	}
	
	public void newIonCannon(int n) {
		IonCannon infoIonCannon = new IonCannon(ARMOR_IONCANNON,BASE_DAMAGE_IONCANNON);
		int metalCost = infoIonCannon.getMetalCost();
		int deuteriumCost = infoIonCannon.getMetalCost();
		int amount = 0;

		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[5].add(new IonCannon(ARMOR_IONCANNON,BASE_DAMAGE_IONCANNON));
				amount++;

				infoIonCannon.setDefenseConstruido(infoIonCannon.getDefenseConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Ion Cannon");
				break;
			}
		}
		System.out.println("Added " + amount + " Ion Cannon");
	}
	
	public void newPlasmaCannon(int n) {
		PlasmaCannon infoPlasmaCannon = new PlasmaCannon(ARMOR_PLASMACANNON,BASE_DAMAGE_PLASMACANNON);
		int metalCost = infoPlasmaCannon.getMetalCost();
		int deuteriumCost = infoPlasmaCannon.getMetalCost();
		int amount = 0;

		for (int i = 0; i < n; i++) {
			// Primero comprobar si reune los materiales necesarios
			if (this.getMetal() >= metalCost && this.getDeuterium() >= deuteriumCost){
				//Restamos dinero
				this.setMetal(this.getMetal() - metalCost);
				this.setDeuterium(this.getDeuterium() - deuteriumCost);
				army[6].add(new PlasmaCannon(ARMOR_PLASMACANNON,BASE_DAMAGE_PLASMACANNON));
				amount++;

				infoPlasmaCannon.setDefenseConstruido(infoPlasmaCannon.getDefenseConstruido() + 1);
			} else {
				System.out.println("There's not enough material to build a Plasma Cannon");
				break;
			}
		}
		System.out.println("Added " + amount + " Plasma Cannon");
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

	public void receiveMetal(int amount) {
		this.setMetal(this.getMetal() + amount);
	}
	
	public void receiveDeuterium(int amount) {
		this.setDeuterium(this.getDeuterium() + amount);
	}
	
	// Ver esto del planeta (informe general) habra que hacerlo formateado
	public void printStats() {
		System.out.println("\nPlantet Stats:\n"+"\nTECHNOLOGY");
		//System.out.println("\nAttack Technology  " + this.getTechnologyAttack() + "\nDefense Technology  " + this.getTechnologyAttack());
		System.out.printf("\n%-20s %d \n%-20s %d\n","Attack Technology",this.getTechnologyAttack(),"Defense Technology",this.getTechnologyDefense());
		System.out.println("\nDEFENSES");
		System.out.printf("\n%-20s %d \n%-20s %d \n%-20s %d\n",
		"Missile Launcher",army[4] != null ? army[4].size() : 0,
				"Ion Cannon", army[5] != null ? army[5].size() : 0,
				"Plasma Cannon", army[6] != null ? army[6].size() : 0);
		System.out.println("\nFLEET");
		System.out.printf("\n%-20s %d \n%-20s %d \n%-20s %d \n%-20s %d\n",
		"Light Hunter", army[0] != null ? army[0].size() : 0,
				"Heavy Hunter", army[1] != null ? army[1].size() : 0,
				"Battle Ship", army[2] != null ? army[2].size() : 0,
				"Armored Ship", army[3] != null ? army[3].size() : 0);
		System.out.println("\nRESOURCES");
		System.out.printf("\n%-20s %d \n%-20s %d\n",
		"Metal",this.getMetal(),
				"Deuterium", this.getDeuterium());
		
	}
}
