package com.qualcomm.ftcrobotcontroller.LolOps;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.ColorSensor;


public class ColorTest extends OpMode {

	public ColorSensor cs;
	@Override
	public void init() {
		cs = hardwareMap.colorSensor.get("cs");
 	}

	@Override
	public void loop() {

			cs.enableLed(true);
			telemetry.addData("red", cs.red());
			telemetry.addData("green", cs.green());
			telemetry.addData("blue", cs.blue());
			telemetry.addData("alpha", cs.alpha());

	}
}
