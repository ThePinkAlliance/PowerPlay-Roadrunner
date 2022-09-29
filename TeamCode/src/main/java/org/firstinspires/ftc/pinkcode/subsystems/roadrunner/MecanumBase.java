package org.firstinspires.ftc.pinkcode.subsystems.roadrunner;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.drive.MecanumDrive;

import org.firstinspires.ftc.pinkcode.subsystems.Hardware;

import java.util.Arrays;
import java.util.List;

public class MecanumBase extends MecanumDrive {
    protected Hardware hardware;

    public MecanumBase(double kV, double kA, double kStatic, double trackWidth, double wheelBase, Hardware hardware) {
        super(kV, kA, kStatic, trackWidth, wheelBase);

        this.hardware = hardware;
    }

    @Override
    protected double getRawExternalHeading() {
        return 0;
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList();
    }

    @Override
    public void setMotorPowers(double v, double v1, double v2, double v3) {
        this.hardware.frontLeft.setPower(v);
        this.hardware.frontRight.setPower(v1);
        this.hardware.backLeft.setPower(v2);
        this.hardware.backRight.setPower(v3);
    }
}
