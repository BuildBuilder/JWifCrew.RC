package com.qualcomm.ftcrobotcontroller.LolOps;

/**
 * Created by 555 on 27.01.2016.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class TestTeleOp extends OpMode {

	/*
     * Note: the configuration of the servos is such that
     * as the arm servo approaches 0, the arm position moves up (away from the floor).
     * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
     */
	// TETRIX VALUES.
	final static double ARM_MIN_RANGE = 0.20;
	final static double ARM_MAX_RANGE = 0.90;
	// position of the arm servo.
	double armPosition;

	// amount to change the arm servo position.
	double armDelta = 0.1;


	DcMotor motorRight;
	DcMotor motorLeft;
	DcMotor motorU1;
	DcMotor motorU2;
	DcMotor motorU7;
	DcMotor motorU12;
	DcMotor motorU25;
	DcMotor motorU56;
	OpticalDistanceSensor OpticalDistanceSensor1;
	Servo arm;

	/**
	 * Constructor
	 */
	public TestTeleOp() {

	}

	/*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
	@Override
	public void init() {


		motorRight = hardwareMap.dcMotor.get("motor_2"); //правый двигатель
		motorLeft = hardwareMap.dcMotor.get("motor_1");  //левый двигатель
		motorU1 = hardwareMap.dcMotor.get("motor_3");    //рулетка левый
		motorU2 = hardwareMap.dcMotor.get("motor_4");    //рулетка правый
		motorU7 = hardwareMap.dcMotor.get("motor_5");    //сброс
		motorU12 = hardwareMap.dcMotor.get("motor_6");   //стабилизация
		motorU25 = hardwareMap.dcMotor.get("motor_7");   //новая система сбора
		motorU56 = hardwareMap.dcMotor.get("motor_8"); //рулетка поъем
		OpticalDistanceSensor1 = hardwareMap.opticalDistanceSensor.get("OpticalDistanceSensor1"); //рулетка поъем
		motorU2.setDirection(DcMotor.Direction.REVERSE);
		motorRight.setDirection(DcMotor.Direction.REVERSE);

		//   arm = hardwareMap.servo.get("servo_1");

		// assign the starting position of the wrist and claw
		//    armPosition = 0.2;
	}

	/*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
	@Override
	public void loop() {
		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

		// throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
		// 1 is full down
		// direction: left_stick_x ranges from -1 to 1, where -1 is full left
		// and 1 is full right
		// float throttle = -gamepad1.left_stick_y;
		// float direction = gamepad1.left_stick_x;
		// float right = throttle - direction;
		// float left = throttle + direction;


		// clip the right/left values so that the values never exceed +/- 1
//        right = Range.clip(right, -1, 1);
		//      left = Range.clip(left, -1, 1);

		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
		//    right = (float)scaleInput(right);
		//  left =  (float)scaleInput(left);

		// write the values to the motors
		// motorRight.setPower(right);
		// motorLeft.setPower(left);

		// update the position of the arm.
		//     if (gamepad1.a) {
		// if the A button is pushed on gamepad1, increment the position of
		// the arm servo.
		//    armPosition += armDelta;
		//   }

		// if (gamepad1.y) {
		// if the Y button is pushed on gamepad1, decrease the position of
		// the arm servo.
		//      armPosition -= armDelta;
		//}

		// update the position of the claw



		if (gamepad2.dpad_right) {
			motorU56.setPower(-0.4);

		} else {
			if (gamepad2.dpad_left) {
				motorU56.setPower(0.4);

			} else {
				motorU56.setPower(0);
			}
		}


		if (gamepad2.dpad_up) {
			motorU1.setPower(0.6);
			motorU2.setPower(0.6);
			motorU12.setPower(1);

		} else {
			if (gamepad2.dpad_down) {
				motorU1.setPower(-0.6);
				motorU2.setPower(-0.6);
				motorU12.setPower(0);

			} else {
				motorU1.setPower(0);
				motorU2.setPower(0);
				motorU12.setPower(0);

			}
		}


		if (gamepad1.x) {
			motorU7.setPower(0.4);
		} else {
			if (gamepad1.y) {
				motorU7.setPower(-0.4);
			}
			else
			{
				motorU7.setPower(0);
			}
		}

		if (gamepad1.a) {
			motorLeft.setPower(0.6);
			motorRight.setPower(0.6);
		} else
		{
			motorLeft.setPower(gamepad1.left_stick_y);
			motorRight.setPower(gamepad1.right_stick_y);
		}


		if (gamepad2.a) {
			motorU25.setPower(0.8);
		}
		else
		{ if (gamepad2.b) {
			motorU25.setPower(-0.8);
		}
		else
		{motorU25.setPower(0);
		}
		}




		// clip the position values so that they never exceed their allowed range.
		//  armPosition = Range.clip(armPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);

		// write position values to the wrist and claw servo
		//  arm.setPosition(armPosition);



		/*
		 * Send telemetry  data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
		telemetry.addData("Text", "*** Robot Data***");
		// telemetry.addData("arm", "arm:  " + String.format("%.2f", armPosition));
		//telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
		//telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));






	}

	/*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
	@Override
	public void stop() {

	}


	/*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.00, 0.00, 0.00, 0, 0, 0, 0,
				0, 0, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);

		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}

}