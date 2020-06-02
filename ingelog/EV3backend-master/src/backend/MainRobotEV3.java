package backend;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

	final static Logger logger = Logger.getLogger(MainRobotEV3.class);
	
	public static void main(String[] args) throws InterruptedException {
    	connect();
    	boolean stop_app = true;
    	VehiculeController vehiculeCrl = new VehiculeController();
    	//LogController logCtrl = new LogController(logger);
    	
    	final Logger logger = Logger.getLogger(backend.controller.VehiculeController.class);
    	
    	while(stop_app)
    	{
    		try {
				commande = (int) in.readByte();
				switch(commande)
				{
    				case 1: 
    					vehiculeCrl.avancer();
	    				break;
    				case 2:
    					vehiculeCrl.reculer();
    					break;
    				case 3:
					try {
						vehiculeCrl.tournerDroite();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    					break;
    				case 4:
					try {
						vehiculeCrl.tournerGauche();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    					break;
    				case 5:
    					vehiculeCrl.decelerer();
    					break;
    				case 6:
    					vehiculeCrl.arret();
    					break;
    				case 7 :
    					stop_app = false;
    					in.close();
    					out.close();
    					break;
    					
    				case 8:
    					vehiculeCrl.auto();
    					break;
    				case 9:
//    					logCtrl.getLog();
//    					out.writeChars("BITE");
    					logger.error("test");
    					break;
				}
    		}catch (IOException ioe) {
    			System.out.println("IO Exception readInt");
    		}
    	}
    }
	  
	public static void connect()
	{  
		System.out.println("En attente");
		BTConnector BTconnector = (BTConnector) Bluetooth.getNXTCommConnector();
		BTConnect = (BTConnection) BTconnector.waitForConnection(30000, NXTConnection.RAW);
		out = BTConnect.openDataOutputStream();
		in = BTConnect.openDataInputStream();
	}


}
