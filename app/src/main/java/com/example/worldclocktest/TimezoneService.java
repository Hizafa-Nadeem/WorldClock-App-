package com.example.worldclocktest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


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

    public void connectTimezoneDb()
    {
        Log.d("Timezone db connection ","TimezoneDb");
    }


}
