package backend;

import backend.VehiculeController;
import junit.framework.*;

//ajout getspeed et ajout variable vitesse = 0

public class MainRobotEV3Test extends TestCase{
	
	/*
	 * Test qui permet de contr�ler si le robot avance/acc�l�re
	 * On cr�e un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de r�cup�rer l'ancienne vitesse 
	 * Et on fait avancer le robot
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir avanc�
	 * Est bien sup�rieure � l'ancienne vitesse
	 */
	public void testAvancer() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		int oldSpeed = ctrl.getSpeed();
		ctrl.decelerer();
		assertTrue(oldSpeed < ctrl.getSpeed());
	}

	/*
	 * Test qui permet de contr�ler si le robot recule
	 * On cr�e un nouveau VehiculeController et on le fait reculer
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir recul�
	 * Est bien �gale � 10 (le programme met la vitesse par d�faut � 10)
	 */
	public void testReculer() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		ctrl.reculer();
		assertEquals(10, ctrl.getSpeed());
	}
		
	/*
	 * Test qui permet de contr�ler si le robot d�c�l�re
	 * On cr�e un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de r�cup�rer l'ancienne vitesse 
	 * Et on fait d�c�l�rer le robot
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir d�c�l�r�
	 * Est bien inf�rieure � l'ancienne vitesse
	 */
	public void testDecelerer() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		int oldSpeed = ctrl.getSpeed();
		ctrl.decelerer();
		assertTrue(oldSpeed > ctrl.getSpeed());
	}
	
	/*
	 * Test qui permet de contr�ler l'arr�t du robot
	 * On cr�e un nouveau VehiculeController et on l'arr�te
	 * On v�rifie ensuite que la vitesse du robot est �gale � 0
	 */
	public void testStop() throws Exception {
		VehiculeController ctrl = new VehiculeController();
		ctrl.stop();
		assertEquals(0, ctrl.getSpeed());
	}
}

