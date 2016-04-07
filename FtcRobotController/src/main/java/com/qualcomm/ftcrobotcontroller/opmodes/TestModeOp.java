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
	final static float
			BRUSH_SPEED     = 1,
			HAND_SPEED      = 1,
			BUSKET_X_ZERO   = 0.53f,
			BUSKET_X_ANGLE  = 0.44f,
			BUSKET_Y_FROM   = 0,
			BUSKET_Y_TO     = 1;
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
		motorRight . setDirection(DcMotor.Direction.REVERSE);
        this.gyro  = new GyroReader(gyro);
 	}

	@Override
	public void loop() {
		float
				right          = scaleInput(Range.clip(gamepad1.left_stick_y, -1, 1)),
				left     	   = scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1)),
		 		handValue      = gamepad1.right_trigger * HAND_SPEED - gamepad1.left_trigger * HAND_SPEED,
				busket_y_value = gamepad1.y ? BUSKET_Y_FROM : BUSKET_Y_TO,
				climbValue     = gamepad1.dpad_up ? 1 : (gamepad1.dpad_down? -1 : 0),
				metlaValue     = gamepad1.left_bumper ? -BRUSH_SPEED : (gamepad1.right_bumper ? BRUSH_SPEED : 0),
				STOP_VAL       = 0.23f,
				hookVAlue      = gamepad1.x ? 1 : STOP_VAL,
				busket_x_value = BUSKET_X_ZERO + (gamepad1.dpad_left ? BUSKET_X_ANGLE : (gamepad1.dpad_right ? -BUSKET_X_ANGLE : 0)),
				doorValue      = gamepad1.a ? 1 : (gamepad1.b ? 0 : STOP_VAL);
		boolean
				lightOn        = gamepad1.left_stick_button,
				signalOn       = gamepad1.right_stick_button;

        door      .setPosition(doorValue     );
        busket_x  .setPosition(busket_x_value);
        busket_y  .setPosition(busket_y_value);
        hook      .setPosition(hookVAlue     );
		motorHand .setPower(handValue      );
		motorMetla.setPower(metlaValue     );
		motorCLimb.setPower(climbValue     );
		motorRight.setPower(right          );
		motorLeft .setPower(left           );
		light     .setPower(lightOn ? 1 : 0);

        if(signalOn) SoundPlayer.INSTANCE.play("Sirena");

		telemetry.addData("Гирыч", gyro.getHeading());
	}

	float scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
		int index = (int) (dVal * 16.0);
		if (index < 0) index = -index;
		if (index > 16) index = 16;
		return (float)(dVal < 0 ? -scaleArray[index]: scaleArray[index]);
	}
}
