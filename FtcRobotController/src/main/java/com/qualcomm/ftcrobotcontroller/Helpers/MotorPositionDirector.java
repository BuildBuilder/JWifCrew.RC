package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by я on 26.04.2016.
 */
public class MotorPositionDirector {
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
        this.motor = motor;
        target = 0;
        motor.setMode(DcMotorController.RunMode.RESET_ENCODERS);
    }

    public void setPosition(){setPosition(target);}
    public void setPosition(int value){
        target = value;
        if(Math.abs(motor.getCurrentPosition() - target) <= DABUDY_DABUDAY) {
            motor.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            motor.setPower(0);
        }
        else {
            motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            motor.setPower(speed);
            motor.setTargetPosition(value);
        }
    }
}
