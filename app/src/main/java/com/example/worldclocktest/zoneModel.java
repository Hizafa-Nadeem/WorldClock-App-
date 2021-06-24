package com.example.worldclocktest;

import java.text.SimpleDateFormat;
import java.util.Hashtable;


public class zoneModel {
        String countryCode;
        String countryName;
        String zoneName;
        int gmtOffset;
        int timestamp;
        ZoneDao Zdao;

    public zoneModel(String countryCode,String countryName,String zoneName,int gmtOffset,int timestamp){
        this.countryCode=countryCode;
        this.countryName=countryName;
        this.zoneName=zoneName;
        this.gmtOffset=gmtOffset;
        this.timestamp=timestamp;
    }
    public void save(ZoneDao dao)
    {
        if(this.Zdao==null)
        {
            this.Zdao=dao;
        }
        if(dao!=null){
            Hashtable<String,String>row=new Hashtable<String,String>();

            row.put("countryCode",countryCode);
            row.put("countryName",countryName);
            row.put("zoneName",zoneName);//TodoLearnthisaswell
            row.put("gmtOffset",String.valueOf(gmtOffset));
            row.put("timestamp",String.valueOf(timestamp));
            dao.save(row);
        }
    }

}
