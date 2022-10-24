import static org.junit.Assert.assertEquals;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.pinkcode.subsystems.junction.Junction;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionLocalizer;
import org.junit.Test;

public class TurretTest {
    protected Pose2d robotPose = new Pose2d(14, 80);

    @Test
    public void testJunctionAngle() {
        Junction closestJunction = JunctionLocalizer.locateJunction(robotPose);

        double newAngle = JunctionLocalizer.getAdjustedTurretAngle(closestJunction, robotPose, 90);

        assertEquals(-81.49, newAngle, 0.2);
    }
}
