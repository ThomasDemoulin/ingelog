package tests;

import junit.framework.TestCase;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import tests.mocks.metier.MoteurMock;

public class MoteurTest extends TestCase {
	
	final int VITESSE = 50;
	
	/**
	 * Test Mo1
	 * Vérifie que le moteur est en marche.
	 * @throws Exception
	 */
	public void testAvancer() throws Exception{
		/* On initialise la connexion à la brique EV3 grâce
		 * à l'interface RemoteEV3. Puis on affecte le port
		 * pour le créer notre moteur, comme pour l'interface
		 * RegulatedMotor
		*/
		RemoteEV3 ev3 = new RemoteEV3("10.0.1.1");
		RMIRegulatedMotor m1;
		m1 = ev3.createRegulatedMotor("A", 'L');
		MoteurMock moteur = new MoteurMock(m1);
		moteur.avancer(VITESSE);
		
		if (moteur.getVitesse() == 0){
			fail("Le moteur devrait avoir de la vitesse");
		}
		
		if (!moteur.enAction()){
			fail("Le moteur devrait avancer");
		}
		moteur.close();
		Thread.sleep(100);
	}
	
	/**
	 * Test Mo2
	 * Vérifie que la vitesse du moteur augmente après une accélération.
	 * @throws Exception
	 */
	public void testAccelere() throws Exception{
		RemoteEV3 ev3 = new RemoteEV3("10.0.1.1");
		RMIRegulatedMotor m1;
		m1 = ev3.createRegulatedMotor("A", 'L');
		MoteurMock moteur = new MoteurMock(m1);
		moteur.avancer(VITESSE);
		moteur.accelerer(VITESSE);
		
		if (moteur.getVitesse() <= 50){
			fail("La vitesse devrait etre superieur a 50");
		}
		moteur.close();
		Thread.sleep(100);
	}
	
	/**
	 * Test Mo5
	 * Vérifie que le moteur s'arrete.
	 * @throws Exception
	 */
	public void testStop() throws Exception{
		RemoteEV3 ev3 = new RemoteEV3("10.0.1.1");
		RMIRegulatedMotor m1;
		m1 = ev3.createRegulatedMotor("A", 'L');
		MoteurMock moteur = new MoteurMock(m1);
		moteur.stop();
		if (moteur.getVitesse() != 0){
			fail("La vitesse devrait etre nulle");
		}
		
		if (moteur.enAction()){
			fail("Le moteur devrait etre a l'arret");
		}
		moteur.close();
		Thread.sleep(100);
		
	}
	
	/**
	 * Test Mo3
	 * Vérifie que le moteur enclenche la marche arrière.
	 * @throws Exception
	 */

	public void testReculer() throws Exception{
		RemoteEV3 ev3 = new RemoteEV3("10.0.1.1");
		RMIRegulatedMotor m1;
		m1 = ev3.createRegulatedMotor("A", 'L');
		MoteurMock moteur = new MoteurMock(m1);
		moteur.reculer(VITESSE);
		if (moteur.getVitesse() == 0){
			fail("La vitesse devrait etre a 50");
		}
		
		if (!moteur.enAction()){
			fail("Le moteur devrait etre en marche");
		}
		moteur.close();
		Thread.sleep(100);
		
	}
	
	/**
	 * Test Mo4
	 * @throws Exception
	 */
//	@Test
//	public void testTourner() throws Exception{
//		Moteur moteurA = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
//		Moteur moteurB = new Moteur(new EV3LargeRegulatedMotor(MotorPort.B));
//
//	}
}
