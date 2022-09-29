package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.kA;
import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.kStatic;
import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.kV;
import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.TRACK_WIDTH;
import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.WHEEL_BASE;

import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.roadrunner.MecanumBase;

public class RoadrunnerTest extends PinkLinearOpMode {
    MecanumBase mecanumBase;

    @Override
    public void runOpMode() throws InterruptedException {
        this.mecanumBase = new MecanumBase(kV, kA, kStatic, WHEEL_BASE, TRACK_WIDTH, this.hardware);

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
