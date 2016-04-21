package com.qualcomm.ftcrobotcontroller;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.LinkedList;

/**
 * Created by Denis on 03.04.2016.
 */
public class GyroReader {
    private static final int DEFAUlT_DEAD_VALUE = 3;
    private static final int DEFAUlT_SCALE_VALUE = 602;
//hello
    private boolean readerFlag;
    private double heading;
    private double deadZoneValue;
    private double scale = DEFAUlT_SCALE_VALUE;
    private GyroSensor gyroSensor;
    private Thread reader;

    public GyroReader(GyroSensor gyroSensor, double deadZoneValue){
        if(gyroSensor == null)
            throw new  NullPointerException("Sensor not init");
        this.gyroSensor = gyroSensor;
        this.deadZoneValue = deadZoneValue;
        resetHeading();
        StartReading();
    }
    public GyroReader(GyroSensor gyroSensor){
        this(gyroSensor,DEFAUlT_DEAD_VALUE);
    }
    public double getHeading(){
        return heading;
    }
    public void Calibration(){
        scale = gyroSensor.getRotation();
    }

    public void ExtendedCalibration(double duration){
        try {
            reader.join((long)duration);
        }
        catch (Exception e) {
            return;
        }

        ElapsedTime timer = new ElapsedTime();
        LinkedList<Integer> list = new LinkedList<Integer>();

        while (timer.time() < duration && list.size() < 1500){
            list.add(new Integer((int)gyroSensor.getRotation()));

            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) {}
        }
        int sum = 0;
        int minValue = 100000000;
        int maxValue = 0;
        for(int i:list) {
            sum += i;
            minValue = Math.min(i, minValue);
            maxValue = Math.max(i,maxValue);
        }

        scale = Math.round((double) sum / list.size());
        deadZoneValue = Math.max(Math.abs(scale -  minValue),Math.abs(scale -  maxValue));
    }

    public void StartReading(){
        if(reader != null && reader.isAlive()) return;
        if(readerFlag) return;
        readerFlag = true;

        reader = new Thread(new Runnable() {
            @Override
            public void run() {
                ElapsedTime duration = new ElapsedTime();
                while (readerFlag){
                    double readValue = gyroSensor.getRotation() - scale;
                    double shift = Math.abs(readValue) >= deadZoneValue ?
                                   readValue * duration.time() :
                                   0;
                    heading += shift;
                    duration.reset();
                }
            }
        });
        reader.start();
    }

    public void resetHeading(){
        heading = 0;
    }
    public void StopReading(){
        if(!reader.isAlive()) return;
        if(!readerFlag) return;
        readerFlag = false;
    }
    public double AbsoluteRotation(){
        return heading % 360.0;
    }
}
