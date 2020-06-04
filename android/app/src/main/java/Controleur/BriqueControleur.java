package Controleur;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static java.util.UUID.fromString;

/**
 * Classe permettant la connexion entre l'application Android et la brique EV3
 *
 * https://stackoverflow.com/questions/4969053/bluetooth-connection-between-android-and-lego-mindstorm-nxt
 */
public class BriqueControleur implements Parcelable {

    //Création des éléments de connexion
    static boolean connexionSucces = true;
    public static BluetoothAdapter bluetoothAdapter;
    private static BluetoothSocket socketEV3;

    private String adresseMAC;

    public BriqueControleur(String adresseMAC) {
        this.adresseMAC = adresseMAC;
    }

    public String getAdresseMAC() {
        return adresseMAC;
    }

    public void setAdresseMAC(String adresseMAC) {
        this.adresseMAC = adresseMAC;
    }

    /////////////////Parcelable/////////////////
    protected BriqueControleur(Parcel in) {
        adresseMAC = in.readString();
    }

    public static final Creator<BriqueControleur> CREATOR = new Creator<BriqueControleur>() {
        @Override
        public BriqueControleur createFromParcel(Parcel in) {
            return new BriqueControleur(in);
        }

        @Override
        public BriqueControleur[] newArray(int size) {
            return new BriqueControleur[size];
        }
    };
    /////////////////Fin Parcelable/////////////////

    //Activer la connexion Bluetooth si elle n'est pas déjà active
    public static void activationBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Si pas de connexion alors l'activer
        if (!bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
    }

    //Connexion à la brique EV3
    public boolean connexionEV3() {
        activationBluetooth();

        //Récupérer le périphérique EV3
        BluetoothDevice EV3 = bluetoothAdapter.getRemoteDevice(getAdresseMAC());

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
        Thread.sleep(500);
        OutputStreamWriter outputMessage = new OutputStreamWriter(socketEV3.getOutputStream());
        outputMessage.write(message);
        outputMessage.flush();
    }

    public String[] recevoirMessage() throws InterruptedException, IOException {
        int bytes; // bytes returned from read()
        byte[] buffer = new byte[256];  // buffer store for the stream

        DataInputStream inputMessage = new DataInputStream(socketEV3.getInputStream());

        Thread.sleep(500);

        this.envoyerMessage((byte) 9);

        bytes = inputMessage.read(buffer);
        String readMessage = new String(buffer, 0, bytes);


        String[] tableauBogues = readMessage.split("/");
        return tableauBogues;
    }

    /////////////////Parcelable/////////////////
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(adresseMAC);
    }
    /////////////////Fin Parcelable/////////////////
}
