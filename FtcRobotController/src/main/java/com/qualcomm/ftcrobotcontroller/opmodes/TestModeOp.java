package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.DataExchange;
import com.qualcomm.ftcrobotcontroller.GyroReader;
import com.qualcomm.ftcrobotcontroller.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TestModeOp extends OpMode {
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
			busket_x,
			busket_y,
			hook,
			door;
	GyroReader
			gyro;

	@Override
	public void init() {
		GyroSensor gyro = hardwareMap.gyroSensor.get("gyro" );
		motorRight = hardwareMap.dcMotor   .get("left" );
		motorLeft  = hardwareMap.dcMotor   .get("right");
		motorMetla = hardwareMap.dcMotor   .get("metla");
		motorCLimb = hardwareMap.dcMotor   .get("climb");
		motorHand  = hardwareMap.dcMotor   .get("hand" );
		light	   = hardwareMap.dcMotor   .get("light");
		hook	   = hardwareMap.servo     .get("hook" );
		door 	   = hardwareMap.servo     .get("door" );
		busket_x   = hardwareMap.servo     .get("bx"   );
		busket_y   = hardwareMap.servo     .get("by"   );
		hook       . setDirection(  Servo.Direction.REVERSE);
		motorRight . setDirection(DcMotor.Direction.REVERSE);
        this.gyro  = new GyroReader(gyro);
 	}

	@Override
	public void loop() {
		double
				rightValue     = scaleInput(Range.clip(gamepad1.left_stick_y, -1, 1)),
				leftValue      = scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1)),
		 		handValue      = gamepad1.right_trigger * HAND_SPEED - gamepad1.left_trigger * HAND_SPEED,
				busket_y_value = gamepad1.x ? BUSKET_Y_FLOOR: gamepad1.y ? BUSKET_Y_ROOF : busket_y.getPosition(),
				climbValue     = gamepad1.dpad_up ? 1 : (gamepad1.dpad_down? -1 : 0),
				metlaValue     = gamepad1.left_bumper ? -BRUSH_SPEED : (gamepad1.right_bumper ? BRUSH_SPEED : 0),
				hookVAlue      = gamepad1.x ? 0 : 0.9,
				busket_x_value = BUSKET_X_ZERO + (gamepad1.dpad_right ? DELTA_X : (gamepad1.dpad_left ? -DELTA_X : 0)),
				doorValue      = gamepad1.a ? 1 : (gamepad1.b ? 0 : 0.23);
		boolean
				lightOn        = gamepad1.left_stick_button,
				signalOn       = gamepad1.right_stick_button;

        door      .setPosition(doorValue     );
        busket_x  .setPosition(busket_x_value);
        busket_y  .setPosition(busket_y_value);
        hook      .setPosition(hookVAlue     );
		motorHand .setPower(handValue        );
		motorMetla.setPower(metlaValue       );
		motorCLimb.setPower(climbValue       );
		motorRight.setPower(rightValue            );
		motorLeft .setPower(leftValue             );
		light     .setPower(lightOn ? 1 : 0  );

        if(signalOn) SoundPlayer.INSTANCE.play("Sirena");

		telemetry.addData("Гирыч", gyro.getHeading());
	}

	float scaleInput(double dVal)  {
		return (float)Math.pow(dVal,3);
	}
}
