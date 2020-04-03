package backend;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

import java.util.ArrayList;

import backend.CapteurUltrason;
import etats.EtatArret;
import etats.EtatAvance;
import etats.EtatRecul;
import etats.EtatRobot;

/**
 * Classe qui permet d'offrir les différents services de l'application.
 * Les services appels les classes qui s'occupent de l'infrastructure 
 * comme la base de données ou les componsants du robot.
 */




public class VehiculeController extends Thread {
	
	Moteur moteurDroit;
	Moteur moteurGauche;

    int speed;
    int angle;
    CapteurUltrason myCapteur;
    boolean isModeAutoActif; 
    
    private EtatRobot etatCourant;
    private EtatRobot etatArret;
    private EtatRobot etatAvance;
    private EtatRobot etatRecul;
    
    private ArrayList<EtatRobot> arrayEtat;
 
    public VehiculeController()
	{
        /**
         * Initialisation des composants
         */
    	this.speed = 50;
    	this.moteurDroit 	= new Moteur(new EV3LargeRegulatedMotor(MotorPort.A), this.speed);
    	this.moteurGauche 	= new Moteur(new EV3LargeRegulatedMotor(MotorPort.B), this.speed);
    	
    	this.myCapteur = new CapteurUltrason( new EV3UltrasonicSensor(SensorPort.S3));
    	this.myCapteur.setController(this);
    	
    	RegulatedMotor sync[] = {this.moteurDroit.getMoteur()};
    	this.moteurGauche.getMoteur().synchronizeWith(sync);
    	
    	this.angle = 10;
    	this.isModeAutoActif = false;
    	
    	this.etatCourant 	= new EtatArret();
    	
    	this.etatArret 		= new EtatArret();
    	this.etatAvance		= new EtatAvance();
    	this.etatRecul		= new EtatRecul();
    	
    	this.arrayEtat = new ArrayList<EtatRobot>();
 
	}

    public void avancer() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();
    	
    	if(	etatCourant instanceof EtatArret ) {
    		
    		this.speed = 50;
    		
    		this.moteurGauche.avancer(this.speed);
    		this.moteurDroit.avancer(this.speed);
    		
    		etatCourant = etatAvance;
    		sauverEtat(etatCourant);
    		
    	}else if( etatCourant instanceof EtatAvance) {

    		this.moteurGauche.accelerer();
    		this.moteurDroit.accelerer();
    		
    		etatCourant = etatAvance;
    		sauverEtat(etatCourant);
    	}
    	this.moteurGauche.getMoteur().endSynchronization();

    }

    public void reculer() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();

    	this.speed = 50;
    	if(this.moteurDroit.enAction() && this.moteurGauche.enAction()) {
    		this.moteurGauche.stop();
    		this.moteurDroit.stop();
    	}
  		this.moteurDroit.reculer(this.speed);
		this.moteurGauche.reculer(this.speed);
		
		etatCourant = etatRecul;
		sauverEtat(etatCourant);
		
    	this.moteurGauche.getMoteur().endSynchronization();
  
    }

    public void tournerDroite() throws InterruptedException {
    	
//    	this.moteurGauche.getMoteur().startSynchronization();
    	int oldSpeed = this.moteurGauche.getVitesse();
    	
    	if(	etatCourant instanceof EtatAvance ) {
        	this.moteurGauche.tournerEnAvant();
        	this.moteurGauche.avancer(oldSpeed);
        	this.moteurDroit.avancer(oldSpeed);
        	
    	}else if(etatCourant instanceof EtatRecul) {
    		this.moteurGauche.tournerEnArriere();
        	this.moteurGauche.reculer(oldSpeed);
        	this.moteurDroit.reculer(oldSpeed);
    	}
    	
//    	this.moteurGauche.getMoteur().endSynchronization();

    }

    public void tournerGauche() throws InterruptedException {
    	
    	int oldSpeed = this.moteurDroit.getVitesse();

    	if(	etatCourant instanceof EtatAvance ) {
	    	this.moteurDroit.tournerEnAvant();
			this.moteurDroit.avancer(oldSpeed);
			this.moteurGauche.avancer(oldSpeed);
			
    	}else if(etatCourant instanceof EtatRecul) {
	    	this.moteurDroit.tournerEnArriere();
			this.moteurDroit.reculer(oldSpeed);
			this.moteurGauche.reculer(oldSpeed);
    	}

    }

    public void decelerer() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();
    	this.moteurGauche.decelerrer();
    	this.moteurDroit.decelerrer();
    	
    	this.etatCourant = etatAvance;
    	sauverEtat(etatCourant);
    	
    	this.moteurGauche.getMoteur().endSynchronization();
    }

    public void arret() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();
    	this.moteurDroit.stop();
    	this.moteurGauche.stop();
    	this.moteurGauche.getMoteur().endSynchronization();
    	
    	this.isModeAutoActif = false;

    	Thread.sleep(500);
    	
    	etatCourant = etatArret;
		sauverEtat(etatCourant);
    }
    
    public boolean isModeAutoActif() {
    	return this.isModeAutoActif;
    }
    
    public void auto() throws InterruptedException {
    	this.isModeAutoActif = true;

    	myCapteur.start();
    	while(myCapteur.isTooClose()) {}
    	
    	this.avancer();
    }

	public int getSpeed() {
		return this.speed;
	}
	
	public void sauverEtat(EtatRobot etatCourantRobot) {
		arrayEtat.add(etatCourantRobot);
	}
	
	public EtatRobot getEtat() {
		return this.etatCourant;
	}
	
	public void sauverDistance()throws InterruptedException {
		distance();
	}
	
	public void distance() throws InterruptedException  {
		if(etatCourant instanceof EtatAvance) {
			this.tournerDroite();
		}
	}
 }