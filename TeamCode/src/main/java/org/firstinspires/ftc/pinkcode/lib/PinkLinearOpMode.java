package org.firstinspires.ftc.pinkcode.lib;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.pinkcode.subsystems.Hardware;

public abstract class PinkLinearOpMode extends LinearOpMode {
    protected Hardware hardware;

    @Override
    public void internalPreInit() {
        super.internalPreInit();

        hardware = new Hardware(hardwareMap);
    }
}
