package com.example.worldclocktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.UUID;

public class ListActivity extends AppCompatActivity {

    ArrayList<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMessage("List Activity Created");


        Intent intent = new Intent();

        cities = new ArrayList<City>(); // previous code


        create_City_list();
        create_View();
    }




    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private void create_City_list() {
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

    private void create_View() {
        setContentView(R.layout.activity_list);

        ListView view = (ListView) findViewById(R.id.view_list);
        CityListAdapter adapter= new CityListAdapter(this,R.layout.adapter_view,cities);
        view.setAdapter(adapter);

        Attach_Text_Listener(adapter);
    }

    @Override
    public void onBackPressed() {
        showMessage("Back Pressed");
        Intent intent = new Intent();
        intent.putExtra("list",cities);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }



    private void Attach_Text_Listener(CityListAdapter adapter) {

        EditText text = findViewById(R.id.editText);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showMessage("Text changed");
                adapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}

