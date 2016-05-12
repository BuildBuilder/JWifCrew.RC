package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by я on 26.04.2016.
 */
public class HandController extends MotorPositionDirector {
    private final double HAND_SPEED = 0.2;
    public HandController(DcMotor motor) {
        super(motor);
        speed = HAND_SPEED;
    }

    public void setPosition(HandPosition position){
        if(position == HandPosition.Current)
            setPosition();
        else
            setPosition(position.getValue());
    }
    public enum HandPosition {
        Higher,
        Lower,
        Current;
        private final int
                HIGHIR_POSITION_VALUE = 856,
                LOWER_POSITION_VALUE  = 0  ;
        protected int getValue(){
            return (this == Higher) ? HIGHIR_POSITION_VALUE : LOWER_POSITION_VALUE;
        }
    }
}
