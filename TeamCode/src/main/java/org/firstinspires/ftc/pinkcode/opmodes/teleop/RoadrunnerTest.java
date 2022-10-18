package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.config.TrajectoryConfig;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
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
