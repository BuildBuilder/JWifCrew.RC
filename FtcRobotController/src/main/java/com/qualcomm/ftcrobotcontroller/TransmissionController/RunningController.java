package com.qualcomm.ftcrobotcontroller.TransmissionController;

/**
 * Created by я on 20.05.2016.
 */
public abstract class RunningController {
    protected Transmission transmission;
    public abstract void RunTo(int target);
}
