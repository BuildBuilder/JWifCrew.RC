package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by я on 28.04.2016.
 */
class TimerControlledSystem extends TransmissionController {
    private final double DEGREE_PER_MILSECOND = 1;
    private final double LENGTH_PER_MILSECOND = 1;
    private final int    COEF                 = 100;
    private double speed = 0.8;
    TimerControlledSystem(Transmission transmission){
        this.transmission = transmission;
    }

    @Override
    public void RotateOn(double target) {
        ElapsedTime timer = new ElapsedTime();
        transmission.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        double distanceLeft;
        while (Math.abs(distanceLeft = timer.time() * DEGREE_PER_MILSECOND - target) > 0 ){
            double _speed = speed * COEF * distanceLeft;
            _speed = Range.clip(_speed,-1,1);
            transmission.setPower(_speed, -_speed);
        }
        transmission.setPower(0);
    }

    @Override
    public void RunOn(int target) {
        ElapsedTime timer = new ElapsedTime();
        transmission.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        double distanceLeft;
        while (Math.abs(distanceLeft = timer.time() * LENGTH_PER_MILSECOND - target) > 0 ){
            double _speed = speed * COEF * distanceLeft;
            _speed = Range.clip(_speed,-1,1);
            transmission.setPower(_speed);
        }
        transmission.setPower(0);
    }

    public void setSpeed(double speed){
        this.speed = Range.clip(speed,-1,1);
    }
}