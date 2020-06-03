package Outils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import android.content.*;

public class Logger {
    private static String fichierCommandes = "commandes.txt";

    public static void init(Context context) throws FileNotFoundException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fichierCommandes, Context.MODE_PRIVATE));
    }

    public static void ecrireCommande(String commande, Context context){
        try {
            ArrayList<String> commandes = lireCommandes(context);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fichierCommandes, Context.MODE_PRIVATE));
            for(int i = 0; i < commandes.size(); i++){
                outputStreamWriter.write(commandes.get(i) + "\n");
            }
            outputStreamWriter.write(new Date().getHours() + "h" + new Date().getMinutes() + " : " + commande);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            System.out.println("File write failed: " + e.toString());
        }
    }

    public static ArrayList<String> lireCommandes(Context context){
        ArrayList<String> commandes = new ArrayList<String>();

        try {
            InputStream inputStream = context.openFileInput(fichierCommandes);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    commandes.add(receiveString);
                }
                inputStream.close();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.toString());
        } catch (IOException e) {
            System.out.println("Can not read file: " + e.toString());
        }

        return commandes;
    }
}
