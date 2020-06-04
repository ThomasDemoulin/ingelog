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
	 * V�rifie qu'un objet est d�tect� par le capteur.
	 */
	@Test
	public void testGetDistance() throws Exception{
		CapteurUltrason capteur = new CapteurUltrason(new EV3UltrasonicSensor(SensorPort.S3));
		
		if ( capteur.getDistance() <= 0) {
			fail("Un objet devrait �tre detecte ");
		}
	}
	
	/*
	 * Test C2
	 * V�rifie qu'un objet est d�tect� � moins de 25cm.
	 */
	@Test
	public void testIsTooClose() throws Exception{
		CapteurUltrason capteur = new CapteurUltrason(new EV3UltrasonicSensor(SensorPort.S3));
		
		if ( capteur.isTooClose() != true) {
			fail("Un objet devrait �tre � moins de 25cm du robot ");
		}
	}
}
