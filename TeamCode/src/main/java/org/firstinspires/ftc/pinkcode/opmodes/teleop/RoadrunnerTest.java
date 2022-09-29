package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.roadrunner.MecanumBase;

public class RoadrunnerTest extends PinkLinearOpMode {
    MecanumBase mecanumBase;

    @Override
    public void runOpMode() throws InterruptedException {
        this.mecanumBase = new MecanumBase(hardwareMap, this.hardware);

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
