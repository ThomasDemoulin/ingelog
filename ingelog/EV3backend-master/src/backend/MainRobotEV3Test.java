package backend;

import backend.VehiculeController;
import junit.framework.*;

//ajout getspeed et ajout variable vitesse = 0

public class MainRobotEV3Test extends TestCase{
	
	/*
	 * Test qui permet de contrôler si le robot avance/accélère
	 * On crée un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de récupérer l'ancienne vitesse 
	 * Et on fait avancer le robot
	 * On vérifie ensuite que la vitesse du robot après avoir avancé
	 * Est bien supérieure à l'ancienne vitesse
	 */
	public void testAvancer() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		int oldSpeed = ctrl.getSpeed();
		ctrl.decelerer();
		assertTrue(oldSpeed < ctrl.getSpeed());
	}

	/*
	 * Test qui permet de contrôler si le robot recule
	 * On crée un nouveau VehiculeController et on le fait reculer
	 * On vérifie ensuite que la vitesse du robot après avoir reculé
	 * Est bien égale à 10 (le programme met la vitesse par défaut à 10)
	 */
	public void testReculer() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		ctrl.reculer();
		assertEquals(10, ctrl.getSpeed());
	}
		
	/*
	 * Test qui permet de contrôler si le robot décélère
	 * On crée un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de récupérer l'ancienne vitesse 
	 * Et on fait décélérer le robot
	 * On vérifie ensuite que la vitesse du robot après avoir décéléré
	 * Est bien inférieure à l'ancienne vitesse
	 */
	public void testDecelerer() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		int oldSpeed = ctrl.getSpeed();
		ctrl.decelerer();
		assertTrue(oldSpeed > ctrl.getSpeed());
	}
	
	/*
	 * Test qui permet de contrôler l'arrêt du robot
	 * On crée un nouveau VehiculeController et on l'arrête
	 * On vérifie ensuite que la vitesse du robot est égale à 0
	 */
	public void testStop() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		ctrl.stop();
		assertEquals(0, ctrl.getSpeed());
	}
}

