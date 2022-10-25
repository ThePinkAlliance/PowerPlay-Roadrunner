package org.firstinspires.ftc.pinkcode.subsystems.junction;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        JunctionColumn columnInline = junctions.get(columnIndex);
        JunctionColumn columnFront = junctions.get(columnIndex+1);
        JunctionColumn columnBack = junctions.get(columnIndex-1);

        ArrayList<Junction> combinedJunctions = new ArrayList<>();

        combinedJunctions.addAll(columnBack.junctions);
        combinedJunctions.addAll(columnFront.junctions);
        combinedJunctions.addAll(columnInline.junctions);

        // Then sort the junctions distance from robot smallest to greatest.
        combinedJunctions.sort((a,b) -> nextJunctionIsCloser(robotLocation, a, b));

        // Select the junction closest to the robot.
        return combinedJunctions.get(0);
    }

    public static Junction locateJunction(Pose2d robotLocation, JunctionType type) {
        int columnIndex = Math.round((float) robotLocation.getY() / 23) - 1;

        if (Math.signum(columnIndex) == -1.0 || junctions.size() < columnIndex) {
            return new Junction(new Vector2d(Double.NaN, Double.NaN), JunctionType.GROUND);
        }

        JunctionColumn columnInline = junctions.get(columnIndex);
        JunctionColumn columnFront = junctions.get(columnIndex+1);
        JunctionColumn columnBack = junctions.get(columnIndex-1);

        ArrayList<Junction> combinedJunctions = new ArrayList<>();

        combinedJunctions.addAll(columnBack.junctions);
        combinedJunctions.addAll(columnFront.junctions);
        combinedJunctions.addAll(columnInline.junctions);

        // Creates a list of junctions filtered by type and sorted by distance from the robot
        List<Junction> filteredJunctionList = combinedJunctions.stream().filter((e) -> e.type.equals(type)).sorted((a, b) -> nextJunctionIsCloser(robotLocation, a, b)).collect(Collectors.toList());

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

    private static int nextJunctionIsCloser(Pose2d robotLocation, Junction a, Junction b) {
        double d2 = JunctionUtils.calculateJunctionDistance(robotLocation, b);
        double d1 = JunctionUtils.calculateJunctionDistance(robotLocation, a);

        return (d1 > d2 ? 1 : -1);
    }
}