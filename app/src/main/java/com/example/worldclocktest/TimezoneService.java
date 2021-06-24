package com.example.worldclocktest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

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


public class TimezoneService extends Service {

    private final IBinder binder = new LocalBinder();
    //ZoneDao Zdao;

    @Override
    public void onCreate(){
        super.onCreate();
        //Zdao=new ZoneDao(this);
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("ServiceStarted","ServiceonStartCommand");
        Thread thread=new Thread(new Runnable(){
            public void run(){
                //ConenctTimezoneDb();
            }
        });
        thread.start();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("onBind","ONBIND");
        return binder;
    }
    public class LocalBinder extends Binder
    {
        public TimezoneService getService()
        {
            return TimezoneService.this;
        }
    };

    public void connectTimezoneDb() throws IOException {
        Log.e("Timezone db connection ", "TimezoneDb");

        HttpURLConnection connection = null;
        BufferedReader reader;
        Log.d("connecttimezone", "connectedwithtimezonedb");

        try {
            URL url = new URL("http://api.timezonedb.com/v2.1/list-time-zone?key=P8B5V3KL22GA&format=xml&country=US");


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
            parse(content);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();

        }
    }
    public void parse (String content) throws JSONException {
        if (!content.isEmpty()) {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray Zones = jsonObject.getJSONArray("zones");

            for (int i = 0; i < Zones.length(); i++) {
                JSONObject zone = Zones.getJSONObject(i);
                String countryCode = zone.getString("countryCode");
                String countryName = zone.getString("countryName");
                String zoneName = zone.getString("zoneName");
                int gmtOffset = zone.getInt("gmtOffset");
                int timestamp = zone.getInt("timestamp");
                zoneName = zoneName.replace("\\", "");
                //zoneModel zoneObj = new zoneModel(countryCode, countryName, zoneName, gmtOffset, timestamp);
                //zoneObj.save(Zdao);
            }
        }


    }



}
