package Vue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;

import Controleur.ConnexionControleur;
import Modele.Utilisateur;
import Outils.Logger;

public class Login extends AppCompatActivity {

    Button login_connexion;
    EditText login_login;
    EditText login_mdp;
    TextView login_text;
    ConnexionControleur connexionControleur;

    protected void onCreate(Bundle savedInstanceState){
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        connexionControleur = new ConnexionControleur(Login.this);

        //Initialisation du logger
        try {
            Logger.init(Login.this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Association de chaque élément avec la vue
        login_connexion = (Button) findViewById(R.id.login_connexion);
        login_login = (EditText) findViewById(R.id.login_login);
        login_mdp = (EditText) findViewById(R.id.login_mdp);
        login_text = (TextView) findViewById(R.id.login_text);

        //Création des listeners
        login_connexion.setOnClickListener( new OnClickListener() {
            public void onClick(View v) {
                if(connexionControleur.verifierIdentifiants(login_login.getText().toString(), login_mdp.getText().toString())){
                    Utilisateur util = connexionControleur.getConnexionBase().getUtilisateur(login_login.getText().toString());
                    if(util.getAdmin()){
                        Logger.ecrireCommande("Connexion en tant qu'admin", Login.this);
                    }else{
                        Logger.ecrireCommande("Connexion en tant qu'utilisateur", Login.this);
                    }
                    Intent intent=new Intent().setClass(Login.this, Menu.class);
                    intent.putExtra("admin", util.getAdmin());
                    startActivity(intent);
                }else{
                    Logger.ecrireCommande("Tentative de connexion échouée", Login.this);
                    login_text.setText("Identifiants invalides");
                }
            }
        });
    }

}
