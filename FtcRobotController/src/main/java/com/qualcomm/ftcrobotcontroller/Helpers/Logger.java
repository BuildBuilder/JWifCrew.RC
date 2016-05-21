package com.qualcomm.ftcrobotcontroller.Helpers;

import android.util.Log;

import java.util.Hashtable;

/**
 * Created by я on 20.05.2016.
 */
public class Logger {
    private Hashtable<DataReader, Double> hashtable =
            new Hashtable<DataReader, Double>();
    public static abstract class DataReader{
        public abstract String getName();
        public abstract double getValue();
    }
    public void Add(DataReader dataReader){
        hashtable.put(dataReader, 0.0d);
    }
    public void review(){
        for(DataReader dr: hashtable.keySet() ){
            double val;
            if(hashtable.get(dr) != (val = dr.getValue())){
                hashtable.put(dr, val);
                Log.i("Logger", "review: " + dr.getName() + "\n\tvalue: " + val);
            }
        }
    }
}
