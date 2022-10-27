package org.firstinspires.ftc.pinkcode.lib;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.pinkcode.subsystems.Hardware;

public abstract class PinkOpMode extends OpMode {
    protected Hardware hardware;

    public void initializeHardware(HardwareMap map) {
        this.hardware = new Hardware(map);
    }
}
