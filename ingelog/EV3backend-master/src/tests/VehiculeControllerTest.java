package tests;


import junit.framework.*;
import tests.mocks.VehiculeControllerMock;

//ajout getspeed et ajout variable vitesse = 0

public class VehiculeControllerTest extends TestCase{
	
	
	/**
	 * Test VC1
	 * Test qui permet de contr�ler si le robot avance/acc�l�re
	 * On cr�e un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de r�cup�rer l'ancienne vitesse 
	 * Et on fait avancer le robot
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir avanc�
	 * Est bien sup�rieure � l'ancienne vitesse
	 */
	public void testAvancer() throws Exception {
		VehiculeControllerMock ctrl = new VehiculeControllerMock();
		ctrl.avancer();
		assertTrue( !ctrl.enArret() );
		ctrl.deconnection();
		Thread.sleep(100);

	}

	/**
	 * Test VC4
	 * Test qui permet de contr�ler si le robot recule
	 * On cr�e un nouveau VehiculeController et on le fait reculer
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir recul�
	 * Est bien �gale � 10 (le programme met la vitesse par d�faut � 10)
	 */
	public void testReculer() throws Exception {
		VehiculeControllerMock ctrl = new VehiculeControllerMock();
		ctrl.reculer();
		assertTrue( !ctrl.enArret() );
		ctrl.deconnection();
		Thread.sleep(100);

	}
	
	/**
	 * Test VC2
	 * Test qui permet de contr�ler si le robot d�c�l�re
	 * On cr�e un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de r�cup�rer l'ancienne vitesse 
	 * Et on fait d�c�l�rer le robot
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir d�c�l�r�
	 * Est bien inf�rieure � l'ancienne vitesse
	 */
	public void testAcceler() throws Exception {
		VehiculeControllerMock ctrl = new VehiculeControllerMock();
		ctrl.avancer();
		int oldSpeed = ctrl.getVitesse();
		ctrl.avancer();

		assertTrue(oldSpeed < ctrl.getVitesse());
		ctrl.deconnection();
		Thread.sleep(100);

	}
		
	/**
	 * Test VC3
	 * Test qui permet de contr�ler si le robot d�c�l�re
	 * On cr�e un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de r�cup�rer l'ancienne vitesse 
	 * Et on fait d�c�l�rer le robot
	 * On v�rifie ensuite que la vitesse du robot apr�s avoir d�c�l�r�
	 * Est bien inf�rieure � l'ancienne vitesse
	 */
	public void testDecelerer() throws Exception {
		VehiculeControllerMock ctrl = new VehiculeControllerMock();
		ctrl.avancer();
		ctrl.avancer();
		int oldSpeed = ctrl.getVitesse();
		ctrl.decelerer();

		assertTrue(oldSpeed > ctrl.getVitesse());
		ctrl.deconnection();
		Thread.sleep(100);

	}
	
	/**
	 * Test VC5
	 * Test qui permet de contr�ler l'arr�t du robot
	 * On cr�e un nouveau VehiculeController et on l'arr�te
	 * On v�rifie ensuite que la vitesse du robot est �gale � 0
	 */
	public void testArret() throws Exception {
		VehiculeControllerMock ctrl = new VehiculeControllerMock();
		ctrl.arret();
		assertTrue(ctrl.enArret());
		ctrl.deconnection();
		Thread.sleep(100);
	}
}

