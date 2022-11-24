package org.firstinspires.ftc.pinkcode.opmodes.teleop;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Claw;

@TeleOp(name = "ClawTeleop")
public class ClawTeleop extends PinkOpMode {
    Claw claw;

    @Override
    public void init() {
        initializeHardware(hardwareMap);
        claw = new Claw(hardware);


    }

    @Override
    public void loop() {

        //Opens and closes claw
        if (gamepad1.a) claw.moveClaw(1.0);
        if (gamepad1.b) claw.moveClaw(0.0);

        //Moves bar forward
        if (gamepad1.x) {
            claw.moveBar(0.75);

        } else {
            claw.moveBar(0.0);
        }

        //Moves bar backward
        if (gamepad1.y) {
            claw.moveBar(-0.25);
        } else {
            claw.moveBar(0.0);
        }
    }

}
