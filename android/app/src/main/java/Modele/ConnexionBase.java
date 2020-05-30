package Modele;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Outils.MySQLiteOpenHelper;

public class ConnexionBase {

    private String nomBase = "bdUsers";
    private Integer versionBase = 1;
    private MySQLiteOpenHelper accesBD;
    private SQLiteDatabase bd;

    public ConnexionBase(Context context){
        accesBD = new MySQLiteOpenHelper(context, nomBase, null, versionBase);
    }

    public void ajoutUtilisateur(String login, String motDePasse, int admin){
        bd = accesBD.getWritableDatabase();
        String requete = "insert into utilisateurs (login, motDePasse, admin) values ";
        requete += "(\""+login+"\",\""+motDePasse+"\","+admin+")";
        bd.execSQL(requete);
    }

    public Utilisateur getUtilisateur(String id){
        Utilisateur util = new Utilisateur();
        bd = accesBD.getReadableDatabase();
        String req = "select * from utilisateurs where login = \"" + id + "\";";
        Cursor curseur = bd.rawQuery(req, null);
        String login = curseur.getString(0);
        int ad = curseur.getInt(2);
        Boolean admin = false;
        if(ad != 0){
            admin = true;
        }
        util.setAdmin(admin);
        util.setIdentifiant(login);
        return util;
    }

    public String login(String id){
        bd = accesBD.getReadableDatabase();
        String req = "select * from utilisateurs where login = \"" + id + "\";";
        return "";
    }
}
