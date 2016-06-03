package com.qualcomm.ftcrobotcontroller.Helpers;

import android.content.Context;
import android.hardware.SensorManager;

import java.util.HashMap;
import java.util.Map;

public enum DataExchange {
    INSTANCE;
    public final Map<String, Double> values  = new HashMap<String, Double>();
    public final Map<String, String> strings = new HashMap<String, String>();
    public SensorManager sensorManager;
    public Context context;

}
