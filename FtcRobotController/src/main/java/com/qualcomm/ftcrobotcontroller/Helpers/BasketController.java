package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by я on 26.04.2016.
 */
public class BasketController {
    private Servo x, y;
    private BasketPositionX xPosition;
    public BasketController(Servo xServo, Servo yServo){
        x = xServo;
        y = yServo;
        xPosition = BasketPositionX.Center;
    }
    public Servo getServo(boolean xAx){return xAx ? x: y; }
    public void setPosition(BasketPositionX position, double compression){
        xPosition = position != BasketPositionX.Current ? position : xPosition;
        if(xPosition == BasketPositionX.Center) compression = 0;
        x.setPosition(Range.clip(xPosition.value() + compression,0,1));
    }
    public void setPosition(BasketPositionY position){
        if(position != BasketPositionY.Current)
            y.setPosition(position.value());
    }
    public void setPosition(BasketPositionX xPosition, BasketPositionY yPosition, double compression){
        setPosition(xPosition, compression);
        setPosition(yPosition);
    }
    public enum BasketPositionX {
        Center,
        Left,
        Right,
        Current;
        public double value(){
            switch (this){
                case Left:
                    return ZERO_VALUE + DELTA_VALUE;
                case Right:
                    return ZERO_VALUE - DELTA_VALUE;
                default:
                    return ZERO_VALUE;
            }
        }
        private final static double
            ZERO_VALUE  = 0.45,
            DELTA_VALUE = 0.4;
    }
    public enum BasketPositionY{
        Higher,
        Lower,
        Current;
        private final double
            FLOOR_VALUE = 0.3,
            ROOF_VALUE  = 0;
        protected double value(){
            return this == Higher ? ROOF_VALUE : FLOOR_VALUE;
        }
    }
}

