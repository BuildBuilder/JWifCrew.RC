package com.qualcomm.ftcrobotcontroller.TransmissionController;

/**
 * Created by я on 20.05.2016.
 */
public abstract class RotationController {
    protected Transmission transmission;
    public abstract void RotateTo(double target);
}
