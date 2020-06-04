package backend;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import backend.controller.LogController;

//import org.apache.log4j.Logger;

//import backend.controller.LogController;
import backend.controller.VehiculeController;
import lejos.hardware.Bluetooth;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;



public class MainRobotEV3 {
	
	private static DataOutputStream out; 
	private static DataInputStream in;
	private static BTConnection BTConnect;
	private static int commande=0;

	 /**
     * Création d'un logger log4j
     */
	final static Logger logger = Logger.getLogger(MainRobotEV3.class);
	
	/**
	 * Classe qui est le point d'entré de l'application. C'est ici que les messages de l'application
	 * android sont recepetionnes. 
	 */
	
	public static void main(String[] args) throws InterruptedException {
		/**
		 * Connexion auprès de l'application Android
		 */
		try {
			connect();
			System.out.println("Connexion reussie");
		} catch (Exception e) {
			logger.error(e);
		}
    	
		/**
		 * Initialisation du VehiculeController, du LogController et du booléen permettant de 
		 * stoper l'application. 
		 */
    	boolean stop_app = true;
    	VehiculeController vehiculeCrl = new VehiculeController();
    	LogController logCtrl = new LogController(logger);

    	
    	while(stop_app)
    	{
    		try {
				commande = (int) in.readByte();
				switch(commande)
				{
    				case 1: 
    					try {
    						vehiculeCrl.avancer();
    					} catch(InterruptedException e) {
    						System.out.println("An error occurred. Check the logs for more infos");
    						logger.error(e);
    					}
    					break;
    				case 2:
    					try {
    						vehiculeCrl.reculer();
    					} catch(InterruptedException e) {
    						System.out.println("An error occurred. Check the logs for more infos");
    						logger.error(e);
    					}
    					break;
    				case 3:
						try {
							vehiculeCrl.tournerDroite();
						} catch (InterruptedException e) {
							System.out.println("An error occurred. Check the logs for more infos");
							logger.error(e);
						}
    					break;
    				case 4:
						try {
							vehiculeCrl.tournerGauche();
						} catch (InterruptedException e) {
							System.out.println("An error occurred. Check the logs for more infos");
							logger.error(e);
						}
    					break;
    				case 5:
    					try {
        					vehiculeCrl.decelerer();
    					} catch(InterruptedException e) {
    						System.out.println("An error occurred. Check the logs for more infos");
    						logger.error(e);
    					}
    					break;
    				case 6:
    					try {
    						vehiculeCrl.arret();
    					} catch(InterruptedException e) {
    						System.out.println("An error occurred. Check the logs for more infos");
    						logger.error(e);
    					}
    					break;
    				case 7 :
    					stop_app = false;
    					in.close();
    					out.close();
    					break;
    					
    				case 8:
    					try {
        					vehiculeCrl.auto();
    					} catch(InterruptedException e) {
    						System.out.println("An error occurred. Check the logs for more infos");
    						logger.error(e);
    					}
    					break;
    				case 9:			
    					try {
    						String logs = logCtrl.getLog();
    						if(logs == null) {
    							out.writeChars("An error occured.");
        						out.close();
    							stop_app = false;
    						}else {
        						out.writeChars(logs);
        						out.close();
    						}
    					}catch(Exception e) {
    						System.out.println("An error occurred. Check the logs for more infos");
    						logger.error(e);
    					}
    					break;
				}
    		}catch (IOException ioe) {
    			System.out.println("IO Exception readInt");
    			logger.error(ioe);
    			stop_app = false;
    		}
    	}
    }
	
	/**
	 * Méthode permettant de se connecter à l'application Android.
	 */
	  
	public static void connect()
	{  
		System.out.println("En attente");
		BTConnector BTconnector = (BTConnector) Bluetooth.getNXTCommConnector();
		BTConnect = (BTConnection) BTconnector.waitForConnection(30000, NXTConnection.RAW);
		out = BTConnect.openDataOutputStream();
		in = BTConnect.openDataInputStream();
	}


}
