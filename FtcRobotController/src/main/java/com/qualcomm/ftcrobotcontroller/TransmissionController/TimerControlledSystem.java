package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by я on 28.04.2016.
 */
final class TimerControlledSystem extends TransmissionController {
    private final static double DEGREE_PER_MILSECOND = 1;
    private final static double LENGTH_PER_MILSECOND = 1;
    private final static int    COEF                 = 100;
    private TimerControlledSystem(){}
    public final class RotationController extends com.qualcomm.ftcrobotcontroller.TransmissionController.RotationController{
        private double speed = 0.8;
        RotationController(Transmission transmission){ this.transmission = transmission;}
        @Override
        public void RotateTo(double target) {
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
    }
    public final static class RunningController extends com.qualcomm.ftcrobotcontroller.TransmissionController.RunningController{
        private double speed = 0.8;
        RunningController(Transmission transmission){ this.transmission = transmission;}
        @Override
        public void RunTo(int target) {

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
    }
}