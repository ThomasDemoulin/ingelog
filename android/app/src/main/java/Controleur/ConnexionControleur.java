package Controleur;

import android.content.Context;

import Modele.ConnexionBase;

public class ConnexionControleur {
    private ConnexionBase connexionBase;

    public ConnexionControleur(Context context){
        connexionBase = new ConnexionBase(context);
    }

    public Boolean verifierIdentifiants(String login, String mdp){
        String mdpBase = connexionBase.getMotDePasse(login);
        if(mdpBase == null){
            return false;
        }else{
            return mdpBase.equals(mdp);
        }
    }

    public ConnexionBase getConnexionBase() {
        return connexionBase;
    }

    public void setConnexionBase(ConnexionBase connexionBase) {
        this.connexionBase = connexionBase;
    }
}
