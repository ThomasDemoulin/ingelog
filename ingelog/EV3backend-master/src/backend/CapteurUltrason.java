package backend;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import backend.VehiculeController;

public class CapteurUltrason extends Thread {

	  /**
     * Classe qui permet de gérer le capteur de contact
     * pour savoir si le robot peut exécuter les mouvements de base
     */
    private EV3UltrasonicSensor capteur;
    private final SampleProvider distanceMode;
    private final float[] distanceSample;
    private VehiculeController controller;
    private boolean contact;

    public CapteurUltrason(EV3UltrasonicSensor capteur) {
        /**
         * Constructeur de la classe capteur contact
         * On recupère le mode distance qui va nous permettre de mesurer
         * la distance.
         */
        this.capteur = capteur;
        this.capteur.enable();
        this.distanceMode = this.capteur.getDistanceMode();
        distanceSample = new float[distanceMode.sampleSize()];
        this.contact = false;
    }
    
	@Override
	public void run() {
		while ( controller.isModeAutoActif() ) {
			if( this.isTooClose() ) {
				try {
					this.controller.sauverDistance();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

    public int getDistance() {
		distanceMode.fetchSample(distanceSample, 0);
		float result = distanceSample[0];
		return (int) (result * 100);
    }
    
    public boolean isTooClose() {
    	return (this.getDistance() < 25 ) ? true : false;
    }
    
	public VehiculeController getController() {
		return this.controller;
	}

	public void setController(VehiculeController controleur) {
		this.controller = controleur;
	}
}
