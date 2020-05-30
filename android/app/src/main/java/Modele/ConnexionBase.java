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
