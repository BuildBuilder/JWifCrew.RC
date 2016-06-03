package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.Helpers.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

public class TeleOp extends OpMode {
	final static DataExchange data = DataExchange.INSTANCE;
	private Robot robot = new Robot();
	private RobotCondition rc = robot.getRobotCondition();

	@Override
	public void init(){
		robot.init(hardwareMap);
		robot.LoggerInit();
	}
	@Override
	public void loop() {
		rc.xPosition    = gamepad2.dpad_left ? BasketController.BasketPositionX.Left :
								 	gamepad2.dpad_right ? BasketController.BasketPositionX.Right :
								 		gamepad2.dpad_down ? BasketController.BasketPositionX.Center :
											BasketController.BasketPositionX.Current;
		rc.yPosition = BasketController.BasketPositionY.Current;
		rc.handPosition = gamepad2.right_trigger == 1 ? HandController.HandPosition.Higher :
									gamepad2.left_trigger == 1 ? HandController.HandPosition.Lower :
										HandController.HandPosition.Current;
		rc.rightValue   = scaleInput(Range.clip(gamepad1.right_stick_y, -1, 1));
		rc.leftValue    = scaleInput(Range.clip(gamepad1.left_stick_y , -1, 1));
		rc.climbValue   = gamepad1.right_trigger - gamepad1.left_trigger;
		rc.clawValue    = gamepad1.right_bumper ? 1 : gamepad1.left_bumper ? 0 : robot.claw.getPosition();
		rc.metlaValue   = gamepad2.left_bumper ? 1 : (gamepad2.right_bumper ? -1 : 0);
		rc.jostleValue  = gamepad2.a ? 0 : gamepad2.b ? 1 : robot.jostle.getPosition();
		rc.hookVAlue    = gamepad2.y ? 1 : 0;
		rc.doorValue    = Math.pow(Math.abs(gamepad2.right_stick_y / 2+0.5), 0.8) * 0.45;
		rc.x_offset		= gamepad2.left_stick_y / 10;
		rc.lightOn      = gamepad1.left_stick_button  ||  gamepad2.left_stick_button;
		rc.signalOn     = gamepad1.right_stick_button;
		rc.isDoorLocked = gamepad2.right_stick_button ? false :
							gamepad2.back ? true :
							  rc.isDoorLocked;
		rc.isDoorOpened = gamepad2.x ? true :
							gamepad2.right_stick_button ? false :
							rc.isDoorOpened;


		if(Math.abs(rc.climbValue) < 0.4) rc.climbValue = 0;
		if(rc.signalOn) SoundPlayer.INSTANCE.play("Sirena");
		robot.setCondition();
	}

	float scaleInput(double dVal)  {
		return (float)Math.pow(dVal,3);
	}
}