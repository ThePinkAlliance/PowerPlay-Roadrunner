package org.firstinspires.ftc.pinkcode.subsystems.junction;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JunctionLocalizer {
    protected static JunctionType[] columnOuter = {JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND};
    protected static JunctionType[] columnInner = {JunctionType.LOW, JunctionType.MEDIUM, JunctionType.HIGH, JunctionType.MEDIUM, JunctionType.LOW};
    protected static JunctionType[] columnCenter = {JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND};

    protected static List<JunctionColumn> junctions = Arrays.asList(new JunctionColumn(JunctionGenerator.generateList(0, columnOuter)), new JunctionColumn(JunctionGenerator.generateList(1, columnInner)), new JunctionColumn(JunctionGenerator.generateList(2, columnCenter)), new JunctionColumn(JunctionGenerator.generateList(3, columnInner)), new JunctionColumn(JunctionGenerator.generateList(4, columnOuter)));

    public static Junction locateJunction(Pose2d robotLocation) {
        int columnIndex = Math.round((float) robotLocation.getY() / 23) - 1;

        JunctionColumn column = junctions.get(columnIndex);

        // Sorts the junctions by distance from greatest to smallest.
        column.junctions.sort((a, b) -> (b.vector2d.getX() > a.vector2d.getX() ? 1 : 0));

        // Then sort the junctions distance from robot smallest to greatest.
        column.junctions.sort((a, b) -> (JunctionUtils.calculateJunctionDistance(robotLocation, b) < JunctionUtils.calculateJunctionDistance(robotLocation, a) ? 1 : 0));

        // Select the junction closest to the robot.
        return column.junctions.get(0);
    }

    public static Junction locateJunction(Pose2d robotLocation, JunctionType type) {
        int columnIndex = Math.round((float) robotLocation.getY() / 23) - 1;

        JunctionColumn column = junctions.get(columnIndex);

        // Sorts the junctions by distance from greatest to smallest.
        column.junctions.sort((a, b) -> (b.vector2d.getX() > a.vector2d.getX() ? 1 : 0));

        // Then sort the junctions distance from robot smallest to greatest.
        column.junctions.sort((a, b) -> nextJunctionIsGreater(robotLocation, a, b));

        // Creates a list of junctions filtered by type and sorted by distance from the robot
        List<Junction> filteredJunctionList = column.junctions.stream().filter((e) -> e.type.equals(type)).sorted((a, b) -> nextJunctionIsGreater(robotLocation, a, b)).collect(Collectors.toList());

        // Select the junction closest to the robot.
        return filteredJunctionList.get(0);
    }

    private static int nextJunctionIsGreater(Pose2d robotLocation, Junction a, Junction b) {
        return (JunctionUtils.calculateJunctionDistance(robotLocation, b) < JunctionUtils.calculateJunctionDistance(robotLocation, a) ? 1 : 0);
    }
}