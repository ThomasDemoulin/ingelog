package tests.mocks;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import backend.metier.CapteurUltrason;
import etats.EtatArret;
import etats.EtatAvance;
import etats.EtatAvanceDroite;
import etats.EtatAvanceGauche;
import etats.EtatModeAutomatique;
import etats.EtatRecule;
import etats.EtatReculeDroite;
import etats.EtatReculeGauche;
import etats.EtatRobot;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import tests.mocks.metier.MoteurMock;

public class VehiculeControllerMock extends Thread {
	
	/**
	 * Classe qui permet de Mocker la classe VehiculeController directement
	 * via l'ordinateur afin d'effectuer des tests
	 */
	
	MoteurMock moteurDroit;
	MoteurMock moteurGauche;
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
    
    RMIRegulatedMotor m1;
    RMIRegulatedMotor m2;
    RemoteEV3 ev3;
    
    /**
     * Création d'un logger log4j
     */
    final static Logger logger = Logger.getLogger(backend.controller.VehiculeController.class);
    final int VITESSE = 50;
 
    public VehiculeControllerMock()
	{
    	/**
    	 * Initialisation des moteurs.
    	 */
    	try {
    		ev3 = new RemoteEV3("10.0.1.1");

    		this.m1 = ev3.createRegulatedMotor("A", 'L');
    		this.m2 = ev3.createRegulatedMotor("B", 'L');
    		this.moteurDroit  = new MoteurMock(this.m1);
    		this.moteurGauche = new MoteurMock(this.m2);

    	} catch(Exception e) {
    		System.out.println("erreur");
    		logger.error(e);
	    }
    	
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
    
    public void deconnection() throws RemoteException {
    	this.moteurDroit.close();
    	this.moteurGauche.close();
    }
    
    
    /**
     * Méthode permettant de faire avancer le robot s'il est à l'état Arret ou Recule
     * et fait accélérer le robot si celui-ci est déjà en état Avance.
     * @throws InterruptedException
     * @throws RemoteException 
     */

    public void avancer() throws InterruptedException, RemoteException {
    	
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

    }
    
    /**
     * Méthode permettant de faire reculer le robot s'il est à l'état Avance ou Arret.
     * @throws InterruptedException
     * @throws RemoteException 
     */

    public void reculer() throws InterruptedException, RemoteException {
    	
    	
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
  
    }

    /**
     * Méthode permettant de faire tourner à droite le robot, qu'il soit en état Avance
     * ou en état Recule.
     * @throws InterruptedException
     * @throws RemoteException 
     */
    
    public void tournerDroite() throws InterruptedException, RemoteException {
    	
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
     * @throws RemoteException 
     */
    
    public void tournerGauche() throws InterruptedException, RemoteException {
    	
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
     * @throws RemoteException 
     */

    public void decelerer() throws InterruptedException, RemoteException {
    	if(	this.etatCourant instanceof EtatAvance ) {

	    	this.moteurGauche.decelerrer();
	    	this.moteurDroit.decelerrer();
	    	
	    	this.etatCourant = this.etatAvance;
	    	sauverEtat(this.etatCourant);
	    	
    	}
    }
    
    /**
     * Méthode permettant d'arreter les moteurs du robot.
     * @throws InterruptedException
     * @throws RemoteException 
     */

    public void arret() throws InterruptedException, RemoteException {

    	this.moteurDroit.stop();
    	this.moteurGauche.stop();

    	
//    	if(this.isModeAutoActif()) {
//    		this.isModeAutoActif = false;
//    	}
    	
    	this.etatCourant = this.etatArret;
		sauverEtat(this.etatCourant);
    }
//    
//    public boolean isModeAutoActif() {
//    	return this.isModeAutoActif;
//    }
    
    /**
     * Méthode permettant de lancer le mode automatique du robot.
     * @throws InterruptedException
     */
//    public void auto() throws InterruptedException {
//    	if(this.isModeAutoActif == false) {
//    		this.isModeAutoActif = true;
//    		System.out.println("Mode automatique");
//    		/**
//    		 * A modifier dans le prochaine incrément, on ne peut pas
//    		 * lancer le même thread 2 fois.
//    		 */
//
//			capteurDistance.start();
//        	while(capteurDistance.isTooClose()) {}
//        	this.avancer();
//    	}
//    }
	
    /**
     * Méthode permettant d'afficher les différents état du robot et les ajoutes
     * à une ArrayList, utilisable pour le prochain incrément.
     * @param etatCourantRobot
     */
	public void sauverEtat(EtatRobot etatCourantRobot) {
		arrayEtat.add(etatCourantRobot);
		System.out.println(etatCourantRobot.getEtat());
	}
	
	public int getVitesse() throws RemoteException {
		if(this.moteurDroit.getVitesse() == this.moteurGauche.getVitesse()) {
			return this.moteurDroit.getVitesse();
		}else {
			return -1;
		}
	}
	
	
	public boolean enArret() throws RemoteException{
		if(this.moteurDroit.enAction() && 
				this.moteurGauche.enAction()) {
			return false;
		} else {
			return true;
		}
	}
	
	public EtatRobot getEtat() {
		return this.etatCourant;
	}
	
//	public void obstacleDetecte()throws InterruptedException {
//		if(etatCourant instanceof EtatAvance) {
//			this.tournerDroite();
//		}
//	}
	
 }