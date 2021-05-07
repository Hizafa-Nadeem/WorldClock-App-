package com.example.worldclocktest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CItyDao implements ICityDao {

    private Context context;
    SQLiteDatabase db;

    public CItyDao(Context ctx){
        context = ctx;
    }
    @Override
    public void save(Hashtable<String, String> attributes) {
        DbHelper dbHelper= new DbHelper(context);

        db = dbHelper.getWritableDatabase();

        ContentValues content = new ContentValues();
        Enumeration<String> keys = attributes.keys();
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            content.put(key,attributes.get(key));
        }
        db.insert("City",null,content);
    }

    @Override
    public void save(ArrayList<Hashtable<String, String>> objects) {

        for(Hashtable<String,String> obj : objects){
            save(obj);
        }

    }

    @Override
    public ArrayList<Hashtable<String, String>> load()
    {
        DbHelper dbHelper= new DbHelper(context);
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM City",null);
        ArrayList<Hashtable<String, String>> objects = new ArrayList<Hashtable<String, String>>();
        while(cursor.moveToNext())
        {
            String [] columns = cursor.getColumnNames();
            Hashtable<String, String> obj = new Hashtable<String,String>();
            for(String col : columns) {
                obj.put(col.toLowerCase(), cursor.getString(cursor.getColumnIndex(col)));
            }
                objects.add(obj);
        }

            return objects;
    }

    @Override
    public Hashtable<String, String> load(String id) {
        return null;
    }
}
