package tests.mocks.metier;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;

public class MoteurMock {
	/**
	 * Classe qui permet de gérer la communication avec le composant moteur du robot directement
	 * via l'ordinateur afin d'effectuer des tests
	 */

	private RMIRegulatedMotor moteur;
	private int vitesse;

	public MoteurMock(RMIRegulatedMotor moteur) {
		// Constructeur
		this.moteur = moteur;
		this.vitesse = 50;
	}
	
	public void setVitesse(int v) throws RemoteException {
		this.vitesse = v;
		this.moteur.setSpeed(this.vitesse );
	}
	
	public void close() throws RemoteException{
		this.moteur.close();
	}
	
	public int getVitesse() throws RemoteException {
		return this.moteur.getSpeed();
	}
	
	
	public RMIRegulatedMotor getMoteur() {
		return this.moteur;
	}
	
	public boolean enAction() throws RemoteException {
		return this.moteur.isMoving();
	}
	
	public void avancer(int v) throws RemoteException{
		setVitesse(v);
		this.moteur.forward();
	}

	public void reculer(int v) throws RemoteException{
		setVitesse(v);
		if (this.moteur.isMoving()) {
			this.moteur.stop(true);
			this.moteur.backward();
		}
		else {
			this.moteur.backward();
		}

	}

	public void accelerer(int v) throws RemoteException{
		if(this.moteur.isMoving()) {
			this.setVitesse(this.vitesse + v);
			this.moteur.forward();
		}
		
	}

	public void decelerrer() throws RemoteException{
		if(this.moteur.isMoving()) {
			if(this.getVitesse() >= 100) {
				this.setVitesse(this.vitesse - 50);
			}
			this.moteur.forward();
		}
	}

	public void tourner() throws InterruptedException, RemoteException {
		if(this.moteur.isMoving()) {
	    	int oldSpeed = this.moteur.getSpeed();
	    	int newSpeed = (oldSpeed + this.vitesse)*2;
	    	this.moteur.setSpeed(newSpeed);
	    	Thread.sleep(1000);
		}
	}


	public void stop() throws RemoteException {
		this.setVitesse(0);
		this.moteur.stop(true);
		
	}




}
