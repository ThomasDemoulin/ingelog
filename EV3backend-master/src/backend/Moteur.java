package backend;

import lejos.robotics.RegulatedMotor;

public class Moteur {
	/**
	 * Classe qui permet de gérer la communication avec le componsant moteur du robot via l'api EV3
	 */

	private RegulatedMotor moteur;
	private int vitesse;

	public Moteur(RegulatedMotor moteur, int v) {
		// Constructeur
		this.moteur = moteur;
		this.vitesse = v;
	}

	public void avancer(int v){
		this.setVitesse(v);
		this.moteur.forward();
	}
	
	public void setVitesse(int v) {
		this.vitesse = v;
		this.moteur.setSpeed(this.vitesse );
	}
	
	public int getVitesse() {
		return this.vitesse;
	}
	
	public RegulatedMotor getMoteur() {
		return this.moteur;
	}
	
	public boolean enAction() {
		return this.moteur.isMoving();
	}

	public void reculer(int v){
		if (this.moteur.isMoving()) {
			this.moteur.stop();
			this.setVitesse(v);
			this.moteur.backward();
		}
		else {
			this.setVitesse(v);
			this.moteur.backward();
		}

	}

	public void accelerer(){
		if(this.moteur.isMoving()) {
			this.setVitesse(this.vitesse + 50);
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

	public void tournerEnAvant() throws InterruptedException {
		if(this.moteur.isMoving()) {
	    	int oldSpeed = this.moteur.getSpeed();
	    	int newSpeed = (oldSpeed + this.vitesse)*2;
	    	this.moteur.setSpeed(newSpeed);
	    	Thread.sleep(1000);
		}
	}
	
	public void tournerEnArriere() throws InterruptedException {
		if(this.moteur.isMoving()) {
	    	int oldSpeed = this.moteur.getSpeed();
	    	int newSpeed = (oldSpeed + this.vitesse)*2;
	    	this.moteur.setSpeed(newSpeed);
	    	Thread.sleep(1000);
		}
	}


	public void stop() {
		this.moteur.stop();
		
	}




}
