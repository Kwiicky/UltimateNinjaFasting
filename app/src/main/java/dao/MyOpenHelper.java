package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin Stagiaire on 22/03/2018.
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "programmation";
    private static final int VERSION = 1;


    public MyOpenHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createProgrammationTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String createProgrammationTable(){
        return "CREATE TABLE programmation (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " plage_horaire TEXT, "+
                " heure_Debut Date, " +
                " heure_Fin Date," +
                " selection BOOLEAN)";

    }

}
