package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;
import org.firstinspires.ftc.pinkcode.subsystems.junction.Junction;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionLocalizer;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionType;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionUtils;

@TeleOp(name = "Junction Localizer Test, [EXPERIMENTAL]")
public class JunctionLocalizerTest extends OpMode {
    @Override
    public void init() {
        telemetry.addData("test distance", JunctionLocalizer.locateJunction(new Pose2d(10,10)));
    }

    @Override
    public void loop() {

    }
}
