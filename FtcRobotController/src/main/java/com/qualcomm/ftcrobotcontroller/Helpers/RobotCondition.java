package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.robotcore.util.Range;

/**
 * Created by я on 20.05.2016.
 */
public class RobotCondition {
    public BasketController.BasketPositionX xPosition;
    public BasketController.BasketPositionY yPosition;
    public HandController.HandPosition handPosition;
    public double
            rightValue,
            leftValue,
            climbValue,
            clawValue,
            metlaValue,
            jostleValue,
            hookVAlue,
            doorValue,
            x_offset;
    public boolean
            lightOn,
            isDoorLocked,
            isDoorOpened,
            signalOn;
}
