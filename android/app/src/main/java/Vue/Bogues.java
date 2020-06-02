package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import Controleur.BriqueControleur;

public class Bogues extends AppCompatActivity {
    Button bogues_retour;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bogues);

        //Récupération de l'intent
        Intent intent = getIntent();
        final BriqueControleur briqueControleur = intent.getParcelableExtra("briqueControleur");

        //Association de chaque élément avec la vue
        bogues_retour = (Button) findViewById(R.id.bogues_retour);

        try {
            briqueControleur.envoyerMessage((byte) 9);
            briqueControleur.recevoirMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Création des listeners
        bogues_retour.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
