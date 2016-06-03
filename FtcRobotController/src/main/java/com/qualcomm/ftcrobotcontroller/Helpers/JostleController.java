package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Кирилл on 03.06.2016.
 */
public class JostleController {
    public static final float
            FIRST_POSITION  = 0,
            SECOND_POSITION = 0.5f;
    Servo _servo;
    private JostleState _js;
    private boolean wasSateCHanged = false;
    public JostleController(Servo servo){
        _servo = servo;
    }
    public void setPositionChanging(boolean ps){
        if(!ps){
            wasSateCHanged = false;
        }
        else if(!wasSateCHanged){
            wasSateCHanged = true;
            setPosition(_js.getAnother());
        }
    }
    private void  setPosition(JostleState jostleState){
        switch (jostleState){
            case first:
                _servo.setPosition(FIRST_POSITION);
                break;
            case second:
                _servo.setPosition(SECOND_POSITION);
                break;
        }
        _js = jostleState;
    }
    private enum JostleState{
        first,second;
        public JostleState getAnother(){
            switch (this){
                case first:
                    return second;
                case second:
                    return first;
                default:
                    return null;
            }
        }
    }
}
