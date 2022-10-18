package org.firstinspires.ftc.pinkcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.pinkcode.util.Encoder;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

public class Hardware {
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;

    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public Encoder leftEncoder;
    public Encoder rightEncoder;
    public Encoder centerEncoder;

    public WebcamName webcamFront;

    public Hardware(HardwareMap map) {
        frontLeft = map.get(DcMotorEx.class, "front-left");
        frontRight = map.get(DcMotorEx.class, "front-right");
        backRight = map.get(DcMotorEx.class, "back-right");
        backLeft = map.get(DcMotorEx.class, "back-left");

        leftEncoder = new Encoder(map.get(DcMotorEx.class, "left-encoder"));
        rightEncoder = new Encoder(map.get(DcMotorEx.class, "right-encoder"));
        centerEncoder = new Encoder(map.get(DcMotorEx.class, "center-encoder"));

        webcamFront = map.get(WebcamName.class, "webcam");

        configureMotors();
    }

    private void configureMotors() {
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
