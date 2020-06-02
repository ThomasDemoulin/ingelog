package Controleur;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
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
        OutputStreamWriter outputMessage = new OutputStreamWriter(socketEV3.getOutputStream());
        outputMessage.write(message);
        outputMessage.flush();
        Thread.sleep(1000);
    }

    public int recevoirMessage() throws InterruptedException, IOException {
        InputStreamReader inputMessage = new InputStreamReader(socketEV3.getInputStream(),"UTF-8");

        int c = inputMessage.read();
        
        // en UTF-8 le premier octet indique le codage
        c = inputMessage.read();
        while(c != -1){
            Log.e("COUCOU", (char)c + "");
            c = inputMessage.read();
        }

        return inputMessage.read();
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
