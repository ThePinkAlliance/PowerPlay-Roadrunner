package org.firstinspires.ftc.pinkcode.lib;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.pinkcode.subsystems.Hardware;

public abstract class PinkOpMode extends OpMode {
    protected Hardware hardware;

    @Override
    public void internalPreInit() {
        super.internalPreInit();

        hardware = new Hardware(hardwareMap);
    }
}
