package com.example.worldclocktest;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class CitySelectedListAdapter extends RecyclerView.Adapter<CitySelectedListAdapter.ViewHolder>   {

   private ArrayList<City> selected_cities;

    public CitySelectedListAdapter(ArrayList<City> selected_cities) {
        this.selected_cities = selected_cities;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_selected_cities, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getCity_name().setText(selected_cities.get(position).getName());
        holder.getCity_time().setText(selected_cities.get(position).getTime());

    }


    @Override
    public int getItemCount() {
        return selected_cities.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView city_name;
        TextView city_time;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            city_name = (TextView) itemView.findViewById(R.id.text1);
            city_time = (TextView) itemView.findViewById(R.id.text2);
            layout =(LinearLayout)itemView.findViewById(R.id.layout_id);
            layout.setOnCreateContextMenuListener(this);

        }



        public TextView getCity_name() {
            return city_name;
        }

        public TextView getCity_time() {
            return city_time;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add(getAdapterPosition(),1,0,"delete");
        }

    }
    public void update_list(int ind)
    {
        selected_cities.remove(ind);
        notifyDataSetChanged();
        //selected_cities.get(i).delete();
    }
};