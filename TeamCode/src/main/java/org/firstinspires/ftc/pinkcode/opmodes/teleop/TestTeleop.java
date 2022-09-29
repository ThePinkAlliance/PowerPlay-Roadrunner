package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Base;

/**
 * @TeleOp tells the driver station that this class is a teleop mode and to list it on the driver station.
 */
@TeleOp(name = "Test Teleop")
public class TestTeleop extends PinkOpMode {
    Base base;

    /**
     * Everything in the init method is executed when the driver presses the init button on the driver station.
     *
     * The init state always comes before the start state which is handled by the loop method.
     */
    @Override
    public void init() {
        base = new Base(hardware);
    }

    /**
     * Loop runs when start is selected on the driver station and runs repeatedly until the driver presses the stop button.
     */
    @Override
    public void loop() {

        /*
         * Passing the values from gamepad1's left y stick and right y stick into the drive method allowing for tank styled control.
         */
        base.mecanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

    @Override
    public void stop() {

    }
}
