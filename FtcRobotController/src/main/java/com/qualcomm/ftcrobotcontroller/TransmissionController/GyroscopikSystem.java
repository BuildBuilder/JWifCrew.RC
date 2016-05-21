package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.ftcrobotcontroller.Helpers.GyroReader;
import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by Denis on 28.04.2016.
 */
final class GyroscopikSystem{
    private final static double DABUDI_DABUDAY = 3;
    private final static double SPEED_CORRECTION = 10;

    public static final class RotationController extends com.qualcomm.ftcrobotcontroller.TransmissionController.RotationController{
        private GyroReader gyroReader;
        public RotationController(Transmission transmission, GyroReader gyroReader){
            this.transmission = transmission;
            this.gyroReader = gyroReader;
        }

        @Override
        public void RotateTo(double target) {
            gyroReader.resetHeading();
            transmission.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            double path;
            while(Math.abs(path = gyroReader.getHeading() - target) > DABUDI_DABUDAY)
                transmission.setPower(path * SPEED_CORRECTION);
            transmission.setPower(0);
        }
    }
}
