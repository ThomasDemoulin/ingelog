package tests;


import junit.framework.*;
import tests.mocks.VehiculeControllerMock;

//ajout getspeed et ajout variable vitesse = 0

public class VehiculeControllerTest extends TestCase{
	
	
	/**
	 * Test VC1
	 * Test qui permet de contrôler si le robot avance/accélère
	 * On crée un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de récupérer l'ancienne vitesse 
	 * Et on fait avancer le robot
	 * On vérifie ensuite que la vitesse du robot après avoir avancé
	 * Est bien supérieure à l'ancienne vitesse
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
	 * Test qui permet de contrôler si le robot recule
	 * On crée un nouveau VehiculeController et on le fait reculer
	 * On vérifie ensuite que la vitesse du robot après avoir reculé
	 * Est bien égale à 10 (le programme met la vitesse par défaut à 10)
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
	 * Test qui permet de contrôler si le robot décélère
	 * On crée un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de récupérer l'ancienne vitesse 
	 * Et on fait décélérer le robot
	 * On vérifie ensuite que la vitesse du robot après avoir décéléré
	 * Est bien inférieure à l'ancienne vitesse
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
	 * Test qui permet de contrôler si le robot décélère
	 * On crée un nouveau VehiculeController et on initialise une variable
	 * oldSpeed qui permet de récupérer l'ancienne vitesse 
	 * Et on fait décélérer le robot
	 * On vérifie ensuite que la vitesse du robot après avoir décéléré
	 * Est bien inférieure à l'ancienne vitesse
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
	 * Test qui permet de contrôler l'arrêt du robot
	 * On crée un nouveau VehiculeController et on l'arrête
	 * On vérifie ensuite que la vitesse du robot est égale à 0
	 */
	public void testArret() throws Exception {
		VehiculeControllerMock ctrl = new VehiculeControllerMock();
		ctrl.arret();
		assertTrue(ctrl.enArret());
		ctrl.deconnection();
		Thread.sleep(100);
	}
}

