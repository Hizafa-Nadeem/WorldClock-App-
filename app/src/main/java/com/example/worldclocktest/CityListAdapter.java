package com.example.worldclocktest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;import android.widget.Toast;

public class CityListAdapter extends ArrayAdapter<City> implements Filterable {

    private ArrayList<City> cities;
    private ArrayList<City> filtered_list;
    private Filter filter;

    public CityListAdapter(Context context, int resource, ArrayList<City>cities) {
        super(context, resource, cities);
        this.cities = cities;
        this.filtered_list = cities;
    }
    int getIndex(City city)
    {
        int ind =-1;
        for (int i =0;i< cities.size()&& ind == -1;i++)
        {
            if(cities.get(i).isCity(city)== true)
            {
                ind =i;
            }
        }
        return ind;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,  ViewGroup parent) {
        City city = getItem(position) ;
        Holder holder = new Holder();
        if(convertView == null) {
            LayoutInflater inflator = LayoutInflater.from(getContext());
            convertView = inflator.inflate(R.layout.adapter_view,parent,false);


            holder.box = (CheckBox) convertView.findViewById(R.id.checkBox);

            holder.name = (TextView) convertView.findViewById(R.id.textView);
            holder.time = (TextView) convertView.findViewById(R.id.timeView);
            convertView.setTag(holder);



            holder.box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v.findViewById(R.id.checkBox) ;
                    City citychecked = (City) v.getTag();


                    int index = getIndex(citychecked);
                    cities.get(index).setImportant(cb.isChecked());

                }
            });




        }
        else {
            holder = (Holder) convertView.getTag();
        }
        holder.name.setText(city.getName());
        holder.time.setText(city.getTime());
        holder.box.setTag(city);


        return convertView;
    }
    private class Holder {
        CheckBox box;
        TextView name;
        TextView time;


    }




    @NonNull
    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CityFilter();
        }
        return filter;
    }



    private class CityFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();


            if (constraint != null && constraint.length()!= 0) {
                ArrayList<City> filteredlist = new ArrayList<City>();
                for (int i = 0; i < cities.size(); i++) {
                    if (cities.get(i).getName().contains(constraint)) {
                        filteredlist.add(cities.get(i));
                        showMessage(cities.get(i).getName() + "added");
                    }
                }

                results.values = filteredlist;
                results.count = filteredlist.size();
            }
            else{
                results.count = cities.size();
                results.values = cities;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_list = (ArrayList<City>) results.values;

            notifyDataSetChanged();
        }

    }
    public void showMessage(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
    @Override
    public int getCount() {
        return filtered_list.size();
    }

    public City getItem(int position){
        return filtered_list.get(position);
    }

}
