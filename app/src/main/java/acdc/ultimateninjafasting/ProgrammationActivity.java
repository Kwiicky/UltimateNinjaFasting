package acdc.ultimateninjafasting;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.ProgrammationAdapter;
import bean.Programmation;
import dao.ProgrammationDao;

public class ProgrammationActivity extends AppCompatActivity {

    private static ProgrammationAdapter adapter ;
    public static final int ADD_REQUEST_CODE = 101;

    private ProgrammationDao progDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progDao = new ProgrammationDao(this);
        setContentView(R.layout.activity_programmation);
        initViews(savedInstanceState);
    }

    /**
     * Initialisation de la vue
     * @param savedInstanceState
     */
    private void initViews(Bundle savedInstanceState) {
        ListView lv = findViewById(R.id.listProgramme);
        registerForContextMenu(lv);
        ArrayList<Programmation> data = initData();
        adapter = new ProgrammationAdapter(this, data);
        lv.setAdapter((ListAdapter) adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Programmation selectedItem = adapter.getItem(position);
                Intent details = new Intent(ProgrammationActivity.this, ProgrammationDetailsActivity.class);
                details.putExtra(ProgrammationDetailsActivity.CURRENT_PROGRAMMATION,selectedItem.getId());
                details.putExtra(ProgrammationDetailsActivity.EDIT_MODE,false);
                startActivity(details);
            }
        });
//        refreshNbContacts();
    }

    /**
     * Initialisation des données
     * @return
     */
    private ArrayList<Programmation> initData() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://ultimate.ninja.fasting/programmation")
                ,new String[]{"_id","plage_horaire","heure_debut","heure_fin","selection"}
                ,null
                ,null
                , null);
        ArrayList<Programmation> resultat = new ArrayList<>();
        if(cursor.moveToFirst()){
            while (cursor.moveToNext()){
                Programmation prog = new Programmation();
                prog.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                prog.setPlageHoraire(cursor.getString(cursor.getColumnIndex("plage_horaire")));

            }
        }


        return resultat;
    }

    // Méthode de clique sur les boutons
    public void clickEvent(View v){
        switch (v.getId()){
            case R.id.btnAddProg :
                creationProgramme();
                break;
        }
    }

    private void creationProgramme() {
        Intent addIntent = new Intent(this, ProgrammationDetailsActivity.class);
        startActivityForResult(addIntent, ADD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case ADD_REQUEST_CODE :
                    Programmation c = (Programmation)data.getSerializableExtra(ProgrammationDetailsActivity.CURRENT_PROGRAMMATION);
                    progDao.create(c);
                    adapter.clear();
                    adapter.addAll(initData());
                    break;

            }
        }
    }

}
