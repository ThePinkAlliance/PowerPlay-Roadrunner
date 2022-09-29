package org.firstinspires.ftc.pinkcode.subsystems.roadrunner;

import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.GEAR_RATIO;
import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.TICKS_PER_REV;
import static org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig.WHEEL_RADIUS;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.drive.MecanumDrive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;

import org.firstinspires.ftc.pinkcode.lib.Subsystem;
import org.firstinspires.ftc.pinkcode.subsystems.Hardware;

import java.util.Arrays;
import java.util.List;

@Config
public class TrackingWheelLocalizer extends ThreeTrackingWheelLocalizer {
    private Hardware hardware;

    public TrackingWheelLocalizer(Hardware hardware, @NonNull List<Pose2d> wheelPoses) {
        super(wheelPoses);

        this.hardware = hardware;
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public List<Double> getWheelVelocities() {
        return Arrays.asList(
                encoderTicksToInches(hardware.leftEncoder.getVelocity()),
                encoderTicksToInches(hardware.rightEncoder.getVelocity()),
                encoderTicksToInches(hardware.centerEncoder.getVelocity())
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(hardware.leftEncoder.getCurrentPosition()),
                encoderTicksToInches(hardware.rightEncoder.getCurrentPosition()),
                encoderTicksToInches(hardware.centerEncoder.getCurrentPosition())
        );
    }
}
