package com.example.worldclocktest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{


    ArrayList<City> selected_cities = null;
    ArrayList<City> cities= null;
    ArrayList<CityNames> cityNames;
    RecyclerView Rview =null;
    CitySelectedListAdapter adapter =null;
    final int REQUEST_CODE = 1;
    ICityDao dao;
    TimezoneService timezoneService =null;
    boolean bound =false;

    private ServiceConnection connection=new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timezoneService = ((TimezoneService.LocalBinder) service).getService();
            Log.e("Serviceconnected","ServiceConnected");
            bound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound=false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new CItyDao(this);
        selected_cities = new ArrayList<City>();
        cities = new ArrayList<City>();

        showMessage("Created");
        Intent intent=new Intent(this,TimezoneService.class);
        startService(intent);
        bindService(intent,connection,Context.BIND_AUTO_CREATE);

        Thread thread = new Thread()
        {
            @Override
            public void run() {
                while(!isInterrupted())
                {
                    try {

                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for(int i =0;i<selected_cities.size();i++)
                                {
                                    selected_cities.get(i).updatetime();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();

        //create_City_list();
        try {
            loadCities();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




    }

    @Override
    protected void onStart() {
        super.onStart();
        showMessage("Started");

    }

    public void onResume()
    {
            super.onResume();
            showMessage("Resumed");
            City temp = new City("temp","temp",dao);
            selected_cities = temp.load(dao);
        if (selected_cities.size() != 0) {
            CreateListView();
        }

        for (int i = 0; i < selected_cities.size(); i++) {
            load_checkbox(selected_cities.get(i).getName());

        }

    }



    public void LoadCities(ArrayList<CityNames> cityNames)
    {
        for(int i =0;i<cityNames.size();i++)
        {
            String cityName = cityNames.get(i).cityname;
            String zoneName = cityNames.get(i).zoneName;
            City city = new City(cityName,zoneName,dao);
            cities.add(city);
        }
    }



    void load_checkbox(String city_name)
    {
        boolean found = false;
         for( int i =0;i<cities.size() && found == false;i++)
         {
             if(cities.get(i).getName().equals(city_name)==true)
             {
                 cities.get(i).setImportant(true);
                 found = true;
             }

         }
    }


    private void create_City_list() {
        cities = new ArrayList<City>();
        City city1 = new City("Singapore","Asia/Singapore",dao);
        City city2 = new City("Karachi","Asia/Karachi",dao);
        City city3 = new City("Muscat","Asia/Muscat",dao);
        City city4 = new City("NewYork","America/NewYork",dao);
        City city5 = new City("Istanbul","Asia/Istanbul",dao);
        City city6 = new City("LogAngeles","America/Los_Angeles",dao);
        City city7= new City("San Francisco","America/San_Francisco",dao);
        City city8 = new City("London","Europe/London",dao);
        City city9 = new City("Victoria","Australia/Victoria",dao);
        City city10 = new City("Delhi","Asia/Delhi",dao);
        City city11 = new City("Shanghai","Asia/Shanghai",dao);
        City city12 = new City("Toronto","Canada/Toronto",dao);
        City city13 = new City("Yukon","Canada/Yukon",dao);
        City city14 = new City("Sydney","Australia/Sydney",dao);
        City city15 = new City("Mexico","America/Mexico_City",dao);
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
    private void CreateListView()
    {

        Rview = (RecyclerView) findViewById(R.id.view_list1);
        Rview.setLayoutManager(new LinearLayoutManager(this));

        adapter= new CitySelectedListAdapter(selected_cities);
        Rview.setAdapter(adapter);
    }



    private void showMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
    public void buttonClick(View v)
    {
        if (v.getId() == R.id.button_list) {

            list_cities();

        }
    }
    public void list_cities()
    {


        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("list",cities);
        startActivityForResult(intent,REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
    int Already_selected(City city)
    {
        int ind =-1;
        for (int i=0;i< selected_cities.size()&& ind == -1;i++)
        {
            if (selected_cities.get(i).getName().equals( city .getName()) == true)
            {
                ind = i;
            }
        }
        return ind;
    }

    private void addselectedcities() {

        //selected_cities.clear();
        for(int i=0;i<cities.size();i++) {
            int ind = Already_selected(cities.get(i));
            if(cities.get(i).isImportant() == true && ind == -1) {

                selected_cities.add(cities.get(i));
                cities.get(i).save(dao);

            }
            else if(cities.get(i).isImportant() == false && ind !=-1)
            {
                deleteCity(ind);
            }
        }


    }



    void deleteCity(int id)
    {
        showMessage("deleted");
        adapter.update_list(id,dao);
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
    void loadCities() throws MalformedURLException {
        URL url = new URL("http://api.timezonedb.com/v2.1/list-time-zone?key=P8B5V3KL22GA&format=json");
        new LoadCitiesTask().execute(url);
    }

    public class LoadCitiesTask extends AsyncTask<URL,String,String>
    {

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection connection = null;
            BufferedReader reader;
            Log.d("connecttimezone", "connectedwithtimezonedb");

            try {
                URL url = new URL("http://api.timezonedb.com/v2.1/list-time-zone?key=P8B5V3KL22GA&format=json");


                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String content = buffer.toString();
                return content;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String Content) {
            super.onPostExecute(Content);
            if (!Content.isEmpty()) {
                try {
                    JSONObject jsonObject = new JSONObject(Content);
                    JSONArray Zones = jsonObject.getJSONArray("zones");

                    for (int i = 0; i < Zones.length(); i++) {
                        JSONObject zone = Zones.getJSONObject(i);
                        String countryCode = zone.getString("countryCode");
                        String countryName = zone.getString("countryName");
                        String zoneName = zone.getString("zoneName");
                        int gmtOffset = zone.getInt("gmtOffset");
                        int timestamp = zone.getInt("timestamp");
                        zoneName = zoneName.replace("\\", "");
                        String cityname= zoneName.split("/")[1];
                        City city = new City(cityname,zoneName,dao);
                        cities.add(city);
                    }

                }
                catch(JSONException j)
                {
                 j.printStackTrace();
                }
            }

        }
    };
}