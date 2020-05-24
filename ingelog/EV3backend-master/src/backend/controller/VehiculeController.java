package backend.controller;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

import java.util.ArrayList;

import org.apache.log4j.Logger;

//import backend.MainRobotEV3;
import backend.metier.CapteurUltrason;
import backend.metier.Moteur;
import etats.EtatArret;
import etats.EtatAvance;
import etats.EtatRecule;
import etats.EtatRobot;

/**
 * Classe qui permet d'offrir les différents services de l'application.
 * Les services appels les classes qui s'occupent de l'infrastructure 
 * comme la base de données ou les componsants du robot.
 */




public class VehiculeController extends Thread {
	
	Moteur moteurDroit;
	Moteur moteurGauche;

    int vitesse;
    CapteurUltrason capteurDistance;
    boolean isModeAutoActif; 
    
//    private String loginUtilisateur;
    
    private EtatRobot etatCourant;
    private EtatRobot etatArret;
    private EtatRobot etatAvance;
    private EtatRobot etatRecul;
    
    private ArrayList<EtatRobot> arrayEtat;
    
    final static Logger logger = Logger.getLogger(backend.controller.VehiculeController.class);
 
    public VehiculeController()
	{
        /**
         * Initialisation des composants
         */
    	this.vitesse = 50;
    	this.moteurDroit 	= new Moteur(new EV3LargeRegulatedMotor(MotorPort.A), this.vitesse);
    	this.moteurGauche 	= new Moteur(new EV3LargeRegulatedMotor(MotorPort.B), this.vitesse);
    	
    	this.capteurDistance = new CapteurUltrason( new EV3UltrasonicSensor(SensorPort.S3));
    	this.capteurDistance.setController(this);
    	
    	RegulatedMotor sync[] = {this.moteurDroit.getMoteur()};
    	this.moteurGauche.getMoteur().synchronizeWith(sync);
    	
    	this.isModeAutoActif = false;
    	
    	this.etatCourant 	= new EtatArret();
    	
    	this.etatArret 		= new EtatArret();
    	this.etatAvance		= new EtatAvance();
    	this.etatRecul		= new EtatRecule();
    	
    	this.arrayEtat = new ArrayList<EtatRobot>();
 
	}

    public void avancer() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();
    	
    	if(	etatCourant instanceof EtatArret ) {
    		
    		this.vitesse = 50;
    		
    		this.moteurGauche.avancer(this.vitesse);
    		this.moteurDroit.avancer(this.vitesse);
    		
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

    	this.vitesse = 50;
    	if(this.moteurDroit.enAction() && this.moteurGauche.enAction()) {
    		this.moteurGauche.stop();
    		this.moteurDroit.stop();
    	}
  		this.moteurDroit.reculer(this.vitesse);
		this.moteurGauche.reculer(this.vitesse);
		
		etatCourant = etatRecul;
		sauverEtat(etatCourant);
		
    	this.moteurGauche.getMoteur().endSynchronization();
  
    }

    public void tournerDroite() throws InterruptedException {
    	
    	int oldVitesse = this.moteurGauche.getVitesse();
    	
    	if(	etatCourant instanceof EtatAvance ) {
        	this.moteurGauche.tourner();
        	this.moteurGauche.avancer(oldVitesse);
        	this.moteurDroit.avancer(oldVitesse);
        	
    	}else if(etatCourant instanceof EtatRecule) {
    		this.moteurGauche.tourner();
        	this.moteurGauche.reculer(oldVitesse);
        	this.moteurDroit.reculer(oldVitesse);
    	}
    }

    public void tournerGauche() throws InterruptedException {
    	
    	int oldVitesse = this.moteurDroit.getVitesse();

    	if(	etatCourant instanceof EtatAvance ) {
	    	this.moteurDroit.tourner();
			this.moteurDroit.avancer(oldVitesse);
			this.moteurGauche.avancer(oldVitesse);
			
    	}else if(etatCourant instanceof EtatRecule) {
	    	this.moteurDroit.tourner();
			this.moteurDroit.reculer(oldVitesse);
			this.moteurGauche.reculer(oldVitesse);
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

    	capteurDistance.start();
    	while(capteurDistance.isTooClose()) {}
    	
    	this.avancer();
    }

	public int getVitesse() {
		return this.vitesse;
	}
	
	public void sauverEtat(EtatRobot etatCourantRobot) {
		arrayEtat.add(etatCourantRobot);
		logger.info(etatCourantRobot.getEtat());
	}
	
	public EtatRobot getEtat() {
		return this.etatCourant;
	}
	
	public void obstacleDetecte()throws InterruptedException {
		if(etatCourant instanceof EtatAvance) {
			this.tournerDroite();
		}
	}
	
 }