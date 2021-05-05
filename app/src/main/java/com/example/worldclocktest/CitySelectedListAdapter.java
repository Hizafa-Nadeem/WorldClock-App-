package com.example.worldclocktest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CitySelectedListAdapter  extends ArrayAdapter<City> {

    private ArrayList<City> cities;


    public CitySelectedListAdapter(Context context, int resource, ArrayList<City>cities) {
        super(context, resource, cities);
        this.cities = cities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        City city = getItem(position) ;
        Holder2 holder = new Holder2();

        if(convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.adapter_selected_list,parent,false);



            holder.name = (TextView) convertView.findViewById(R.id.text1);
            holder.time = (TextView) convertView.findViewById(R.id.text2);
            convertView.setTag(holder);


        }
        else {
            holder = (Holder2) convertView.getTag();
        }
        holder.name.setText(city.getName());
        holder.time.setText(city.getTime());

        return convertView;
    }
    private class Holder2 {
        TextView name;
        TextView time;

    }
}
