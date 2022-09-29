package org.firstinspires.ftc.pinkcode.subsystems.roadrunner;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConfig {
    public static double kV = 0;
    public static double kStatic = 0;
    public static double kA = 0;
    public static double WHEEL_BASE = 0;
    public static double TRACK_WIDTH = 0;

    public static double TICKS_PER_REV = 0;
    public static double WHEEL_RADIUS = 2; // in
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (encoder) speed

    public static double LATERAL_DISTANCE = 10; // in; distance between the left and right wheels
    public static double FORWARD_OFFSET = 4; // in; offset of the lateral wheel

}
