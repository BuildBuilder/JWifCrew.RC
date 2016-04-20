package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.LolOps.HTRGBExample;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

public class FtcOpModeRegister implements OpModeRegister {

  /**
   * @param manager op mode manager*/
  public void register(OpModeManager manager) {
    manager.register("тик-так ЕОПД", NullOp.class    );
    manager.register("TeleOp" ,      TestModeOp.class);
    manager.register("GyroTest"  ,   GyroTester.class);
  }
}
