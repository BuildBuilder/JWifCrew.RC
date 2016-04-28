package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.ftcrobotcontroller.Helpers.GyroReader;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Denis on 28.04.2016.
 */
class GyroscopikSystem extends TransmissionController{
    private final double DABUDI_DABUDAY = 3;
    private final double SPEED_CORRECTION = 10;
    private Transmission transmission;
    private GyroReader gyroReader;

    public GyroscopikSystem(Transmission transmission, GyroReader gyroReader){
        this.transmission = transmission;
        this.gyroReader = gyroReader;
    }

    @Override
    public void RotateOn(double angle) {
        gyroReader.resetHeading();
        transmission.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        double path;
        while(Math.abs(path = gyroReader.getHeading() - angle) > DABUDI_DABUDAY)
            transmission.setPower(path * SPEED_CORRECTION);
        transmission.setPower(0);
    }

    @Override
    public void RunOn(int target) {
        transmission.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        transmission.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        transmission.setPower(1);
        while ((transmission.getLeftMotor().getCurrentPosition() - target) >= DABUDI_DABUDAY)
            transmission.setTargetPosition(target);
        transmission.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        transmission.setPower(0);
    }
}
