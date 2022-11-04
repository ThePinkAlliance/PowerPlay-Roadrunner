package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.junction.Junction;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionLocalizer;

/**
 * This Operational mode is a prototype of automatic turret control,
 * this opmode is intended to be self contained e.g not using devices from the hardware class this allows for easy testing.
 */
@TeleOp(name = "Turret Navigate Prototype, [EXPERIMENTAL]")
@Config
public class NavigateTurretPrototype extends PinkOpMode {
    private final Pose2d robotLocation = new Pose2d(45, 100);

    // The sum of PPR (Pulses Per Revolution) * 4 is ticks per rotation.
    private final double TICKS_PER_ROT = 1538;

    private final float gearRatio = 27 / 3f;
    private final double degreesPerRotation = (360 / (1 / gearRatio));

    public PIDFCoefficients pidfCoefficients = new PIDFCoefficients(0, 0, 0, 0);

    DcMotorEx turretMotor;

    @Override
    public void init() {
        initializeHardware(hardwareMap);

        this.turretMotor = hardwareMap.get(DcMotorEx.class, "turret-motor");

        this.turretMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidfCoefficients);
    }

    @Override
    public void loop() {
        Junction closestJunction = JunctionLocalizer.locateJunction(robotLocation);
        double targetAngle = JunctionLocalizer.getAdjustedTurretAngle(closestJunction, robotLocation, getTurretAngle());

        setTurretAngle(targetAngle);
    }

    public void setTurretAngle(double targetAngle) {
        double targetPosition = (targetAngle / degreesPerRotation) * TICKS_PER_ROT;

        this.turretMotor.setTargetPosition((int) targetPosition);
    }

    public double getTurretAngle() {
        return degreesPerRotation * ((turretMotor.getCurrentPosition() / TICKS_PER_ROT) * gearRatio);
    }
}
