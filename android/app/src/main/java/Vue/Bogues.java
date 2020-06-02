package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

import Controleur.BriqueControleur;

public class Bogues extends AppCompatActivity {
    Button bogues_retour;
    ListView bogues_listview;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bogues);

        //Récupération de l'intent
        Intent intent = getIntent();
        final BriqueControleur briqueControleur = intent.getParcelableExtra("briqueControleur");

        //Association de chaque élément avec la vue
        bogues_retour = (Button) findViewById(R.id.bogues_retour);
        bogues_listview = (ListView) findViewById(R.id.bogues_listview);

        try {
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, briqueControleur.recevoirMessage());
            bogues_listview.setAdapter(adapter);
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
