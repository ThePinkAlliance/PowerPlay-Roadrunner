package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.pinkcode.lib.Subsystem;

public class Elevator extends Subsystem {
    DcMotor frontRight;

    public Elevator(Hardware hardware) {
        super(hardware);

        frontRight = hardware.frontRight;
    }
}
