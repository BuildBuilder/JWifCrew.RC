package com.qualcomm.ftcrobotcontroller.LolOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Robo on 28.05.2016.
 */
public class SeroTest  extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.servo.get("servo");
        waitForStart();
        while (true){
            servo.setPosition(0.9);
            Thread.sleep(500);
            servo.setPosition(0);
            Thread.sleep(500);
        }
    }

}
