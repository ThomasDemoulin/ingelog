package Vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import 	android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;

import Outils.Logger;

public class Commandes extends AppCompatActivity {

    Button commandes_retour;
    ListView listeCommandes;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commandes);

        //Association de chaque élément avec la vue
        commandes_retour = (Button) findViewById(R.id.commandes_retour);
        listeCommandes = (ListView) findViewById(R.id.commandes_listview);

        //remplissage de la listView

        ArrayList<String> al = Logger.lireCommandes(this);
        for(int i = 0; i < al.size(); i++){
            System.out.println(al.get(i));
        }

        ArrayAdapter<String> itemsAdapter = null;
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Logger.lireCommandes(this));
        listeCommandes.setAdapter(itemsAdapter);

        //Création des listeners
        commandes_retour.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Logger.ecrireCommande("Retour au menu", Commandes.this);
                finish();
            }
        });
    }
}
