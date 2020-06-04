package Outils;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import android.content.*;
import android.util.Log;

public class Logger {
    //Classe permettant d'écrire ou de lire les logs côté Android.

    private static String fichierCommandes = "commandes.txt";

    //Cette méthode n'est appelée qu'une seule fois : lors de l'ouverture de l'application afin de
    // créer le fichier d'historique des commandes
    public static void init(Context context) throws FileNotFoundException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fichierCommandes, Context.MODE_PRIVATE));
    }

    //Cette méthode est appelée à chaque clic sur un bouton de l'utilisateur. Elle permet
    // l'écriture du log dans le fichier commandes.txt
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


    //Cette méthode est appelée lorsque l'utilisateur souhaite lire l'historique des commandes.
    // Elle permet la lecture du fichier commandes.txt
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
            Log.e("Erreur Logger", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Erreur Logger", "Can not read file: " + e.toString());
        }

        return commandes;
    }
}
