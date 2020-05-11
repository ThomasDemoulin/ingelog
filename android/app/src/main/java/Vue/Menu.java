package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    Button menu_robot;
    Button menu_commandes;
    Button menu_bogues;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Association de chaque élément avec la vue
        menu_robot = (Button) findViewById(R.id.menu_robot);
        menu_commandes = (Button) findViewById(R.id.menu_commandes);

        //Création des listeners
        menu_robot.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent().setClass(Menu.this, Connect.class);
                startActivity(intent);
            }
        });
    }
}
