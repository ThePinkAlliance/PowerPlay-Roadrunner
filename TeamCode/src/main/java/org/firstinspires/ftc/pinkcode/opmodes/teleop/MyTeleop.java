package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.pinkcode.subsystems.Elevator;
import org.firstinspires.ftc.pinkcode.subsystems.Hardware;

@TeleOp(name = "MyTeleop")
public class MyTeleop extends OpMode {
    DcMotor motor;
    Servo servo;

    Elevator elevator;

    @Override
    public void init() {
        elevator = new Elevator(new Hardware(hardwareMap));
    }

    @Override
    public void loop() {

    }
}
