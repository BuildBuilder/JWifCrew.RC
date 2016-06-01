package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.ftcrobotcontroller.TransmissionController.TransmissionController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by я on 20.05.2016.
 */
public final class Robot {
    public HandController
            hand;

    public BasketController
            basket;
    public OpticalDistanceSensor
            distanceSensor;
    public DcMotor
            motorLeft,
            motorRight,
            motorMetla,
            motorCLimb,
            light;
    private DcMotor
            motorHand;
    public Servo
            hook,
            jostle,
            claw,
            door;
    private Servo
            basket_x,
            basket_y;
    public GyroReader
            gyro;
    private Logger logger = new Logger();
    private TransmissionController transmissionController;
    private RobotCondition robotCondition = new RobotCondition();
    public void init(HardwareMap hardwareMap) {
        motorRight     = hardwareMap.dcMotor              .get("left"  );
        motorLeft      = hardwareMap.dcMotor              .get("right" );
        motorMetla     = hardwareMap.dcMotor              .get("metla" );
        motorCLimb     = hardwareMap.dcMotor              .get("climb" );
        motorHand      = hardwareMap.dcMotor              .get("hand"  );
        light	       = hardwareMap.dcMotor              .get("light" );
        hook	       = hardwareMap.servo                .get("hook"  );
        claw           = hardwareMap.servo                .get("claw"  );
        door 	       = hardwareMap.servo                .get("door"  );
        basket_x       = hardwareMap.servo                .get("bx"    );
        basket_y       = hardwareMap.servo                .get("by"    );
        jostle         = hardwareMap.servo                .get("jostle");
        distanceSensor = hardwareMap.opticalDistanceSensor.get("eopd");
        motorRight     . setDirection(DcMotor.Direction.REVERSE);
        motorHand      . setDirection(DcMotor.Direction.REVERSE);
        hand           = new HandController(motorHand);
        basket         = new BasketController(basket_x, basket_y);
        transmissionController= TransmissionController.getInstance(motorLeft ,motorRight);
        gyro           = new GyroReader(hardwareMap.gyroSensor.get("gyro" ));
        gyro           . Calibration();
    }
    public void LoggerInit(){
        logger.Add(MotorAdapter("Right", motorRight));
        logger.Add(MotorAdapter("Left ", motorLeft ));
        logger.Add(MotorAdapter("Metla", motorMetla));
        logger.Add(MotorAdapter("Climb", motorCLimb));
        logger.Add(MotorAdapter("Hand ", motorHand ));
        logger.Add(MotorAdapter("Light", light     ));
        logger.Add(gyro.getDataReader());
        logger.Add(new Logger.DataReader() {
            @Override
            public String getName() {
                return "EOPD";
            }

            @Override
            public double getValue() {
                return distanceSensor.getLightDetectedRaw();
            }
        });
    }
    private Logger.DataReader MotorAdapter(final String name, final DcMotor motor){
        return new Logger.DataReader() {
            DcMotor source = motor;
            String _name ="Motor " + name + " Encoder";
            @Override
            public String getName() {
                return _name;
            }
            @Override
            public double getValue() {
                return source.getCurrentPosition();
            }
        };
    }
    public RobotCondition getRobotCondition(){return robotCondition;}
    public void setCondition(){
        motorMetla.setPower(Range.clip(robotCondition.metlaValue,-1,1)     );
        motorCLimb.setPower(Range.clip(robotCondition.climbValue,-1,1)     );
        motorRight.setPower(Range.clip(robotCondition.rightValue,-1,1)     );
        motorLeft .setPower(Range.clip(robotCondition.leftValue,-1,1)      );
        light     .setPower(Range.clip(robotCondition.lightOn ? 1 : 0,-1,1));
        hand      .setPosition(robotCondition.handPosition                 );
        claw      .setPosition(Range.clip(robotCondition.clawValue    ,0,1));
        jostle    .setPosition(Range.clip(robotCondition.jostleValue  ,0,1));
        door      .setPosition(robotCondition.isDoorLocked ? 0 :
                               robotCondition.isDoorOpened ? 0.45:
                               Range.clip(robotCondition.doorValue    ,0,1));
        hook      .setPosition(Range.clip(robotCondition.hookVAlue    ,0,1));
        basket    .setPosition(robotCondition.xPosition,
                               robotCondition.yPosition,
                               robotCondition.x_offset);
        logger.review();
    }
    public HandController getHand(){return hand;}
    public TransmissionController getTransmissionController() {
        return transmissionController;
    }
}
