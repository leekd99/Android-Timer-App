package edu.temple.mytimer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LapAdapter extends ArrayAdapter{

    private Context myContext;
    private int lap_view;

    public LapAdapter(@NonNull Context context, int resource, ArrayList<String> objects){
        super(context, resource, objects);
        this.myContext = context;
        this.lap_view = resource;
    }//end constructor

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String index = Integer.toString(position + 1);
        String time = getItem(position).toString();

        LayoutInflater inflater = LayoutInflater.from(myContext);
        convertView = inflater.inflate(lap_view,parent,false);

        //get TextView
        TextView indexDisplay = (TextView) convertView.findViewById(R.id.indexDisplay);
        TextView lapTimeDisplay = (TextView) convertView.findViewById(R.id.lapTimeDisplay);

        indexDisplay.setText(index);
        lapTimeDisplay.setText(time);

        return convertView;
    }//getView

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }//end getItem

}//end LapAdaptor
