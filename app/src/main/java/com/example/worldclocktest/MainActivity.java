package com.example.worldclocktest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    ArrayList<City> selected_cities;
    ArrayList<City> cities;
    final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cities = new ArrayList<City>();
        selected_cities = new ArrayList<City>();
        showMessage("Created");
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
    boolean Already_selected(City city )
    {
        boolean found = false;
        for(int i=0;i<selected_cities.size() && found == false;i++)
        {
            if (selected_cities.get(i).isCity(city) == true ) {
                found = true;
            }
        }
        return found;
    }
    private void addselectedcities() {
        for(int i=0;i<cities.size();i++) {
            if(cities.get(i).isImportant() == true && Already_selected(cities.get(i)) == false) {

                selected_cities.add(cities.get(i));

            }
        }
    }

    private void CreateListView()
    {
        ListView view = (ListView) findViewById(R.id.view_list1);
        CitySelectedListAdapter adapter= new CitySelectedListAdapter(this,R.layout.adapter_selected_list,selected_cities);
        view.setAdapter(adapter);
    }

}