package org.firstinspires.ftc.pinkcode.subsystems.junction;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import java.util.Arrays;
import java.util.List;

public class JunctionLocalizer {
    protected static JunctionType[] columnOuter = {JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND};
    protected static JunctionType[] columnInner = {JunctionType.LOW, JunctionType.MEDIUM, JunctionType.HIGH, JunctionType.MEDIUM, JunctionType.LOW};
    protected static JunctionType[] columnCenter = {JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND};

    protected static List<JunctionColumn> junctions = Arrays.asList(new JunctionColumn(JunctionGenerator.generateList(0, columnOuter)), new JunctionColumn(JunctionGenerator.generateList(1, columnInner)), new JunctionColumn(JunctionGenerator.generateList(2, columnCenter)), new JunctionColumn(JunctionGenerator.generateList(3, columnInner)), new JunctionColumn(JunctionGenerator.generateList(4, columnOuter)));

    public static Junction locateJunction(Pose2d robotLocation) {
        int columnIndex = Math.round((float) robotLocation.getY() / 23) - 1;
        JunctionColumn column = junctions.get(columnIndex);

        // Sorts the junctions by distance from greatest to smallest.
        column.junctions.sort((a, b) -> (b.pose.getX() > a.pose.getX() ? 1 : 0));

        // Then sort the junctions distance from robot smallest to greatest.
        column.junctions.sort((a, b) -> (JunctionUtils.calculateJunctionDistance(robotLocation, b) < JunctionUtils.calculateJunctionDistance(robotLocation, a) ? 1 : 0));

        // Select the junction closest to the robot.
        return column.junctions.get(0);
    }
}