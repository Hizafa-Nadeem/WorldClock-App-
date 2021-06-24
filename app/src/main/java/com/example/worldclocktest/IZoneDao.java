package com.example.worldclocktest;

import java.util.ArrayList;
import java.util.Hashtable;

public interface IZoneDao {

    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);

}
