package org.firstinspires.ftc.pinkcode.opmodes.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.roadrunner.MecanumBase;

@Autonomous(name = "[RR] Drive Forward Test")
public class DriveForward extends PinkLinearOpMode {
    Pose2d startingPose = new Pose2d();
    MecanumBase base;

    @Override
    public void runOpMode() throws InterruptedException {
        base = new MecanumBase(hardwareMap);

        // Move the robot forward 12 inches.
        Trajectory forwardTrajectory = base.trajectoryBuilder(startingPose).forward(12).build();

        waitForStart();

        // Tell the base to follow the trajectory.
        base.followTrajectoryAsync(forwardTrajectory);
    }
}
