package com.example.worldclocktest;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class City implements Serializable {

    private String id;
    private String name;
    private boolean important;
    private TimeZone time;
    String timevalue;
    String zoneName ;

    private transient ICityDao dao = null;

    public City(String name,String time,ICityDao dao){
        init();
        this.name = name;
        this.important = false;
        this.dao = dao;
        this.zoneName = time;

        this.time = TimeZone.getTimeZone(time);
        Date date =  new Date();
        SimpleDateFormat df  = new SimpleDateFormat("hh:mm:ss");
        df.setTimeZone(this.time);
        timevalue = df.format(date);



    }
    public void updatetime()
    {
        this.time = TimeZone.getTimeZone(this.zoneName);
        Date date =  new Date();
        SimpleDateFormat df  = new SimpleDateFormat("hh:mm:ss");
        df.setTimeZone(this.time);
        this.timevalue = df.format(date);
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

        if (this.name.equals(city.name))
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


    //Database

    public void save(ICityDao dao)
    {
        if (this.dao == null)
        {
            this.dao =dao;
        }
        if(dao != null) {
            Hashtable<String, String> row = new Hashtable<String, String>();
            SimpleDateFormat df = new SimpleDateFormat("hh:mm");

            row.put("id", id);
            row.put("name", name);
            row.put("important", important ? "true" : "false"); //Todo Learn this as well
            row.put("time", timevalue);

            dao.save(row);
        }

    }



    public void load(Hashtable<String,String> row)
    {
        id = row.get("id");
        name = row.get("name");
        timevalue = row.get("time");
        important = Boolean.parseBoolean(row.get("important"));


    }
    public  ArrayList<City> load(ICityDao dao)
    {
       ArrayList<Hashtable<String,String>> rows = dao.load();

       ArrayList<City> cities = new ArrayList<City>();
       for (Hashtable<String,String> row:rows)
       {
           City city = new City("","",dao);
           city.load(row);
           cities.add(city);
       }
    return cities;
    }
    public void delete(String city_name,ICityDao dao)
    {
        dao.delete(city_name);
    }

}
