package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by я on 28.04.2016.
 */
class EncoderControlledSystem {
    private static final double DEGREE_PER_VALUE = 1;
    private static final int DABUDI_DABUDAY = 3;
    private  EncoderControlledSystem(){}
    public static final class RunningController extends com.qualcomm.ftcrobotcontroller.TransmissionController.RunningController{
        public RunningController(Transmission transmission){this.transmission = transmission;}
        @Override
        public void RunTo(int target) {
            transmission.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            transmission.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            transmission.setPower(1);
            while ((transmission.getLeftMotor().getCurrentPosition() - target * DEGREE_PER_VALUE) >= DABUDI_DABUDAY)
                transmission.setTargetPosition(target,-target);
            transmission.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            transmission.setPower(0);
        }
    }
    public final class RotationController extends com.qualcomm.ftcrobotcontroller.TransmissionController.RotationController{
        @Override
        public void RotateTo(double target) {
            transmission.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            transmission.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
            transmission.setPower(1);
            while ((transmission.getLeftMotor().getCurrentPosition() - target) >= DABUDI_DABUDAY)
                transmission.setTargetPosition((int)target);
            transmission.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
            transmission.setPower(0);
        }
    }
}