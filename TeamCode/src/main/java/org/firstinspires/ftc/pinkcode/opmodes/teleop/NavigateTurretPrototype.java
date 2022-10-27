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

@TeleOp(name = "Turret Navigate Prototype, [EXPERIMENTAL]")
@Config
public class NavigateTurretPrototype extends PinkOpMode {
    private final Pose2d robotLocation = new Pose2d(45, 100);

    private final double TICKS_PER_ROT = 580.4;

    private final float gearRatio = 30 / 120f;
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
