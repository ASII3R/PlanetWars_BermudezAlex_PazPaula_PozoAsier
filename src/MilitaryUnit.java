
public interface MilitaryUnit {
	// Estos métodos se tendrán que implementar en todas las clases que hereden de Ship.
	
	abstract int attack(); //Nos devolverá el poder de ataque que tenga la unidad.
	
	abstract void takeDamage(int receivedDamage); // Restará a nuestro blindaje el daño recibido por parámetro.
	
	abstract int getActualArmor(); //Nos devolverá el blindaje que tengamos actualmente, después de haber
								   //recibido un ataque.
	
	abstract int getMetalCost(); // Nos devolverá el coste de Metal que tiene crear una nueva unidad
	
	abstract int getDeteriumCost(); // Nos devolverá el coste de Deuterium que tiene crear una nueva unidad
	
	abstract int getChanceGeneratingWaste(); // Nos la probabilidad de generar residuos al ser totalmente eliminada
											 // (blindaje 0 o inferior).
	
	abstract int getChanceAttackAgain(); // Nos la probabilidad de generar volver a atacar
	
	abstract void resetArmor(); // Nos restablecerá nuestro blindaje a su valor original.

	
	

}
