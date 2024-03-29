
import static org.junit.Assert.assertEquals;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.pinkcode.subsystems.junction.Junction;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionColumn;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionGenerator;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionLocalizer;
import org.firstinspires.ftc.pinkcode.subsystems.junction.JunctionType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalizerTest {
    protected JunctionType[] columnOuter = {JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND, JunctionType.LOW, JunctionType.GROUND};
    protected JunctionType[] columnInner = {JunctionType.LOW, JunctionType.MEDIUM, JunctionType.HIGH, JunctionType.MEDIUM, JunctionType.LOW};
    protected JunctionType[] columnCenter = {JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND, JunctionType.HIGH, JunctionType.GROUND};

    protected ArrayList<JunctionColumn> junctions = new ArrayList<>(Arrays.asList(new JunctionColumn(JunctionGenerator.generateList(0, columnOuter)), new JunctionColumn(JunctionGenerator.generateList(1, columnInner)), new JunctionColumn(JunctionGenerator.generateList(2, columnCenter)), new JunctionColumn(JunctionGenerator.generateList(3, columnInner)), new JunctionColumn(JunctionGenerator.generateList(4, columnOuter))));
    protected Pose2d robotPose = new Pose2d(34, 100);

    @Test
    public void testClosestJunction() {
        Junction closestJunction = JunctionLocalizer.locateJunction(robotPose);
        Vector2d junctionVector = closestJunction.getVector();

        assertEquals(23.3, junctionVector.getX(), 0);
        assertEquals(93.2, junctionVector.getY(), 0);
    }

    @Test
    public void testClosestJunctionType() {
        Junction closestHighJunction = JunctionLocalizer.locateJunction(robotPose, JunctionType.HIGH);

        assertEquals(JunctionType.HIGH, closestHighJunction.getType());
        assertEquals(46.6, closestHighJunction.getVector().getX(),0);
        assertEquals(69.9, closestHighJunction.getVector().getY(), 0);
    }
}
