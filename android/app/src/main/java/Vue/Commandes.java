package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Commandes extends AppCompatActivity {

    Button commandes_retour;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commandes);

        //Association de chaque élément avec la vue
        commandes_retour = (Button) findViewById(R.id.commandes_retour);

        //Création des listeners
        commandes_retour.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
