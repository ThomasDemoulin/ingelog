package Controleur;

import android.content.Context;

import Modele.ConnexionBase;

public class ConnexionControleur {
    private ConnexionBase connexionBase;

    public ConnexionControleur(Context context){
        connexionBase = new ConnexionBase(context);
    }

    public ConnexionBase getConnexionBase() {
        return connexionBase;
    }

    public void setConnexionBase(ConnexionBase connexionBase) {
        this.connexionBase = connexionBase;
    }
}
