package org.firstinspires.ftc.pinkcode.subsystems.junction;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Junction {
    Pose2d pose;
    JunctionType type;

    public Junction(Pose2d pose, JunctionType type) {
        this.pose = pose;
        this.type = type;
    }
}
