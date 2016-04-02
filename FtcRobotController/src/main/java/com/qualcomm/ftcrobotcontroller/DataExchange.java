package com.qualcomm.ftcrobotcontroller;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public enum DataExchange {
    INSTANCE;
    public final Map<String, Double> values  = new HashMap<String, Double>();
    public final Map<String, String> strings = new HashMap<String, String>();
    public Context context;

}
