package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by я on 26.04.2016.
 */
public abstract class TransmissionController {
    protected TransmissionController(){}
    protected Transmission transmission;

    public TransmissionController getInstance(DcMotor Left, DcMotor Right, boolean isEncoderControlled){
        return isEncoderControlled ?
                   new EncoderControlledSystem(new Transmission(Left, Right)):
                   new TimerControlledSystem  (new Transmission(Left, Right));
    }
    public TransmissionController getInstance(DcMotor Left, DcMotor Right){
        return getInstance(Left, Right, true);
    }
    public abstract void RotateOn(double angle);
    public abstract void RunOn(int value);

}
