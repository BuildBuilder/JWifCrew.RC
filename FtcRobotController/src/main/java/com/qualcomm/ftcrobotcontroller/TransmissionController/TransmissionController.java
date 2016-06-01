package com.qualcomm.ftcrobotcontroller.TransmissionController;

import com.qualcomm.ftcrobotcontroller.Helpers.GyroReader;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by я on 26.04.2016.
 */
public class TransmissionController {
    protected Transmission transmission;
    private RunningController runningController;
    private RotationController rotationController;
    protected TransmissionController(){}
    private TransmissionController(DcMotor Left, DcMotor Right){
        transmission = new Transmission(Left, Right);
    }
    public static TransmissionController getInstance(DcMotor Left, DcMotor Right, boolean isEncoderControlled){
        TransmissionController instance = new TransmissionController(Left,Right);
        instance.runningController = isEncoderControlled ?
                new EncoderControlledSystem.RunningController(instance.transmission) :
                new TimerControlledSystem  .RunningController(instance.transmission);
        return instance;
    }
    public static TransmissionController getInstance(DcMotor Left, DcMotor Right){
        return getInstance(Left, Right, true);
    }
    public static TransmissionController getInstance(DcMotor Left, DcMotor Right, GyroReader gyroReader){
        TransmissionController instance = new TransmissionController(Left,Right);
        instance.ConnectGyroReader(gyroReader);
        return instance;
    }
    public void  ConnectGyroReader(GyroReader gyroReader){
        rotationController = new GyroscopikSystem.RotationController(transmission, gyroReader);
    }
    public void RotateOn(double angle){
       if(rotationController != null)
           rotationController.RotateTo(angle);
    }
    public void RunOn(int value){
        if(runningController != null)
            runningController.RunTo(value);
    }

}
