package com.example.worldclocktest;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class City implements Serializable {

    private String id;
    private String name;
    private boolean important;
    private TimeZone time;
    String timevalue;

    public City(String name,String time){
        init();
        this.name = name;
        this.important = false;
        //this.time = time

        this.time = TimeZone.getTimeZone(time);
        Date date =  new Date();
        SimpleDateFormat df  = new SimpleDateFormat("hh:mm");
        df.setTimeZone(this.time);
        timevalue = df.format(date);



    }
    private void init() {
        this.id = UUID.randomUUID().toString();
    }
    public String getName() {
        return name;
    }

    public void setImportant(boolean val) {
        this.important = val;
    }
    public boolean isCity(City city) {

        if (this.name.equals(city.name)) // ==?
            return true;
        else
            return false;
    }
    public boolean isImportant() {
        return this.important;
    }
    public String getTime()
    {
        return timevalue;
    }

}
