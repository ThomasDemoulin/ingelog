package backend.controller;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

//import backend.MainRobotEV3;
import backend.metier.CapteurUltrason;
import backend.metier.Moteur;
import etats.EtatArret;
import etats.EtatAvance;
import etats.EtatAvanceDroite;
import etats.EtatAvanceGauche;
import etats.EtatModeAutomatique;
import etats.EtatRecule;
import etats.EtatReculeDroite;
import etats.EtatReculeGauche;
import etats.EtatRobot;

/**
 * Classe qui permet d'offrir les différents services de l'application.
 * Les services appels les classes qui s'occupent de l'infrastructure 
 * comme la base de données ou les componsants du robot.
 */




public class VehiculeController extends Thread {
	
	Moteur moteurDroit;
	Moteur moteurGauche;
    CapteurUltrason capteurDistance;
    
    boolean isModeAutoActif; 
    
    private EtatRobot etatCourant;
    private EtatRobot etatArret;
    private EtatRobot etatAvance;
    private EtatRobot etatRecule;
    private EtatRobot etatAvanceGauche;
    private EtatRobot etatAvanceDroite;
    private EtatRobot etatReculeGauche;
    private EtatRobot etatReculeDroite;
    private EtatRobot etatModeAuto;
    
    private ArrayList<EtatRobot> arrayEtat;
    
    /**
     * Création d'un logger log4j
     */
    final static Logger logger = Logger.getLogger(backend.controller.VehiculeController.class);
    final int VITESSE = 50;
 
    public VehiculeController()
	{
    	/**
    	 * Initialisation des capteurs moteurs et distance.
    	 */
    	try {
	    	this.moteurDroit 	= new Moteur(new EV3LargeRegulatedMotor(MotorPort.A));
	    	this.moteurGauche 	= new Moteur(new EV3LargeRegulatedMotor(MotorPort.B));
	    	this.capteurDistance = new CapteurUltrason( new EV3UltrasonicSensor(SensorPort.S3));
	    	this.capteurDistance.setController(this);
    	} catch(Exception e) {
    		logger.error(e);
	    }
    	
    	/**
    	 * Synchronisation du moteurDroit avec le moteurGauche.
    	 */
    	RegulatedMotor sync[] = {this.moteurDroit.getMoteur()};
    	this.moteurGauche.getMoteur().synchronizeWith(sync);
    	
    	this.isModeAutoActif = false;
    	
    	/**
    	 * Initialisation des différents états du robot.
    	 */
    	
    	this.etatCourant 	= new EtatArret();
    	
    	this.etatArret 			= new EtatArret();
    	this.etatAvance			= new EtatAvance();
    	this.etatAvanceGauche 	= new EtatAvanceGauche();
    	this.etatAvanceDroite 	= new EtatAvanceDroite();
    	this.etatReculeGauche 	= new EtatReculeGauche();
    	this.etatReculeDroite 	= new EtatReculeDroite();
    	this.etatRecule 		= new EtatRecule();
    	this.etatModeAuto		= new EtatModeAutomatique();

    	this.arrayEtat = new ArrayList<EtatRobot>();
 
	}
    
    /**
     * Méthode permettant de faire avancer le robot s'il est à l'état Arret ou Recule
     * et fait accélérer le robot si celui-ci est déjà en état Avance.
     * @throws InterruptedException
     */

    public void avancer() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();
    	
