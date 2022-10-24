package org.firstinspires.ftc.pinkcode.subsystems.junction;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JunctionLocalizer {
    protected static JunctionType[] columnOuter = {JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND};
    protected static JunctionType[] columnInner = {JunctionType.LOW, JunctionType.MEDIUM, JunctionType.HIGH, JunctionType.MEDIUM, JunctionType.LOW};
    protected static JunctionType[] columnCenter = {JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND};

    protected static List<JunctionColumn> junctions = Arrays.asList(new JunctionColumn(JunctionGenerator.generateList(0, columnOuter)), new JunctionColumn(JunctionGenerator.generateList(1, columnInner)), new JunctionColumn(JunctionGenerator.generateList(2, columnCenter)), new JunctionColumn(JunctionGenerator.generateList(3, columnInner)), new JunctionColumn(JunctionGenerator.generateList(4, columnOuter)));

    public static Junction locateJunction(Pose2d robotLocation) {
        // Is the -1 really needed because it might create a preference for junctions on the left.
        int columnIndex = Math.round((float) robotLocation.getY() / 23) - 1;

        if (Math.signum(columnIndex) == -1.0 || junctions.size() < columnIndex) {
            return new Junction(new Vector2d(Double.NaN, Double.NaN), JunctionType.GROUND);
        }

        JunctionColumn column = junctions.get(columnIndex);

        // Sorts the junctions by distance from greatest to smallest.
        column.junctions.sort((a, b) -> (b.vector2d.getX() > a.vector2d.getX() ? 1 : 0));

        // Then sort the junctions distance from robot smallest to greatest.
        column.junctions.sort((a, b) -> nextJunctionIsGreater(robotLocation, a, b));

        // Select the junction closest to the robot.
        return column.junctions.get(0);
    }

    public static Junction locateJunction(Pose2d robotLocation, JunctionType type) {
        int columnIndex = Math.round((float) robotLocation.getY() / 23) - 1;

        if (Math.signum(columnIndex) == -1.0 || junctions.size() < columnIndex) {
            return new Junction(new Vector2d(Double.NaN, Double.NaN), JunctionType.GROUND);
        }

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

    /**
     * This method will take the selected junction, robot position, and current turret angle and find how many degrees the turret needs to move in order to face the selected junction.
     *
     * @param junction The selected junction.
     * @param robotLocation The Pose2d of the robot.
     * @param turretAngle The Turret angle in degrees.
     */
    public static double getAdjustedTurretAngle(@NotNull Junction junction, @NotNull Pose2d robotLocation, double turretAngle) {
        Vector2d junctionVector = junction.getVector();
        Vector2d robotVector = new Vector2d(robotLocation.getX(), robotLocation.getY());

        double theta = robotVector.angleBetween(junctionVector) * (180 / Math.PI);

        return (theta - turretAngle);
    }

    private static int nextJunctionIsGreater(Pose2d robotLocation, Junction a, Junction b) {
        return (JunctionUtils.calculateJunctionDistance(robotLocation, b) < JunctionUtils.calculateJunctionDistance(robotLocation, a) ? 1 : 0);
    }
}