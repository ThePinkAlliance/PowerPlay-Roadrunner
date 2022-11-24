package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.pinkcode.lib.CommandResponse;
import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Lift;
import org.firstinspires.ftc.pinkcode.subsystems.Turret;
import org.firstinspires.ftc.pinkcode.subsystems.junction.Junction;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionLocalizer;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionUtils;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "Tower Prototype, [EXPERIMENTAL]")
public class TowerPrototype extends PinkOpMode {
    private final Pose2d robotLocation = new Pose2d(45, 100);

    // The distance from the center of the robot to the center of the claw, Unit: Inches.
    double robotCenterToClawCenterDistance = 10;

    Turret turret;
    Lift lift;

    private MotionProfile currentMotionProfile;
    private double velocityConstraint;
    private double accelerationConstraint;
    private double jerkConstraint;
    private MotionState previousMotionState;

    public static PIDCoefficients coefficients = new PIDCoefficients();
    public static PIDFController controller = new PIDFController(coefficients);

    private final Junction closestJunction = JunctionLocalizer.locateJunction(robotLocation);
    private final double targetAngle = JunctionLocalizer.getAdjustedTurretAngle(closestJunction, robotLocation, turret.getTurretAngle());

    private ElapsedTime profileTimer;
    private MotionProfile motionProfile;
    private boolean timerReset;

    @Override
    public void init() {
        initializeHardware(hardwareMap);

        this.lift = new Lift(hardware);
        this.turret = new Turret(hardware, lift);

        this.velocityConstraint = 25;
        this.accelerationConstraint = 40;
        this.jerkConstraint = 80;
        this.previousMotionState = new MotionState(0,0);

        // Might be worth investigating using the output provided by update for improved control system latency.
        motionProfile = generateMotionProfile(new MotionState(targetAngle, 0));

        profileTimer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        timerReset = false;
    }

    @Override
    public void loop() {
        if (timerReset) {
            profileTimer.reset();
            timerReset = false;
        }

        double currentAngle = this.turret.getTurretAngle();
        MotionState state = motionProfile.get(profileTimer.milliseconds());

        controller.setTargetAcceleration(state.getA());
        controller.setTargetVelocity(state.getV());
        controller.setTargetPosition(state.getX());

        double scaledPower = (controller.getTargetPosition() - controller.update(currentAngle)) / controller.getTargetPosition();

        // Allow the turret to move if x button is pressed and the lift is at a safe height.
        if (lift.hasClearedMinimumRotateHeight() && gamepad1.x) {
            CommandResponse response = turret.setTurretPower(scaledPower);

            telemetry.addData("Rotate Command Status", response.toString());
            telemetry.addData("Turret Power", scaledPower);
        } else {
            turret.setTurretAngle(0);
        }

        // Command the lift to move a little above the nearest junction.
        if (gamepad1.b) {
            lift.setLiftHeight(closestJunction.getJunctionHeight() + 2);
        } else {
            lift.stopLift();
        }

        // Set the extension bar target distance to the distance between the robot and the target junction.
        if (gamepad1.a) {
            double distance = JunctionUtils.calculateJunctionDistance(robotLocation, closestJunction) - robotCenterToClawCenterDistance;

            lift.setExtensionDistance(distance);

            telemetry.addData("Extension Bar Distance", distance);
            telemetry.addData("Extension Position", this.hardware.extensionMotor.getCurrentPosition());
            telemetry.addData("Extension Current [amps]", this.hardware.extensionMotor.getCurrent(CurrentUnit.AMPS));
        } else {
            lift.stopExtensionMotor();
        }

        telemetry.update();
    }

    private MotionProfile generateMotionProfile(MotionState goal) {
        MotionProfile profile = MotionProfileGenerator.generateSimpleMotionProfile(previousMotionState, goal, velocityConstraint, accelerationConstraint, jerkConstraint);

        this.previousMotionState = goal;

        return profile;
    }
}
