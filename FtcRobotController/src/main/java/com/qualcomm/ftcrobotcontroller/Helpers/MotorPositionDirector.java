package com.qualcomm.ftcrobotcontroller.Helpers;

import android.util.Log;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by я on 26.04.2016.
 */
public class MotorPositionDirector {
    private boolean isLocked;
    private final static double DEAD_TIME = 0.5;
    private ElapsedTime motorPositionTimer;
    private int lastPosition;
    private final int
            DABUDY_DABUDAY = 15;
    private final double
            DEFAULT_SPEED  = 0.8;
    private  DcMotor motor;
    private int target;
    protected double speed = DEFAULT_SPEED;
    public DcMotor getMotor(){
        return motor;
    }
    public MotorPositionDirector(DcMotor motor){
        motorPositionTimer = new ElapsedTime();
        this.motor = motor;
        isLocked = false;
        lastPosition = motor.getCurrentPosition();
        target = 0;
        motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }
    public void setPosition(){setPosition(target);}
    public void setPosition(int value){
//        if(isLocked){
//            motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
//            motor.setPower(0);
//            return;
//        }
//        if(motor.getCurrentPosition() != lastPosition || isReached()){
//            lastPosition = motor.getCurrentPosition();
//            motorPositionTimer.reset();
//            motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
//            motor.setPower(0);
//            return;
//        }
//        if(motorPositionTimer.time() > DEAD_TIME){
//            Log.w("Logger", "Motor Was Locked");
//            isLocked = true;
//        }
        target = value;
        if(isReached()) {
            motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            motor.setPower(0);
        }
        else {

            motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
            motor.setTargetPosition(value);
        }
    }
    public void setPositionWithTracing(int value){
        while (!isReached() && !isLocked)
            setPosition(value);
    }
    public void unLock(){isLocked = false;}
    public boolean isReached(){
        return Math.abs(motor.getCurrentPosition() - target) <= DABUDY_DABUDAY;
    }
}
