package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Controleur.BriqueControleur;
import Outils.Logger;

public class Connect extends AppCompatActivity {
    //Cette classe permet de gérer ce qui sera affiché sur la page de Connexion au robot

    Button connect_connexion;
    Button connect_retour;
    EditText connect_mac;
    TextView connect_erreur;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        //Récupération de l'intent
        Intent intent = getIntent();
        final Boolean admin = intent.getBooleanExtra("admin",true);

        //Association de chaque élément avec la vue
        connect_mac = (EditText) findViewById(R.id.connect_mac);
        connect_connexion = (Button) findViewById(R.id.connect_connexion);
        connect_erreur = (TextView) findViewById(R.id.connect_erreur);
        connect_retour = (Button) findViewById(R.id.connect_retour);

        //Création des listeners
        connect_connexion.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Logger.ecrireCommande("Tentative de connexion au robot", Connect.this);

                //Création de la connexion Bluetooth
                BriqueControleur briqueControleur = new BriqueControleur(connect_mac.getText() + "");
                try {
                    //Si l'application n'a pas réussi à se connecter à la brique, alors cela
                    // affiche un mesage d'erreur
                    if (!briqueControleur.connexionEV3()) {
                        throw new Exception();
                    }
                    //Si l'application a réussi à se connecter, alors on passe à la page de Menu
                    Logger.ecrireCommande("Connexion au robot réussie", Connect.this);
                    Intent intent = new Intent().setClass(Connect.this, Menu.class);
                    intent.putExtra("admin", admin);
                    intent.putExtra("briqueControleur", briqueControleur);
                    startActivity(intent);
                } catch (Exception e) {
                    Logger.ecrireCommande("Echec de connexion au robot", Connect.this);
                    connect_erreur.setText("Adresse invalide");
                }
            }
        });

        connect_retour.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Logger.ecrireCommande("Retour au menu", Connect.this);
                finish();
            }
        });
    }

}
