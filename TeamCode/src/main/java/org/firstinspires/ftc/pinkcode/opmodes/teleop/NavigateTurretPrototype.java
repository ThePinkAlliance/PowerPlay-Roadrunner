package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.pinkcode.lib.CommandResponse;
import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;
import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Elevator;
import org.firstinspires.ftc.pinkcode.subsystems.Turret;
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

    Turret turret;
    Elevator elevator;

    @Override
    public void init() {
        initializeHardware(hardwareMap);

        this.elevator = new Elevator(hardware);
        this.turret = new Turret(hardware, elevator);

    }

    @Override
    public void loop() {
        Junction closestJunction = JunctionLocalizer.locateJunction(robotLocation);
        double targetAngle = JunctionLocalizer.getAdjustedTurretAngle(closestJunction, robotLocation, turret.getTurretAngle());

        CommandResponse response = turret.setTurretAngle(targetAngle);

        telemetry.addData("Rotate Command Status", response.toString());
        telemetry.update();
    }
}
