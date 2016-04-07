package com.qualcomm.ftcrobotcontroller;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.LinkedList;

/**
 * Created by Denis on 03.04.2016.
 */
public class GyroReader {
    private static final int DEFAUlT_DEAD_VALUE = 2;
    private static final int DEFAUlT_SCALE_VALUE = 602;

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
        for(int i:list)
            sum += i;

        scale = Math.round((double) sum / list.size());
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
                }
            }
        });
        reader.start();
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
