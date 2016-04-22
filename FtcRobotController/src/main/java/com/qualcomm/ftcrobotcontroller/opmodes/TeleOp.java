package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.DataExchange;
import com.qualcomm.ftcrobotcontroller.GyroReader;
import com.qualcomm.ftcrobotcontroller.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {
	final static DataExchange data = DataExchange.INSTANCE;
	final static double
			BUSKET_Y_FLOOR = 0.3,
			BUSKET_Y_ROOF  = 0,
			BUSKET_X_ZERO  = 0.5,
			DELTA_X        = 0.4,
			BRUSH_SPEED    = 1,
			HAND_SPEED     = 1;
	DcMotor
			motorLeft,
			motorRight,
			motorHand,
			motorMetla,
			motorCLimb,
			light;
	Servo
			basket_x,
			busket_y,
			hook,
			jostle,
			claw,
			door;
	GyroReader
			gyro;

	@Override
	public void init() {
		GyroSensor gyro = hardwareMap.gyroSensor.get("gyro" );
		motorRight = hardwareMap.dcMotor   .get("left"  );
		motorLeft  = hardwareMap.dcMotor   .get("right" );
		motorMetla = hardwareMap.dcMotor   .get("metla" );
		motorCLimb = hardwareMap.dcMotor   .get("climb" );
		motorHand  = hardwareMap.dcMotor   .get("hand"  );
		light	   = hardwareMap.dcMotor   .get("light" );
		hook	   = hardwareMap.servo     .get("hook"  );
		claw       = hardwareMap.servo     .get("claw"  );
		door 	   = hardwareMap.servo     .get("door"  );
		basket_x = hardwareMap.servo     .get("bx"    );
		busket_y   = hardwareMap.servo     .get("by"    );
		jostle     = hardwareMap.servo     .get("jostle");
		hook       . setDirection(  Servo.Direction.REVERSE);
		motorRight . setDirection(DcMotor.Direction.REVERSE);
        this.gyro  = new GyroReader(gyro);
		gyro.calibrate();
 	}

	@Override
	public void loop() {
		double
				rightValue     = gamepad1.left_stick_y,
				leftValue      = scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1)),
		 		handValue      = gamepad2.right_trigger * HAND_SPEED - gamepad2.left_trigger * HAND_SPEED,
				climbValue     = gamepad1.right_trigger - gamepad1.left_trigger,
				clawValue      = gamepad1.right_bumper ? 1 : gamepad1.left_bumper ? 0 : claw.getPosition(),
				metlaValue     = gamepad2.left_bumper ? -BRUSH_SPEED : (gamepad2.right_bumper ? BRUSH_SPEED : 0),
				jostleValue    = gamepad2.a ? 0 : gamepad2.b ? 0.2 : jostle.getPosition(),
				hookVAlue      = gamepad2.y ? 0 : 0.9,
				doorValue      = gamepad1.a ? 1 : (gamepad1.b ? 0 : 0.23),
				basket_y_value = gamepad2.dpad_down ? BUSKET_Y_FLOOR: gamepad2.dpad_up ? BUSKET_Y_ROOF : busket_y.getPosition(),
				basket_x_value = BUSKET_X_ZERO + gamepad2.left_stick_y / 18 +
									(gamepad2.dpad_left ? DELTA_X :
										gamepad2.dpad_right ? -DELTA_X :
											gamepad2.dpad_down ? 0 : basket_x.getPosition());
		boolean
				lightOn        = gamepad1.left_stick_button  ||  gamepad2.left_stick_button,
				signalOn       = gamepad1.right_stick_button || gamepad2.right_stick_button;

		rightValue = scaleInput(Range.clip(rightValue, -1, 1));
		basket_x_value = Range.clip(basket_x_value,0,1);

		if(Math.abs(climbValue) < 0.4)
			climbValue = 0;

		claw      .setPosition(clawValue     );
		jostle    .setPosition(jostleValue   );
        door      .setPosition(doorValue     );
        basket_x  .setPosition(basket_x_value);
        busket_y  .setPosition(basket_y_value);
        hook      .setPosition(hookVAlue     );
		motorHand .setPower(handValue        );
		motorMetla.setPower(metlaValue       );
		motorCLimb.setPower(climbValue       );
		motorRight.setPower(rightValue       );
		motorLeft .setPower(leftValue        );
		light     .setPower(lightOn ? 1 : 0  );

        if(signalOn) SoundPlayer.INSTANCE.play("Sirena");

		telemetry.addData("GyroValue", gyro.getHeading());
	}

	float scaleInput(double dVal)  {
		return (float)Math.pow(dVal,3);
	}
}