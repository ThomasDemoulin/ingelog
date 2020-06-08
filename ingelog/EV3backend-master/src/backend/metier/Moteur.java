package backend.metier;

import lejos.robotics.RegulatedMotor;

public class Moteur {
	/**
	 * Classe qui permet de gérer la communication avec le componsant moteur du robot via l'api EV3
	 */

	private RegulatedMotor moteur;
	private int vitesse;

	public Moteur(RegulatedMotor moteur) {
		// Constructeur
		this.moteur = moteur;
		this.vitesse = 50;
	}
	
	public void setVitesse(int v) {
		this.vitesse = v;
		this.moteur.setSpeed(this.vitesse );
	}
	
	public int getVitesse() {
		return this.moteur.getSpeed();
	}
	
	public RegulatedMotor getMoteur() {
		return this.moteur;
	}
	
	public boolean enAction() {
		return this.moteur.isMoving();
	}
	
	public void avancer(int v){
		setVitesse(v);
		this.moteur.forward();
	}

	public void reculer(int v){
		setVitesse(v);
		if (this.moteur.isMoving()) {
			this.moteur.stop();
			this.moteur.backward();
		}
		else {
			this.moteur.backward();
		}

	}

	public void accelerer(int v){
		if(this.moteur.isMoving()) {
			this.setVitesse(this.vitesse + v);
			this.moteur.forward();
		}
		
	}

	public void decelerrer(){
		if(this.moteur.isMoving()) {
			if(this.getVitesse() >= 100) {
				this.setVitesse(this.vitesse - 50);
			}
			this.moteur.forward();
		}
	}

	public void tourner() throws InterruptedException {
		if(this.moteur.isMoving()) {
	    	int oldSpeed = this.moteur.getSpeed();
	    	int newSpeed = (oldSpeed + this.vitesse)*2;
	    	this.moteur.setSpeed(newSpeed);
	    	Thread.sleep(1000);
		}
	}


	public void stop() {
		this.setVitesse(0);
		this.moteur.stop();
		
	}




}
