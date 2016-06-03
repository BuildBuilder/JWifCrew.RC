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
    private float x, y, z;
    private SensorManager sensorManager;
    private Sensor Accel;
    public AccelSensor(){
        this.sensorManager = DataExchange.INSTANCE.sensorManager;
        if(sensorManager == null){
            Log.e("AcccelError", "npe was catched");
            return;
        }
        Accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                x = event.values[0];
                y = event.values[1];
                z = event.values[2];
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("Logger", "хз что это было, но обработать надо было");
            }
        }, Accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public float getX(){return x; }
    public float getY(){return y; }
    public float getZ(){return z; }

    public Logger.DataReader getReader(AxiesType at){
        final AxiesType _at = at;
        return new Logger.DataReader() {
            @Override
            public String getName() {
                switch (_at){
                    case X:
                        return "Accel_x";
                    case Y:
                        return "Accel_y";
                    case Z:
                        return "Accel_z";
                    default:
                        return "";
                }
            }
            @Override
            public double getValue() {
                switch (_at){
                    case X:
                        return x;
                    case Y:
                        return y;
                    case Z:
                        return z;
                    default:
                        return 0;
                }
            }
        };
    }
    public enum AxiesType{
        X, Y, Z
    }
}