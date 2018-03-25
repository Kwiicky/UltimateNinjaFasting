package acdc.ultimateninjafasting;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bean.Programmation;

public class ProgrammationDetailsActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 101;

    public static final String CURRENT_PROGRAMMATION = "Current_programmation";
    public static final String EDIT_MODE = "editmode";

    private Programmation programmationCourant = new Programmation();
    private boolean editmode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programmation_details);
        initData();
        initViews();
    }

    /**
     * Initialisation des données
     */
    private void initData() {
        if(getIntent().getExtras()!= null && getIntent().getExtras().containsKey(CURRENT_PROGRAMMATION)){
            Integer id = (Integer) getIntent().getSerializableExtra(CURRENT_PROGRAMMATION);


            // Récupération de la programmation dont l'id est passé en paramètre
            Cursor cursor = getContentResolver().query(Uri.parse("content://ultimate.ninja.fasting/programmation/"+id)
                    ,new String[]{"_id","plage_horaire","heure_debut","heure_fin","selection"}
                    ,null
                    ,null
                    , null);

            // Parcours de la liste pour récupérer la programmation qui nous intéresse
            while (cursor.moveToNext()){
                programmationCourant.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                programmationCourant.setPlageHoraire(cursor.getString(cursor.getColumnIndex("plage_horaire")));
                long heure_debut = cursor.getLong(cursor.getColumnIndex("heure_debut"));
                Date date = new Date(heure_debut);
                programmationCourant.setHeureDebut(date);
                long heure_fin = cursor.getLong(cursor.getColumnIndex("heure_fin"));
                date = new Date(heure_fin);
                programmationCourant.setHeureFin(date);
                programmationCourant.setSelection(cursor.getInt(cursor.getColumnIndex("selection"))==1);
            }

        }
        if(getIntent().getExtras() != null){
            editmode = getIntent().getBooleanExtra(EDIT_MODE,false);
        }
    }

    /**
     * Initialisation de la vue
     */
    private void initViews() {

        // TODO initialiser la vue avec les valeurs
        if(!editmode){
            findViewById(R.id.btnValider).setVisibility(View.GONE);

        }
    }

    // Méthode de clique sur les boutons
    public void clickEvent(View v){
        switch (v.getId()){
            case R.id.btnValider :
                Intent backIntent = getIntent();
                setResult(RESULT_OK,backIntent);
                backIntent.putExtra(CURRENT_PROGRAMMATION, createFromViews());
                finish();
                break;
            case R.id.editDateDebut:
                showTimeDialog(R.id.editDateDebut);
                break;
            case R.id.editDateFin:
                showTimeDialog(R.id.editDateFin);
                break;
        }
    }


    private Programmation createFromViews(){
        Programmation resultat = programmationCourant;

        // Set de la plage d'horaire
        EditText heureDebut = findViewById(R.id.editDateDebut);
        EditText heureFin = findViewById(R.id.editDateFin);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date deb = sdf.parse(String.valueOf(heureDebut.getText()));
            Date fin = sdf.parse(String.valueOf(heureFin.getText()));
            long diff = deb.getTime() - fin.getTime();;
            resultat.setPlageHoraire(String.valueOf(diff));
        }
        catch (ParseException ex) {
            Log.e("Alert", ex.getMessage(),ex);
        }


        resultat.setHeureDebut(new Date());
        resultat.setHeureFin(new Date());
        resultat.setSelection(true);


        return resultat;
    }

    private void showTimeDialog(int editText) {

        final EditText date = findViewById(editText);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                date.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

}
