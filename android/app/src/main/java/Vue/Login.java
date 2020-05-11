package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button login_connexion;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Association de chaque élément avec la vue
        login_connexion = (Button) findViewById(R.id.login_connexion);

        //Création des listeners
        login_connexion.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent().setClass(Login.this, Menu.class);
                startActivity(intent);
            }
        });
    }

}
