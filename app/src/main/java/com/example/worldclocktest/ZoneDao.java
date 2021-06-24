package com.example.worldclocktest;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class ZoneDao implements  IZoneDao{

    private Context context;
    SQLiteDatabase db;
//privateStringName="Name";

    public ZoneDao(Context ctx){
        context=ctx;
    }
    @Override
    public void save(Hashtable<String,String> attributes)
    {
        DbHelper dbHelper=new DbHelper(context);

        db=dbHelper.getWritableDatabase();

        ContentValues content=new ContentValues();
        Enumeration<String> keys=attributes.keys();
        while(keys.hasMoreElements()){
            String key=keys.nextElement();
            content.put(key,attributes.get(key));
        }
        db.insert("Zone",null,content);
    }



    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {
        for(Hashtable<String,String>obj:objects){
            save(obj);
        }
    }





}
