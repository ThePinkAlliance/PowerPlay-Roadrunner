package org.firstinspires.ftc.pinkcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Base;

/**
 * @Autonomous tells the driver station that this class is a auto mode and to list it on the driver station.
 */
@Autonomous(name = "Test Auto")
public class TestAuto extends PinkLinearOpMode {
    Base base;

    /**
     * Everything before waitForStart is executed when the driver presses the init button on the driver station.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        base = new Base(this.hardware);

        /* This pauses the linear opmode using a while loop until the driver selects play */
        waitForStart();

        /*
         * All this while loop is doing is it's calling the move method repeatedly from the base subsystem
         * until the driver presses stop.
         */
        while (opModeIsActive()) {
            base.move(1);
        }
    }
}
