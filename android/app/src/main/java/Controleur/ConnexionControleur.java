package Controleur;

import android.content.Context;

import Modele.ConnexionBase;

public class ConnexionControleur {
    private ConnexionBase connexionBase;

    public ConnexionControleur(Context context){
        connexionBase = new ConnexionBase(context);
    }

    //Permet de vérifier si les identifiants rentrés par l'utilisateur sont présents dans la base
    // de données
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
