package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.Base;
import org.firstinspires.ftc.pinkcode.subsystems.Lift;
import org.firstinspires.ftc.pinkcode.subsystems.Turret;

@TeleOp(name = "[PROTOTYPE] Teleop", group = "proto")
public class PrototypeTele extends PinkOpMode {
    Turret turret;
    Lift lift;
    Base base;

    @Override
    public void init() {
        initializeHardware(hardwareMap);

        this.lift = new Lift(hardware);
        this.turret = new Turret(hardware, lift);
        this.base = new Base(hardware);
    }

    @Override
    public void loop() {
        /*
         * NOTE: There is the possibility that the way the setPosition method works for the ftc motors and how our code is handling the calculation of the turret angle,
         * you might encounter some drift or other erratic behavior.
         *
         * NOTE: Do keep in mind that the setPosition method from the DcMotor object might be relative and not absolute.
         */
        if (gamepad2.dpad_up) {
            this.turret.setTurretAngle(0);
        } else if (gamepad2.dpad_left && isTowerReady()) {
            this.turret.setTurretAngle(-90);
        } else if (gamepad2.dpad_right && isTowerReady()) {
            this.turret.setTurretAngle(90);
        } else if (gamepad2.dpad_down && isTowerReady()) {
            this.turret.setTurretAngle(180);
        }

        /*
         * Keep in mind that all these inputs for height are arbitrary and need to be determined though testing.
         */
        if (gamepad2.a && isTurretAtHome()) {
            this.lift.setLiftHeight(0);
        } else if (gamepad2.b) {
            this.lift.setLiftHeight(7);
        } else if (gamepad2.y) {
            this.lift.setLiftHeight(14);
        } else if (gamepad2.x) {
            this.lift.setLiftHeight(21);
        }

        this.lift.setExtensionPower(gamepad2.left_stick_y);
        this.base.mecanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }

    public boolean isTurretAtHome() {
        boolean atHome = this.turret.getPosition() <= 5 && this.turret.getPosition() >= -5;

        if (!atHome) {
            this.turret.setTurretAngle(0);
        }

        return atHome;
    }

    public boolean isTowerReady() {
        boolean cleared = this.lift.hasClearedMinimumRotateHeight();

        if (!cleared) {
            this.lift.goToSafeHeight();
        }

        return cleared;
    }
}
