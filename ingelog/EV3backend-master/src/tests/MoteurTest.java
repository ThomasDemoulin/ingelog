package tests;

import static org.junit.Assert.fail;

import org.junit.Test;

import backend.metier.Moteur;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class MoteurTest {
	
	final int VITESSE = 50;
	
	@Test
	/**
	 * Test Mo1
	 * V�rifie que le moteur est en marche.
	 * @throws Exception
	 */
	public void testAvancer() throws Exception{
		Moteur moteur = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
		moteur.avancer(VITESSE);
		
		if (moteur.getVitesse() == 0){
			fail("Le moteur devrait avoir de la vitesse");
		}
		
		if (!moteur.enAction()){
			fail("Le moteur devrait avancer");
		}
	}
	
	/**
	 * Test Mo2
	 * V�rifie que la vitesse du moteur augmente apr�s une acc�l�ration.
	 * @throws Exception
	 */
	@Test
	public void testAccelere() throws Exception{
		Moteur moteur = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
		moteur.avancer(VITESSE);
		moteur.accelerer(VITESSE);
		
		if (moteur.getVitesse() <= 50){
			fail("La vitesse devrait etre superieur a 50");
		}
	}
	
	/**
	 * Test Mo5
	 * V�rifie que le moteur s'arrete.
	 * @throws Exception
	 */
	@Test
	public void testStop() throws Exception{
		Moteur moteur = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
		moteur.stop();
		if (moteur.getVitesse() != 0){
			fail("La vitesse devrait etre nulle");
		}
		
		if (moteur.enAction()){
			fail("Le moteur devrait etre a l'arret");
		}
		
	}
	
	/**
	 * Test Mo3
	 * V�rifie que le moteur enclenche la marche arri�re.
	 * @throws Exception
	 */
	@Test
	public void testReculer() throws Exception{
		Moteur moteur = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
		moteur.reculer(VITESSE);
		if (moteur.getVitesse() == 0){
			fail("La vitesse devrait etre a 50");
		}
		
		if (!moteur.enAction()){
			fail("Le moteur devrait etre en marche");
		}
		
	}
	
	/**
	 * Test Mo4
	 * @throws Exception
	 */
	@Test
	public void testTourner() throws Exception{
		Moteur moteurA = new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
		Moteur moteurB = new Moteur(new EV3LargeRegulatedMotor(MotorPort.B));

	}
}
