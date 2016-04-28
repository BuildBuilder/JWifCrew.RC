package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by я on 28.04.2016.
 */
class Transmission {
    private DcMotor left, right;
    public Transmission(DcMotor left, DcMotor right){
        this.left  = left;
        this.right = right;
    }

    public void setMode(DcMotorController.RunMode runMode){
        left.setMode (runMode);
        right.setMode(runMode);
    }

    public void setTargetPosition(int leftTarget, int rightTarget){
        left .setTargetPosition(leftTarget );
        right.setTargetPosition(rightTarget);
    }
    public void setTargetPosition(int target){setTargetPosition(target,target);}

    public void setPower(double leftPower,double rightPower){
        leftPower =  Range.clip(leftPower ,-1,1);
        rightPower = Range.clip(rightPower,-1,1);
        left .setPower(leftPower);
        right.setPower(rightPower);
    }
    public void setPower(double power){setPower(power, power);}

    public DcMotor getLeftMotor() {return left; }
    public DcMotor getRigthMotor(){return right;}
}