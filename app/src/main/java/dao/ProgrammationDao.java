package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

import bean.Programmation;

/**
 * Created by FP13694 on 24/03/2018.
 */

public class ProgrammationDao {

    private static final String TABLE_PROGRAMMATION = "programmation";
    private MyOpenHelper helper;

    public ProgrammationDao(Context context) {
        this.helper = new MyOpenHelper(context);
    }

    public void create(Programmation prog){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = getContentValues(prog);
        long id = db.insert(TABLE_PROGRAMMATION,null,values);
        prog.setId(id);
        db.close();
    }

    public Programmation read(int id){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_PROGRAMMATION,
                getColumns(),
                "_id="+id,
                null,
                null,
                null,
                null,
                null);
        Programmation prog = null;
        if (cursor.moveToNext()){
            prog = new Programmation();
            setValues(prog,cursor);
        }
        db.close();
        return prog;
    }

    public void setValues(Programmation prog,Cursor cursor){
        prog = new Programmation();
        prog.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        prog.setPlageHoraire(cursor.getString(cursor.getColumnIndex("plage_horaire")));
        Date date = new Date(cursor.getLong(cursor.getColumnIndex("heure_debut")));
        prog.setHeureDebut(date);
        date = new Date(cursor.getLong(cursor.getColumnIndex("heure_fin")));
        prog.setHeureFin(date);
        prog.setSelection(cursor.getInt(cursor.getColumnIndex("selection"))==1);
    }

    public ArrayList<Programmation> read(){
        ArrayList<Programmation> resultat = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(true, TABLE_PROGRAMMATION,
                getColumns(),
                null,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            Programmation prog = new Programmation();
            resultat.add(prog);
            setValues(prog,cursor);
        }
        db.close();
        return resultat;
    }

    public void update(Programmation prog){
        if(prog.getId() == null){
            create(prog);
        }else{
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = getContentValues(prog);
            db.update(TABLE_PROGRAMMATION, values, "_id="+prog.getId(), null);
            db.close();

        }
    }

    @NonNull
    private ContentValues getContentValues(Programmation prog) {
        ContentValues values = new ContentValues();
        values.put("plage_horaire",prog.getPlageHoraire());
        values.put("heure_debut",prog.getHeureDebut().getTime());
        values.put("heure_fin",prog.getHeureFin().getTime());
        values.put("selection",prog.getSelection());
        return values;
    }

    public void delete(Programmation prog){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TABLE_PROGRAMMATION, "_id="+prog.getId(), null);
        db.close();

    }
    private String[] getColumns(){
        return new String[]{"_id","plage_horaire","heure_debut","heure_fin","selection"};
    }


}
