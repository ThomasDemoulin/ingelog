package backend;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.hardware.Bluetooth;
import lejos.remote.nxt.BTConnection;
import lejos.remote.nxt.BTConnector;
import lejos.remote.nxt.NXTConnection;;



public class MainRobotEV3 {
	
	private static DataOutputStream out; 
	private static DataInputStream in;
	private static BTConnection BTConnect;
	private static int commande=0;
	public static void main(String[] args) throws InterruptedException {
    	connect();

    	boolean stop_app = true;
    	VehiculeController ctrl = new VehiculeController();

    	while(stop_app)
    	{
    		try {
				commande = (int) in.readByte();
				switch(commande)
				{
    				case 1: 
				    	ctrl.avancer();
	    				break;
    				case 2:
    					ctrl.reculer();
    					break;
    				case 3:
					try {
						ctrl.tournerDroite();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    					break;
    				case 4:
					try {
						ctrl.tournerGauche();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    					break;
    				case 5:
    					ctrl.decelerer();
    					break;
    				case 6:
    					ctrl.arret();
    					break;
    				case 7 :
    					stop_app = false;
    					in.close();
    					out.close();
    					break;
    					
    				case 8:
    					ctrl.auto();
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
