package tests;

import static org.junit.Assert.fail;

import org.junit.Test;

import backend.metier.CapteurUltrason;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class CapteurUltrasonTest {
	
	/*
	 * Test C1
	 * Vérifie qu'un objet est détecté par le capteur.
	 */
	@Test
	public void testGetDistance() throws Exception{
		CapteurUltrason capteur = new CapteurUltrason(new EV3UltrasonicSensor(SensorPort.S3));
		
		if ( capteur.getDistance() <= 0) {
			fail("Un objet devrait être detecte ");
		}
	}
	
	/*
	 * Test C2
	 * Vérifie qu'un objet est détecté à moins de 25cm.
	 */
	@Test
	public void testIsTooClose() throws Exception{
		CapteurUltrason capteur = new CapteurUltrason(new EV3UltrasonicSensor(SensorPort.S3));
		
		if ( capteur.isTooClose() != true) {
			fail("Un objet devrait être à moins de 25cm du robot ");
		}
	}
}
