package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.pinkcode.lib.Subsystem;
import org.firstinspires.ftc.pinkcode.subsystems.roadrunner.RobotConfig;

public class Elevator extends Subsystem {
    // Unit Inches
    private double currentElevatorHeight = 0;

    private final double clawHeightDifference = 0;
    private final double maxElevatorHeightTicks = 100;
    private final double maxElevatorHeightInches = 120;
    private final double inchesPerElevatorRotation = maxElevatorHeightInches / (maxElevatorHeightTicks / RobotConfig.TICKS_PER_REV);
    private final double safeRotationHeightInches = 8;

    PIDFCoefficients pidCoefficients = new PIDFCoefficients();

    public Elevator(Hardware hardware) {
        super(hardware);

        this.hardware.liftMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidCoefficients);
        this.hardware.liftMotor.setTargetPositionTolerance(2);
    }

    /**
     * Commands the elevator to move to a specific height with the height of the claw in mind.
     */
    public void setElevatorHeight(double height) {
        double rotations = (height + clawHeightDifference) / inchesPerElevatorRotation;
        double position = Range.clip(rotations * RobotConfig.TICKS_PER_REV, 0, maxElevatorHeightTicks);

        this.hardware.liftMotor.setTargetPosition((int) position);
        this.currentElevatorHeight = height + clawHeightDifference;
    }

    /**
     * Returns the current claw height from the ground in inches.
     */
    public double getClawHeight() {
        return currentElevatorHeight - clawHeightDifference;
    }

    /**
     * Returns if the current elevator height has cleared the zone that's dangerous to rotate.
     */
    public boolean hasClearedMinimumRotateHeight() {
        return getClawHeight() > safeRotationHeightInches;
    }

    /**
     * Returns the minimum height that's safe for the turret to rotate.
     */
    public double getSafeRotationHeight() {
        return safeRotationHeightInches;
    }

    /**
     * Returns the current elevator height in inches.
     */
    public double getElevatorHeight() {
        return currentElevatorHeight;
    }
}
