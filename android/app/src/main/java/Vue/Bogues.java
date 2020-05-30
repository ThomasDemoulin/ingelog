package Vue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Bogues extends AppCompatActivity {

    Button bogues_retour;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bogues);

        //Association de chaque élément avec la vue
        bogues_retour = (Button) findViewById(R.id.bogues_retour);

        //Création des listeners
        bogues_retour.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}
