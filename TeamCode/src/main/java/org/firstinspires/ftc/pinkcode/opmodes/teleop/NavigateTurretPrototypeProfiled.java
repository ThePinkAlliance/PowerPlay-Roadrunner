package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.control.PIDCoefficients;
import com.acmerobotics.roadrunner.control.PIDFController;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.profile.MotionProfile;
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator;
import com.acmerobotics.roadrunner.profile.MotionState;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.pinkcode.lib.CommandStatus;
import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Lift;
import org.firstinspires.ftc.pinkcode.subsystems.Turret;
import org.firstinspires.ftc.pinkcode.subsystems.junction.Junction;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionLocalizer;

/**
 * This Operational mode is a prototype of automatic turret control,
 * this opmode is intended to be self contained e.g not using devices from the hardware class this allows for easy testing.
 */
@TeleOp(name = "Turret Navigate Prototype Profiled, [EXPERIMENTAL]", group = "EXPERIMENTAL")
@Config
public class NavigateTurretPrototypeProfiled extends PinkOpMode {
    private final Pose2d robotLocation = new Pose2d(45, 100);

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
        CommandStatus response = turret.setTurretPower(scaledPower);

        telemetry.addData("Rotate Command Status", response.toString());
        telemetry.update();
    }

    private MotionProfile generateMotionProfile(MotionState goal) {
        MotionProfile profile = MotionProfileGenerator.generateSimpleMotionProfile(previousMotionState, goal, velocityConstraint, accelerationConstraint, jerkConstraint);

        this.previousMotionState = goal;

        return profile;
    }
}
