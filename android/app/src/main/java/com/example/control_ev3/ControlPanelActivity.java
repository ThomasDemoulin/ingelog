package com.example.control_ev3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import com.github.anastr.speedviewlib.SpeedView;
import Vue.R;

import java.io.IOException;

public class ControlPanelActivity extends AppCompatActivity {

    //Création des différents boutons et de la connexion
    ConnexionActivity BluetoothConnexion;
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
    SpeedView Speedometer ;
    //Initialisation de la vitesse de départ à 0
    //Distinction de la vitesse pour avancer et de la vitesse pour reculer
    int vitesseAvancer = 0;
    int vitesseReculer = 0;

    protected void onCreate(Bundle savedInstanceState) {
        //Création de la vue sur l'application Android
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telecommande);

        //Association de chaque élément avec la vue
        BAvancer = (Button) findViewById(R.id.bAvancer);
        BReculer = (Button) findViewById(R.id.bReculer);
        BAccelerer = (Button) findViewById(R.id.bAccelerer);
        BRalentir = (Button) findViewById(R.id.bRalentir);
        BArreter = (Button) findViewById(R.id.bArreter);
        BGauche = (ImageButton) findViewById(R.id.bGauche);
        BDroite = (ImageButton) findViewById(R.id.bDroite);
        BEteindre = (Button) findViewById(R.id.bEteindre);
        //Speedometer = (SpeedView) findViewById(R.id.speedView);
        Speedometer = null;
        modeAuto = (Switch) findViewById(R.id.modeAuto);


        //Création de la connexion Bluetooth
        BluetoothConnexion = new ConnexionActivity();
        ConnexionActivity.connexionEV3();

        //Création d'un listener sur le bouton Avancer
        //On peux également accélérer grâce au bouton Avancer
        BAvancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitesseAvancer = vitesseAvancer + 10;
                vitesseReculer = 0;
                Speedometer.speedTo(vitesseAvancer);

                try {
                    BluetoothConnexion.envoyerMessage((byte) 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Création d'un listener sur le bouton Accelerer
        //La vitesse augmente de 10 en 10
        BAccelerer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitesseReculer != 0) {
                    vitesseReculer = vitesseReculer + 10;
                    vitesseAvancer = 0;
                    Speedometer.speedTo(vitesseReculer);
                }
                else {
                    vitesseAvancer = vitesseAvancer + 10;
                    vitesseReculer = 0;
                    Speedometer.speedTo(vitesseAvancer);
                }
                try {
                    BluetoothConnexion.envoyerMessage((byte) 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //Création d'un listener sur le bouton Ralentir
        //La vitesse diminue de 10 en 10
        BRalentir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vitesseReculer != 0) {
                    vitesseReculer = vitesseReculer - 10;
                    vitesseAvancer = 0;
                    Speedometer.speedTo(vitesseReculer);
                }
                else {
                    vitesseAvancer = vitesseAvancer - 10;
                    vitesseReculer = 0;
                    Speedometer.speedTo(vitesseAvancer);
                }
                try {
                    BluetoothConnexion.envoyerMessage((byte) 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Création d'un listener sur le bouton Reculer
        //On peux également accélérer grâce au bouton Reculer
        BReculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vitesseReculer = vitesseReculer + 10;
                vitesseAvancer = 0;
                Speedometer.speedTo(vitesseReculer);

                try {
                    BluetoothConnexion.envoyerMessage((byte) 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Création d'un listener sur le bouton droit
        BDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BluetoothConnexion.envoyerMessage((byte) 3);
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
                    BluetoothConnexion.envoyerMessage((byte) 4);
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
                vitesseReculer = 0;
                vitesseAvancer = 0;
                Speedometer.speedTo(0);
                try {
                    BluetoothConnexion.envoyerMessage((byte) 6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        BEteindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                try {
                    BluetoothConnexion.envoyerMessage((byte) 7);
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
                        BluetoothConnexion.envoyerMessage((byte) 8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        BluetoothConnexion.envoyerMessage((byte) 6);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }


}
