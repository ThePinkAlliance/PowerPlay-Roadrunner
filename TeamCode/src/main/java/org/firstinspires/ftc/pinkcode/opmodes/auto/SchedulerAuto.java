package org.firstinspires.ftc.pinkcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.pinkcode.lib.PinkLinearOpMode;

import java.util.Arrays;
import java.util.List;

abstract class Task {
    public abstract void execute();

    public abstract boolean isFinished();
}

class TestTask extends Task {

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}

@Autonomous(name = "Scheduler Test")
public class SchedulerAuto extends PinkLinearOpMode {

    List<Task> queue = Arrays.asList(new TestTask());
    Task currentTask;

    enum SchedulerState {
        RUNNING,
        STOPPED
    }

    SchedulerState currentState = SchedulerState.RUNNING;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {
            if (currentState == SchedulerState.RUNNING) {
                currentTask.execute();
            }

            if (currentTask.isFinished() && currentTask != null) {
                if (queue != null && !queue.isEmpty()) {
                    currentTask = queue.get(0);
                    queue.remove(0);
                } else {
                    if (queue != null && currentTask != null && queue.isEmpty() && currentState == SchedulerState.RUNNING) {
                        currentTask = null;
                        currentState = SchedulerState.STOPPED;
                    }
                }
            }
        }
    }
}