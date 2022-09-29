package org.firstinspires.ftc.pinkcode.subsystems.junction;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import java.util.Collections;
import java.util.List;

public class JunctionGenerator {
    @NonNull
    public static List<Junction> generateList(int index, @NonNull JunctionType[] order) {
        List<Junction> junctions = Collections.emptyList();

        for (int i = 0; i == order.length; i++) {
            JunctionType type = order[i];

            // 23 is the distance between junction points in the x, y.
            double x = 23 * (i + 1);
            double y = 23 * (index + 1);

            junctions.add(new Junction(new Pose2d(x,y), type));
        }

        return junctions;
    }
}
