package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileBuilder;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;

@TeleOp(name = "Configure Elevator & Turret, [EXPERIMENTAL]", group = "EXPERIMENTAL")
@Config
public class ConfigureElevatorAndTurret extends PinkOpMode {
    public static boolean enablePid = false;

    public static int elevatorTargetPosition = 0;

    public static PIDFCoefficients turretPidCoefficients = new PIDFCoefficients();
    public static PIDFCoefficients elevatorPidCoefficients = new PIDFCoefficients();

    DcMotorEx turretMotor;
    DcMotorEx elevatorMotor;

    @Override
    public void init() {
        this.elevatorMotor = this.hardware.liftMotor;
        this.turretMotor = this.hardware.turretMotor;

        if (enablePid) {
            this.turretMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, turretPidCoefficients);
            this.elevatorMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, elevatorPidCoefficients);
        }
    }

    @Override
    public void loop() {
        double turretPosition = turretMotor.getCurrentPosition();
        double elevatorPosition = elevatorMotor.getCurrentPosition();

        if (!enablePid) {
            double max = 0.5;

            elevatorMotor.setPower(Range.clip(gamepad1.left_stick_y * -1.0, max * -1.0, max));
        }

        if (enablePid) {
            elevatorMotor.setTargetPosition(elevatorTargetPosition);
        }

        telemetry.addData("turret position", turretPosition);
        telemetry.addData("elevator position", elevatorPosition);
        telemetry.update();
    }
}
