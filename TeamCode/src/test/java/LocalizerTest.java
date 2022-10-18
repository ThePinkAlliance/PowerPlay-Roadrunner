
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

    @Test
    public void testClosestJunction() {
        Junction closestJunction = JunctionLocalizer.locateJunction(new Pose2d(34, 100));
        Vector2d junctionVector = closestJunction.getVector();
        JunctionType junctionType = closestJunction.getType();

        assertEquals(23.0, junctionVector.getX(), 0);
        assertEquals(92.0, junctionVector.getY(), 0);
    }

    public void printJunctions() {
        int idx = 0;

        for (JunctionColumn column: junctions) {
            System.out.println("Column: " + idx);
            for (Junction junction: column.junctions) {
                Vector2d pose = junction.getVector();
                System.out.println("x: " + pose.getX() + ", y: " + pose.getY());
            }
            System.out.println("\n");
            idx++;
        }

        Junction junction = JunctionLocalizer.locateJunction(new Pose2d(34, 100));

        System.out.println("Closest Junction[ x: " + junction.getVector().getX() + ", y: " + junction.getVector().getY() + " ]");

    }
}
