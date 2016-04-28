package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.ftcrobotcontroller.Helpers.GyroReader;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by я on 26.04.2016.
 */
public abstract class TransmissionController {
    protected Transmission transmission;
    protected TransmissionController(){}

    public TransmissionController getInstance(DcMotor Left, DcMotor Right, boolean isEncoderControlled){
        return isEncoderControlled ?
                   new EncoderControlledSystem(new Transmission(Left, Right)):
                   new TimerControlledSystem  (new Transmission(Left, Right));
    }
    public TransmissionController getInstance(DcMotor Left, DcMotor Right){
        return getInstance(Left, Right, true);
    }
    public TransmissionController getInstance(DcMotor Left, DcMotor Right, GyroReader gyroReader){
        return new GyroscopikSystem(new Transmission(Left, Right), gyroReader);
    }
    public abstract void RotateOn(double angle);
    public abstract void RunOn(int value);

}
