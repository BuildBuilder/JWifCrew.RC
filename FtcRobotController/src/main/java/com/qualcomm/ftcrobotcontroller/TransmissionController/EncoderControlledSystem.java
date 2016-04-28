package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.robotcore.hardware.DcMotorController;

/**
 * Created by я on 28.04.2016.
 */
class EncoderControlledSystem extends TransmissionController {
    private final double DEGREE_PER_VALUE = 1;
    private final int DABUDI_DABUDAY = 3;

    EncoderControlledSystem(Transmission transmission){
        this.transmission = transmission;
    }

    @Override
    public void RotateOn(double target) {
        transmission.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        transmission.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        transmission.setPower(1);
        while ((transmission.getLeftMotor().getCurrentPosition() - target * DEGREE_PER_VALUE) >= DABUDI_DABUDAY)
            transmission.setTargetPosition((int)target,-(int)target);
        transmission.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        transmission.setPower(0);
    }

    @Override
    public void RunOn(int target) {
        transmission.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        transmission.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        transmission.setPower(1);
        while ((transmission.getLeftMotor().getCurrentPosition() - target * DEGREE_PER_VALUE) >= DABUDI_DABUDAY)
            transmission.setTargetPosition(target);
        transmission.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        transmission.setPower(0);
    }
}