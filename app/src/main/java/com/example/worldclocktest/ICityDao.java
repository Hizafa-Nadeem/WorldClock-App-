package com.example.worldclocktest;

import java.util.ArrayList;
import java.util.Hashtable;

public interface ICityDao {

    public void save(Hashtable<String,String> attributes);
    public void save(ArrayList<Hashtable<String,String>> objects);
    public ArrayList<Hashtable<String,String>> load();
    public Hashtable<String,String> load(String id); // will be used in above function.
    public void delete(String city_name);


}
