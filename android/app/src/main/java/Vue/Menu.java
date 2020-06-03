package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import Outils.Logger;

public class Menu extends AppCompatActivity {

    Button menu_robot;
    Button menu_commandes;
    Button menu_bogues;
    Button menu_deconnexion;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);

        //Récupération de l'intent
        Intent intent = getIntent();
        final Boolean admin = intent.getBooleanExtra("admin",true);

        //On charge le layout admin ou user en fonction de l'intent
        if(admin){
            setContentView(R.layout.menu_admin);
        }else{
            setContentView(R.layout.menu_user);
        }

        //Association de chaque élément avec la vue
        menu_robot = (Button) findViewById(R.id.menu_robot);
        menu_commandes = (Button) findViewById(R.id.menu_commandes);
        menu_bogues = (Button) findViewById(R.id.menu_bogues);
        menu_deconnexion = (Button) findViewById(R.id.menu_deconnexion);

        //Création des listeners
        menu_robot.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Logger.ecrireCommande("Se connecter au robot", Menu.this);
                Intent intent=new Intent().setClass(Menu.this, Connect.class);
                intent.putExtra("admin", admin);
                startActivity(intent);
            }
        });

        if(admin) {
            menu_commandes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Logger.ecrireCommande("Voir l'historique des commandes", Menu.this);
                    Intent intent = new Intent().setClass(Menu.this, Commandes.class);
                    startActivity(intent);
                }
            });

            menu_bogues.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Logger.ecrireCommande("Voir l'historique des bogues", Menu.this);
                    Intent intent = new Intent().setClass(Menu.this, Bogues.class);
                    startActivity(intent);
                }
            });
        }

        menu_deconnexion.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Logger.ecrireCommande("Déconnexion", Menu.this);
                finish();
            }
        });
    }
}
