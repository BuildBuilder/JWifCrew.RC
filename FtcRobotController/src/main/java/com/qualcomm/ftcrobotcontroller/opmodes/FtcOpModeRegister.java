package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.ftcrobotcontroller.LolOps.SeroTest;
import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegister;

public class FtcOpModeRegister implements OpModeRegister {

  /**
   * @param manager op mode manager*/
  public void register(OpModeManager manager) {
    manager.register("тик-так ЕОПД", NullOp.class    );
    manager.register("рукокладочка", HandsDown.class );
    manager.register("TeleOp"      , TeleOp.class    );
    manager.register("servo", SeroTest.class);
    manager.register("GyroTest"    , GyroTester.class);
  }
}
