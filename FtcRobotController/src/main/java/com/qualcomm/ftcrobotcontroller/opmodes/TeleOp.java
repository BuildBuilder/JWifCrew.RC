package com.qualcomm.ftcrobotcontroller.opmodes;
import android.util.Log;
import com.qualcomm.ftcrobotcontroller.Helpers.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {
	final static DataExchange data = DataExchange.INSTANCE;
	private HandController
			hand;
	private BasketController
			basket;
	private final static double
			BRUSH_SPEED    = 1;
	DcMotor
			motorLeft,
			motorRight,
			motorHand,
			motorMetla,
			motorCLimb,
			light;
	Servo
			basket_x,
			basket_y,
			hook,
			jostle,
			claw,
			door;
	GyroReader
			gyro;
	@Override
	public void init() {
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
		hook       . setDirection(  Servo.Direction.REVERSE);
		motorRight . setDirection(DcMotor.Direction.REVERSE);
		motorHand  . setDirection(DcMotor.Direction.REVERSE);
		hand       = new HandController(motorHand);
		gyro       = new GyroReader(hardwareMap.gyroSensor.get("gyro" ));
		gyro       . Calibration();
		basket = new BasketController(basket_x, basket_y);
 	}

	@Override
	public void loop() {
		BasketController.BasketPositionX
				xPosition      = gamepad2.dpad_left ? BasketController.BasketPositionX.Left :
								 	gamepad2.dpad_right ? BasketController.BasketPositionX.Right :
								 		gamepad2.dpad_down ? BasketController.BasketPositionX.Center :
											BasketController.BasketPositionX.Current;
		BasketController.BasketPositionY
				yPosition	   = gamepad2.start ? BasketController.BasketPositionY.Higher :
				        		 	gamepad2.back ? BasketController.BasketPositionY.Lower :
										BasketController.BasketPositionY.Current;
		HandController.HandPosition
				handPosition   = gamepad2.right_trigger == 1 ? HandController.HandPosition.Higher :
									gamepad2.left_trigger == 1 ? HandController.HandPosition.Lower :
										HandController.HandPosition.Current;

		double
				rightValue     = scaleInput(Range.clip(gamepad1.left_stick_y , -1, 1)),
				leftValue      = scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1)),
				climbValue     = gamepad1.right_trigger - gamepad1.left_trigger,
				clawValue      = gamepad1.right_bumper ? 1 : gamepad1.left_bumper ? 0 : claw.getPosition(),
				metlaValue     = gamepad2.left_bumper ? -BRUSH_SPEED : (gamepad2.right_bumper ? BRUSH_SPEED : 0),
				jostleValue    = gamepad2.a ? 0 : gamepad2.b ? 0.2 : jostle.getPosition(),
				hookVAlue      = gamepad2.y ? 0 : 0.9,
				doorValue      = gamepad1.a ? 1 : (gamepad1.b ? 0 : 0.23),
				offset		   = gamepad2.left_stick_y / 20;
		boolean
				lightOn        = gamepad1.left_stick_button  ||  gamepad2.left_stick_button,
				signalOn       = gamepad1.right_stick_button || gamepad2.right_stick_button;

		if(Math.abs(climbValue) < 0.4) climbValue = 0;

		basket    .setPosition(xPosition, yPosition, offset);
		claw      .setPosition(clawValue   );
		jostle    .setPosition(jostleValue );
        door      .setPosition(doorValue   );
        hook      .setPosition(hookVAlue   );
//		motorMetla.setPower(metlaValue     );
		motorCLimb.setPower(climbValue     );
		motorRight.setPower(rightValue     );
		motorLeft .setPower(leftValue      );
		light     .setPower(lightOn ? 1 : 0);

        if(signalOn) SoundPlayer.INSTANCE.play("Sirena");
		hand.setPosition(handPosition);

		telemetry.addData("GyroValue", gyro.getHeading());
//		Log.i("Servo val" , " " + basket_x_value);
//		Log.i("Encoder", "Metla :" + motorMetla.getCurrentPosition());
		Log.i("Encoder", "Hand  :" + motorHand .getCurrentPosition());
//		Log.i("Encoder", "Climb :" + motorCLimb.getCurrentPosition());
//		Log.i("Encoder", "Right :" + motorRight.getCurrentPosition());
//		Log.i("Encoder", "Left  :" + motorLeft .getCurrentPosition());
	}

	float scaleInput(double dVal)  {
		return (float)Math.pow(dVal,3);
	}
}