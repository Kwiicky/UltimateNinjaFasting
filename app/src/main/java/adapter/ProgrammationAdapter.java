package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import acdc.ultimateninjafasting.R;
import bean.Programmation;

/**
 * Created by FP13694 on 24/03/2018.
 */

public class ProgrammationAdapter extends ArrayAdapter<Programmation> {

    private ArrayList<Programmation> items;

    public ProgrammationAdapter(@NonNull Context context, @NonNull ArrayList<Programmation> objects) {
        super(context, R.layout.programmation_item_list, objects);
        items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.programmation_item_list, parent,false);
        }
        Programmation currentProgrammation = getItem(position);
        TextView tv = convertView.findViewById(R.id.itemPlageHoraire);
        tv.setText(currentProgrammation.getPlageHoraire().toString());
       // tv = convertView.findViewById(R.id.itemJour);
        tv.setText(currentProgrammation.getSelection().toString());


        return convertView;
    }

        public ArrayList<Programmation> getItems() {
            return items;
        }

        public void set(int index, Programmation item){
            items.set(index,item);
            notifyDataSetChanged();
        }

}
