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

import java.io.IOException;

import static android.widget.RelativeLayout.*;

public class Telecommande extends AppCompatActivity {

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
        //On peut également accélérer grâce au bouton Avancer
        BAvancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitesse == 0 || vitesse == -1) {
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
        //La vitesse augmente si le robot avance et si celui-ci n'a pas atteint la vitesse maximale
        BAccelerer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitesse > 0 && vitesse <= 4) {
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
                if (vitesse > 1 && vitesse <= 5) {
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
        //On peux également accélérer grâce au bouton Reculer
        BReculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitesse >= 0 ) {
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
                try {
                    briqueControleur.envoyerMessage((byte) 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Création d'un listener sur le bouton gauche
        BGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    briqueControleur.envoyerMessage((byte) 4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Création d'un listener sur le bouton Arrêter
        //Envoie simplement l'ordre au robot de s'arrêter
        //Remet le compteur de vitesse à 0
        BArreter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitesse != 0) {
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
                    Intent intent = new Intent().setClass(Telecommande.this, Commandes.class);
                    startActivity(intent);
                }
            });
        }

    }


}
