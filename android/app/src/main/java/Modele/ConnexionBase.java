package Modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Outils.MySQLiteOpenHelper;

public class ConnexionBase {
    //Cette classe permet l'instanciation et la connexion à la base de données

    private String nomBase = "bdUsers";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    //Instanciation de la base de données
    public ConnexionBase(Context context){
        accesBD = new MySQLiteOpenHelper(context, nomBase, null, versionBase);
    }

    //Cette méthode permet de récupérer un utilisateur grâce à son login
    public Utilisateur getUtilisateur(String log){
        bd = accesBD.getReadableDatabase();
        Utilisateur u = null;
        String req = "select * from utilisateurs where login = \'" + log + "\';";
        Cursor c = bd.rawQuery(req, null);
        if(c != null && c.getCount()>0) {
            c.moveToLast();
            if (!c.isAfterLast()) {
                String id = c.getString(0);
                int ad = c.getInt(2);
                Boolean admin = false;
                if (ad != 0) admin = true;
                u = new Utilisateur();
                u.setIdentifiant(id);
                u.setAdmin(admin);
            }
        }
        c.close();
        return u;
    }

    //Cette méthode permet de récupérer le mot de passe d'un utilisateur grâce à son login
    public String getMotDePasse(String log){
        String mdp = null;
        bd = accesBD.getReadableDatabase();
        String req = "select * from utilisateurs where login = \'" + log + "\';";
        Cursor c = bd.rawQuery(req, null);
        if(c != null) {
            c.moveToLast();
            if (!c.isAfterLast()) {
                mdp = c.getString(1);
            }
        }
        c.close();
        return mdp;
    }
}
