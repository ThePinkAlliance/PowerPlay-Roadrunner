package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.ejml.simple.SimpleMatrix;
import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;

@TeleOp(name = "Tower Inverse Kinematics, [EXPERIMENTAL]")
public class TowerKinematics extends PinkOpMode {
    int numRows = 2;
    int numCols = 2;

    SimpleMatrix identityMatrix = SimpleMatrix.identity(3);
    SimpleMatrix frameOne = new SimpleMatrix(numRows, numCols);
    SimpleMatrix frameTwo = new SimpleMatrix(numRows, numCols);
    SimpleMatrix frameThree = new SimpleMatrix(numRows, numCols);

    @Override
    public void init() {
        initializeHardware(hardwareMap);

        frameOne.set(0, 0, 1);
        frameOne.set(1, 1, 1);
        frameOne.set(2, 2, 1);
    }

    private SimpleMatrix createZRotationMatrix(double theta) {
        SimpleMatrix matrix = new SimpleMatrix(2, 2);

        matrix.set(0, 0, Math.cos(theta));
        matrix.set(0, 1, -Math.sin(theta));
        matrix.set(1, 1, Math.cos(theta));
        matrix.set(1, 0, Math.sin(theta));

        return matrix;
    }


    private SimpleMatrix createYRotationMatrix(double theta) {
        SimpleMatrix matrix = new SimpleMatrix(2, 2);

        matrix.set(0, 0, Math.cos(theta));
        matrix.set(2, 0, -Math.sin(theta));
        matrix.set(2, 2, Math.cos(theta));
        matrix.set(0,2, Math.sin(theta));

        return matrix;
    }

    @Override
    public void loop() {

    }
}
