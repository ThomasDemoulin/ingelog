package com.example.control_ev3;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;

import java.io.IOException;
import java.io.OutputStreamWriter;

import static java.util.UUID.fromString;

/**
 * Classe permettant la connexion entre l'application Android et la brique EV3
 *
 * https://stackoverflow.com/questions/4969053/bluetooth-connection-between-android-and-lego-mindstorm-nxt
 */
public class ConnexionActivity<booleen> {

    //Création des éléments de connection

    static boolean connexionSucces = true;
    public static BluetoothAdapter bluetoothAdapter;
    private static BluetoothSocket socketEV3;

    //Activer la connexion Bluetooth si elle n'est pas déjà active
    public static void activationBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Si pas de connexion alors l'activer
         if (!bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
    }

    //Connexion à la brique EV3
    public static boolean connexionEV3() {
        activationBluetooth();

        //Saisir l'adresse MAC de la brique EV3
        String adresseMAC = "00:16:53:56:5F:C2";
        //Récupérer le périphérique EV3
        BluetoothDevice EV3 = bluetoothAdapter.getRemoteDevice(adresseMAC);

        //Connexion à la brique EV3
        try {
            socketEV3 = EV3.createRfcommSocketToServiceRecord(fromString("00001101-0000-1000-8000-00805F9B34FB"));
            socketEV3.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connexionSucces = false;
        }
        return connexionSucces;
    }

    public void envoyerMessage(byte message) throws InterruptedException, IOException {
                OutputStreamWriter outputMessage = new OutputStreamWriter(socketEV3.getOutputStream());
                outputMessage.write(message);
                outputMessage.flush();
                Thread.sleep(1000);
    }
}
