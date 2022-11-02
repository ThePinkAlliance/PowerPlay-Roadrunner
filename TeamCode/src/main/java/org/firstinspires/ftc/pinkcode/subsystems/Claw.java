package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.pinkcode.lib.Subsystem;

public class Claw extends Subsystem {
    Servo claw;
    DcMotor barMover;

    // TODO: Make a method for the barMover.
    public Claw(Hardware hardware) {
        super(hardware);
        claw = hardware.claw;
        barMover = hardware.barMover;
    }

    public void move(double position) {
        claw.setPosition(position);
    }
}
