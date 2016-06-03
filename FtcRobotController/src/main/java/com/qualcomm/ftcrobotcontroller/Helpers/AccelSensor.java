package com.qualcomm.ftcrobotcontroller.Helpers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Кирилл on 03.06.2016.
 */
public class AccelSensor {
    private float mx, my, vz;
    private SensorManager sensorManager;
    private Sensor Accel;
    private static final float NOIS = 2.0f;
    public AccelSensor(SensorManager sensorManager){
        this.sensorManager = sensorManager;
        Accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.i("Loggerx", "x: " + event.values[0]);
                Log.i("Loggery", "y: " + event.values[1]);
                Log.i("Loggerz", "z: " + event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("Logger", "skdfbgldfkhgsldkfhsgldkfhgsld ");
            }
        }, Accel, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
