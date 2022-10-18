package org.firstinspires.ftc.pinkcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.roadrunner.MecanumBase;

@Autonomous(name = "Roadrunner Test Move 12 inches")
public class RoadrunnerTest extends PinkLinearOpMode {
    MecanumBase mecanumBase;

    @Override
    public void runOpMode() throws InterruptedException {
        this.mecanumBase = new MecanumBase(hardwareMap, this.hardware);

        // Move forward 12 inches.
        Trajectory testTrajectory = mecanumBase.trajectoryBuilder(new Pose2d()).forward(12).addDisplacementMarker(() -> {
            telemetry.addData("[STATUS]", "Trajectory Complete.");
            telemetry.update();
        }).build();

        waitForStart();

        mecanumBase.followTrajectoryAsync(testTrajectory);
    }
}