    	if(	this.etatCourant instanceof EtatArret ||
    		this.etatCourant instanceof EtatRecule ) {
    		
    		this.moteurGauche.avancer(VITESSE);
    		this.moteurDroit.avancer(VITESSE);
    		
    		this.etatCourant = this.etatAvance;
    		sauverEtat(this.etatCourant);
    		
    	}else if( this.etatCourant instanceof EtatAvance) {

    		this.moteurGauche.accelerer(VITESSE);
    		this.moteurDroit.accelerer(VITESSE);
    		
    		this.etatCourant = this.etatAvance;
    		sauverEtat(this.etatCourant);
    	}
    	this.moteurGauche.getMoteur().endSynchronization();

    }
    
    /**
     * Méthode permettant de faire reculer le robot s'il est à l'état Avance ou Arret.
     * @throws InterruptedException
     */

    public void reculer() throws InterruptedException {
    	
    	this.moteurGauche.getMoteur().startSynchronization();
    	
    	if(	this.etatCourant instanceof EtatAvance || 
    		this.etatCourant instanceof EtatArret	) {
	    	
	
	    	if(this.moteurDroit.enAction() && this.moteurGauche.enAction()) {
	    		this.moteurGauche.stop();
	    		this.moteurDroit.stop();
	    	}
	  		this.moteurDroit.reculer(VITESSE);
			this.moteurGauche.reculer(VITESSE);
			
			this.etatCourant = this.etatRecule;
			sauverEtat(this.etatCourant);
    	}
    	
    	this.moteurGauche.getMoteur().endSynchronization();
  
    }

    /**
     * Méthode permettant de faire tourner à droite le robot, qu'il soit en état Avance
     * ou en état Recule.
     * @throws InterruptedException
     */
    
    public void tournerDroite() throws InterruptedException {
    	
    	int oldVitesse = this.moteurGauche.getVitesse();
    	
    	if(	this.etatCourant instanceof EtatAvance ) {
    		
        	this.moteurGauche.tourner();
        	
        	this.etatCourant = this.etatAvanceDroite;
        	sauverEtat(this.etatCourant);
        	
        	this.moteurGauche.avancer(oldVitesse);
        	this.moteurDroit.avancer(oldVitesse);
        	
        	this.etatCourant = this.etatAvance;
        	sauverEtat(this.etatCourant);
        	
    	}else if(this.etatCourant instanceof EtatRecule ||
    			this.etatCourant instanceof EtatReculeGauche) {
    		
    		this.moteurGauche.tourner();
    		
    		this.etatCourant = this.etatReculeDroite;
        	sauverEtat(this.etatCourant);

        	this.moteurGauche.reculer(oldVitesse);
        	this.moteurDroit.reculer(oldVitesse);
        	
        	this.etatCourant = this.etatRecule;
        	sauverEtat(this.etatCourant);
    	}
    }

    /**
     * Méthode permettant de faire tourner à gauche le robot, qu'il soit en état Avance
     * ou en état Recule.
     * @throws InterruptedException
     */
    
    public void tournerGauche() throws InterruptedException {
    	
    	int oldVitesse = this.moteurDroit.getVitesse();

    	if(	this.etatCourant instanceof EtatAvance ) {
    		
	    	this.moteurDroit.tourner();
	    	
	    	this.etatCourant = this.etatAvanceGauche;
			sauverEtat(this.etatCourant);
			
			this.moteurDroit.avancer(oldVitesse);
			this.moteurGauche.avancer(oldVitesse);
			
			this.etatCourant = this.etatAvance;
			sauverEtat(this.etatCourant);
			
    	}else if(this.etatCourant instanceof EtatRecule || 
    			this.etatCourant instanceof EtatReculeDroite) {
    		
	    	this.moteurDroit.tourner();
	    	
	    	this.etatCourant = this.etatReculeGauche;
        	sauverEtat(etatCourant);
        	
			this.moteurDroit.reculer(oldVitesse);
			this.moteurGauche.reculer(oldVitesse);
			
			this.etatCourant = this.etatRecule;
			sauverEtat(this.etatCourant);
    	}

    }
    
    /**
     * Méthode permettant de réduire la vitesse du robot si celui-ci est en état Avance.
     * @throws InterruptedException
     */

    public void decelerer() throws InterruptedException {
    	if(	this.etatCourant instanceof EtatAvance ) {
	    	this.moteurGauche.getMoteur().startSynchronization();
	    	this.moteurGauche.decelerrer();
	    	this.moteurDroit.decelerrer();
	    	
	    	this.etatCourant = this.etatAvance;
	    	sauverEtat(this.etatCourant);
	    	
	    	this.moteurGauche.getMoteur().endSynchronization();
    	}
    }
    
    /**
     * Méthode permettant d'arreter les moteurs du robot.
     * @throws InterruptedException
     */

    public void arret() throws InterruptedException {
    	this.moteurGauche.getMoteur().startSynchronization();
    	this.moteurDroit.stop();
    	this.moteurGauche.stop();
    	this.moteurGauche.getMoteur().endSynchronization();
    	
    	if(this.isModeAutoActif()) {
    		this.isModeAutoActif = false;
    	}

    	Thread.sleep(500);
    	
    	this.etatCourant = this.etatArret;
		sauverEtat(this.etatCourant);
    }
    
    public boolean isModeAutoActif() {
    	return this.isModeAutoActif;
    }
    
    /**
     * Méthode permettant de lancer le mode automatique du robot.
     * @throws InterruptedException
     */
    public void auto() throws InterruptedException {
    	if(this.isModeAutoActif == false) {
    		this.isModeAutoActif = true;
    		
    		/**
    		 * A modifier dans le prochaine incrément, on ne peut pas
    		 * lancer le même thread 2 fois.
    		 */

			capteurDistance.start();
        	while(capteurDistance.isTooClose()) {}
        	this.avancer();
    	}
    }
	
    /**
     * Méthode permettant d'afficher les différents état du robot et les ajoutes
     * à une ArrayList, utilisable pour le prochain incrément.
     * @param etatCourantRobot
     */
	public void sauverEtat(EtatRobot etatCourantRobot) {
		arrayEtat.add(etatCourantRobot);
		System.out.println(etatCourantRobot.getEtat());
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