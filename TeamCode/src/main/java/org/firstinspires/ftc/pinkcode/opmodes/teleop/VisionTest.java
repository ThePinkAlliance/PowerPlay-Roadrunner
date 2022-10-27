package org.firstinspires.ftc.pinkcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.pinkcode.subsystems.Hardware;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.pinkcode.lib.PinkOpMode;

import java.util.List;

@TeleOp(name = "Tensorflow Test, [EXPERIMENTAL]")
public class VisionTest extends PinkOpMode {
    private static final String TFOD_MODEL_ASSET = "PowerPlay_LCR.tflite";
    private static final String[] LABELS = {
            "left",
            "center",
            "right"
    };

    private static final String VUFORIA_KEY =
            "AU5HdoL/////AAABmdflEYY1uEgKvLLnXhuUKQEiOh/Swf8w1NP3fjwJ0L5KhNZjEBmtqvcb1vRriuL7dxpTimmKsrPxVN0GSemDm1z0zZHiuEDJjN6is0gE5cC8eCf5/w4A9J9xygAQMiK4UOje3lWQjKpyMbqNeKgy1I6PZqyXBae1+6/gecIRmHuDjcqGFcEnRKmf8e6iPrFIdaC53DkmQUxJWRalVEqWsdmwmLm69AsaoG+aL7D0xkupVo7U23C2fdDkl66qsFO7v7jf0ONGEdmNjg1TTEKQmrip86/iMst+I7mdLA/pYsY00EjAjgPJ8YdXEqR5pKR2CK4DNmVU+c2A7T+w+KhGwxJ8us9j9FpYTd1yC0wRQD0R";

    TFObjectDetector tensorflow;
    VuforiaLocalizer vuforiaLocalizer;

    @Override
    public void init() {
        this.hardware = new Hardware(hardwareMap);

        configureVision();

        if (tensorflow != null) {
            tensorflow.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tensorflow.setZoom(2.5, 16.0/9.0);
        }
    }

    @Override
    public void loop() {
        if (tensorflow != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tensorflow.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                // step through the list of recognitions and display boundary info.
                for (int i = 0; i < updatedRecognitions.size(); i++) {
                    Recognition recognition = updatedRecognitions.get(i);

                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                telemetry.update();
            }
        }
    }

    private void configureVision() {
        VuforiaLocalizer.Parameters vuforiaParameters = new VuforiaLocalizer.Parameters();

        vuforiaParameters.vuforiaLicenseKey = VUFORIA_KEY;
        vuforiaParameters.cameraName = hardware.webcamFront;

        vuforiaLocalizer = ClassFactory.getInstance().createVuforia(vuforiaParameters);

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tensorflowParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        tensorflowParameters.minResultConfidence = 0.82f;
        tensorflowParameters.isModelTensorFlow2 = true;
//        tensorflowParameters.isModelQuantized = false;

        tensorflow = ClassFactory.getInstance().createTFObjectDetector(tensorflowParameters, vuforiaLocalizer);
        tensorflow.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
}
