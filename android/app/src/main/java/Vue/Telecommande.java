package Vue;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.anastr.speedviewlib.SpeedView;
import Controleur.BriqueControleur;
import Outils.Logger;

import java.io.IOException;

import static android.widget.RelativeLayout.*;

public class Telecommande extends AppCompatActivity {
    //Cette classe permet de gérer ce qui sera affiché sur la page de Télécommande

    //Création des différents boutons et de la connexion
    Button BAvancer ;
    Button BReculer ;
    Button BAccelerer ;
    Button BRalentir ;
    Button BArreter;
    Button BEteindre;
    ImageButton BGauche ;
    ImageButton BDroite ;
    Switch modeAuto;
    //Création de la vue de la vitesse
    TextView vitesseRobot;
    //Initialisation de la vitesse de départ à 0
    int vitesse = 0;
    Button BHistorique;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telecommande);

        //Récupération de l'intent
        Intent intent = getIntent();
        final Boolean admin = intent.getBooleanExtra("admin",true);
        final BriqueControleur briqueControleur = intent.getParcelableExtra("briqueControleur");

        //Association de chaque élément avec la vue
        BAvancer = (Button) findViewById(R.id.bAvancer);
        BReculer = (Button) findViewById(R.id.bReculer);
        BAccelerer = (Button) findViewById(R.id.bAccelerer);
        BRalentir = (Button) findViewById(R.id.bRalentir);
        BArreter = (Button) findViewById(R.id.bArreter);
        BGauche = (ImageButton) findViewById(R.id.bGauche);
        BDroite = (ImageButton) findViewById(R.id.bDroite);
        BEteindre = (Button) findViewById(R.id.bEteindre);
        vitesseRobot = (TextView) findViewById(R.id.vitesseRobot);
        modeAuto = (Switch) findViewById(R.id.modeAuto);
        BHistorique = (Button) findViewById(R.id.bHistorique);

        //On cache le bouton historique si ce n'est pas un admin
        if(!admin){
            BHistorique.setVisibility(View.GONE);
        }

        //Création d'un listener sur le bouton Avancer
        BAvancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Avancer seulement si le robot n'est pas en mode
                // automatique, et si celui-ci n'avance pas déjà
                if (!modeAuto.isChecked() && (vitesse == 0 || vitesse == -1)) {
                    Logger.ecrireCommande("Avancer", Telecommande.this);
                    vitesse = 1;
                    vitesseRobot.setText("Vitesse 1");
                    try {
                        briqueControleur.envoyerMessage((byte) 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Création d'un listener sur le bouton Accelerer
        BAccelerer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Accélérer seulement si le robot n'est pas en mode
                // automatique, si celui-ci avance et qu'il n'a pas atteint la vitesse maximale
                if (!modeAuto.isChecked() && vitesse > 0 && vitesse <= 4) {
                    Logger.ecrireCommande("Accelerer", Telecommande.this);
                    vitesse += 1;
                    vitesseRobot.setText("Vitesse " + vitesse);
                    try {
                        briqueControleur.envoyerMessage((byte) 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        //Création d'un listener sur le bouton Ralentir
        //La vitesse diminue si le robot avance et si celui-ci n'a pas atteint la vitesse minimale
        BRalentir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Ralentir seulement si le robot n'est pas en mode
                // automatique, si celui-ci avance et qu'il n'a pas atteint la vitesse minimale
                if (!modeAuto.isChecked() && vitesse > 1 && vitesse <= 5) {
                    Logger.ecrireCommande("Ralentir", Telecommande.this);
                    vitesse -= 1;
                    vitesseRobot.setText("Vitesse " + vitesse);
                    try {
                        briqueControleur.envoyerMessage((byte) 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Création d'un listener sur le bouton Reculer
        BReculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Reculer seulement si le robot n'est pas en mode
                // automatique, et si celui-ci ne recule pas déjà
                if (!modeAuto.isChecked() && vitesse >= 0) {
                    Logger.ecrireCommande("Reculer", Telecommande.this);
                    vitesse = -1;
                    vitesseRobot.setText("Vitesse -1");
                    try {
                        briqueControleur.envoyerMessage((byte) 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Création d'un listener sur le bouton droit
        BDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Droite seulement si le robot n'est pas en mode
                // automatique
                if (!modeAuto.isChecked()) {
                    Logger.ecrireCommande("Droite", Telecommande.this);
                    try {
                        briqueControleur.envoyerMessage((byte) 3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Création d'un listener sur le bouton gauche
        BGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Gauche seulement si le robot n'est pas en mode
                // automatique
                if (!modeAuto.isChecked()) {
                    Logger.ecrireCommande("Gauche", Telecommande.this);
                    try {
                        briqueControleur.envoyerMessage((byte) 4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Création d'un listener sur le bouton Arrêter
        BArreter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On ne peut cliquer sur le bouton Arrêter seulement si le robot n'est pas en mode
                // automatique, et si celui-ci n'est pas déjà arrêté
                if (!modeAuto.isChecked() || vitesse != 0) {
                    Logger.ecrireCommande("Arrêter", Telecommande.this);
                    vitesse = 0;
                    vitesseRobot.setText("Vitesse 0");
                    try {
                        briqueControleur.envoyerMessage((byte) 6);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        BEteindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On peut cliquer sur le bouton Eteindre à tout moment
                Logger.ecrireCommande("Eteindre", Telecommande.this);
                try {
                    briqueControleur.envoyerMessage((byte) 7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        modeAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    try {
                        briqueControleur.envoyerMessage((byte) 8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        briqueControleur.envoyerMessage((byte) 6);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if(admin) {
            BHistorique.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Logger.ecrireCommande("Voir l'historique des commandes", Telecommande.this);
                    Intent intent = new Intent().setClass(Telecommande.this, Bogues.class);
                    intent.putExtra("briqueControleur", briqueControleur);
                    startActivity(intent);
                }
            });
        }

    }


}
