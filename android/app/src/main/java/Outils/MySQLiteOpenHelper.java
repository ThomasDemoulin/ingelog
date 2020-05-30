package Outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private String creation = "create table utilisateurs ("
            + "login TEXT PRIMARY KEY,"
            + "motDePasse TEXT NOT NULL,"
            + "admin INTEGER NOt NULL);";

    private String admin = "insert into utilisateurs (login, motDePasse, admin) values "
            + "(\"admin\",\"admin\",1);";

    private String user = "insert into utilisateurs (login, motDePasse, admin) values "
            + "(\"user\",\"user\",0);";

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creation);
        sqLiteDatabase.execSQL(admin);
        sqLiteDatabase.execSQL(user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
