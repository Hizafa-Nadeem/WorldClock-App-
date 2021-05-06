package com.example.worldclocktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    ArrayList<City> selected_cities;
    ArrayList<City> cities;
    RecyclerView Rview;
    CitySelectedListAdapter adapter;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_City_list();

        selected_cities = new ArrayList<City>();
        showMessage("Created");

    }
    private void create_City_list() {
        cities = new ArrayList<City>();
        City city1 = new City("Singapore","Asia/Singapore");
        City city2 = new City("Karachi","Asia/Karachi");
        City city3 = new City("Muscat","Asia/Muscat");
        City city4 = new City("NewYork","America/NewYork");
        City city5 = new City("Istanbul","Asia/Istanbul");
        City city6 = new City("LogAngeles","America/Los_Angeles");
        City city7= new City("San Francisco","America/San_Francisco");
        City city8 = new City("London","Europe/London");
        City city9 = new City("Victoria","Australia/Victoria");
        City city10 = new City("Delhi","Asia/Delhi");
        City city11 = new City("Shanghai","Asia/Shanghai");
        City city12 = new City("Toronto","Canada/Toronto");
        City city13 = new City("Yukon","Canada/Yukon");
        City city14 = new City("Sydney","Australia/Sydney");
        City city15 = new City("Mexico","America/Mexico_City");
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);
        cities.add(city4);
        cities.add(city5);
        cities.add(city6);
        cities.add(city7);
        cities.add(city8);
        cities.add(city9);
        cities.add(city10);
        cities.add(city11);
        cities.add(city12);
        cities.add(city13);
        cities.add(city14);
        cities.add(city15);

    }


    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
    public void buttonClick(View v)
    {
        if (v.getId() == R.id.button_list) {
            showMessage("buttonClicked");
            list_cities();
        }
    }
    public void list_cities()
    {

        showMessage("city_list");
        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("list",cities);
        startActivityForResult(intent,REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        showMessage("Activity Result");
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                cities = (ArrayList<City>) data.getSerializableExtra("list");

                if (cities.size()!= 0) {
                    showMessage("Selected cities");
                    addselectedcities();
                    CreateListView();
                }
            }else {
                showMessage("Result Code Problem");
            }
        }
        else {
            showMessage("Request Code Problem");
        }

    }

    private void addselectedcities() {

        selected_cities.clear();
        for(int i=0;i<cities.size();i++) {
            if(cities.get(i).isImportant() == true) {

                selected_cities.add(cities.get(i));

            }
        }

    }


    private void CreateListView()
    {
        Rview = (RecyclerView) findViewById(R.id.view_list1);
        Rview.setLayoutManager(new LinearLayoutManager(this));

        adapter= new CitySelectedListAdapter(selected_cities);
        Rview.setAdapter(adapter);
    }

    void deleteCity(int id)
    {
        showMessage("deleted");
        adapter.update_list(id);
    }






    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {

            case 1:
                deleteCity(item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }



}