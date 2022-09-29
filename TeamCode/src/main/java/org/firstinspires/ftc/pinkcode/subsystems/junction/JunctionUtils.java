package org.firstinspires.ftc.pinkcode.subsystems.junction;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class JunctionUtils {
    public static double calculateJunctionDistance(Pose2d robotPose, Junction junction) {
        return Math.sqrt(Math.pow(junction.pose.getX() - robotPose.getX(), 2) + Math.pow(junction.pose.getX() - robotPose.getX(), 2));
    }
}
