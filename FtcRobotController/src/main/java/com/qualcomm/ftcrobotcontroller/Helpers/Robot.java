package com.qualcomm.ftcrobotcontroller.Helpers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by я on 20.05.2016.
 */
public final class Robot {
    public HandController
            hand;
    public BasketController
            basket;
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
    private RobotCondition robotCondition = new RobotCondition();
    public void init(HardwareMap hardwareMap) {
        motorRight = hardwareMap.dcMotor   .get("left"  );
        motorLeft  = hardwareMap.dcMotor   .get("right" );
        motorMetla = hardwareMap.dcMotor   .get("metla" );
        motorCLimb = hardwareMap.dcMotor   .get("climb" );
        motorHand  = hardwareMap.dcMotor   .get("hand"  );
        light	   = hardwareMap.dcMotor   .get("light" );
        hook	   = hardwareMap.servo     .get("hook"  );
        claw       = hardwareMap.servo     .get("claw"  );
        door 	   = hardwareMap.servo     .get("door"  );
        basket_x   = hardwareMap.servo     .get("bx"    );
        basket_y   = hardwareMap.servo     .get("by"    );
        jostle     = hardwareMap.servo     .get("jostle");
        hook       . setDirection(Servo.Direction.REVERSE  );
        motorLeft  . setDirection(DcMotor.Direction.REVERSE);
        motorHand  . setDirection(DcMotor.Direction.REVERSE);
        hand       = new HandController(motorHand);
        gyro       = new GyroReader(hardwareMap.gyroSensor.get("gyro" ));
        gyro       . Calibration();
        basket     = new BasketController(basket_x, basket_y);
    }
    public void LoggerInit(){
        logger.Add(MotorAdapter("Right", motorRight));
        logger.Add(MotorAdapter("Left ", motorLeft ));
        logger.Add(MotorAdapter("Metla", motorMetla));
        logger.Add(MotorAdapter("Climb", motorCLimb));
        logger.Add(MotorAdapter("Hand ", motorHand ));
        logger.Add(gyro.getDataReader());
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
        motorMetla.setPower(robotCondition.metlaValue     );
        motorCLimb.setPower(robotCondition.climbValue     );
        motorRight.setPower(robotCondition.rightValue     );
        motorLeft .setPower(robotCondition.leftValue      );
        light     .setPower(robotCondition.lightOn ? 1 : 0);
        hand      .setPosition(robotCondition.handPosition);
        claw      .setPosition(robotCondition.clawValue   );
        jostle    .setPosition(robotCondition.jostleValue );
        door      .setPosition(robotCondition.doorValue   );
        hook      .setPosition(robotCondition.hookVAlue   );
        basket    .setPosition(robotCondition.xPosition,
                               robotCondition.yPosition,
                               robotCondition.x_offset);
        logger.review();
    }
}
