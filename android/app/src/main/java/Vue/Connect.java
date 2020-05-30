package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Connect extends AppCompatActivity {

    Button connect_connexion;
    Button connect_retour;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);

        //Récupération de l'intent
        Intent intent = getIntent();
        final Boolean admin = intent.getBooleanExtra("admin",true);

        //Association de chaque élément avec la vue
        connect_connexion = (Button) findViewById(R.id.connect_connexion);
        connect_retour = (Button) findViewById(R.id.connect_retour);

        //Création des listeners
        connect_connexion.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent().setClass(Connect.this, Telecommande.class);
                intent.putExtra("admin", admin);
                startActivity(intent);
            }
        });

        connect_retour.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

}
