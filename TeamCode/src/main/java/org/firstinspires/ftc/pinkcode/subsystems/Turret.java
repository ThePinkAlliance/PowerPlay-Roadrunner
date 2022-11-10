package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.pinkcode.lib.CommandResponse;
import org.firstinspires.ftc.pinkcode.lib.Subsystem;

public class Turret extends Subsystem {
    // The sum of PPR (Pulses Per Revolution) * 4 is ticks per rotation.
    private final double TICKS_PER_ROT = 1538;

    private final float gearRatio = 27 / 3f;
    private final double degreesPerRotation = (360 / (1 / gearRatio));

    public PIDFCoefficients pidfCoefficients = new PIDFCoefficients();
    public Elevator elevator;

    public Turret(Hardware hardware, Elevator elevator) {
        super(hardware);

        this.elevator = elevator;

        this.hardware.turretMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, pidfCoefficients);
    }

    public CommandResponse setTurretAngle(double targetAngle) {
        double targetPosition = (targetAngle / degreesPerRotation) * TICKS_PER_ROT;

        if (elevator.hasClearedMinimumRotateHeight()) {
            this.hardware.turretMotor.setTargetPosition((int) targetPosition);

            return CommandResponse.ACCEPTED;
        }

        return CommandResponse.REJECTED;
    }

    public double getTurretAngle() {
        return degreesPerRotation * ((this.hardware.turretMotor.getCurrentPosition() / TICKS_PER_ROT) * gearRatio);
    }
}
