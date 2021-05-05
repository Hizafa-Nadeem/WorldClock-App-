package com.example.worldclocktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.UUID;

public class ListActivity extends AppCompatActivity  {

    ArrayList<City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMessage("List Activity Created");


        Intent intent = getIntent();
        //cities = new ArrayList<City>();
        cities = (ArrayList<City>) intent.getSerializableExtra("list");
        create_View();
    }




    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
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

